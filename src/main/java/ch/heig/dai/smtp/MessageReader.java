package ch.heig.dai.smtp;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * MessageReader
 * Reads the email messages from a JSON file using google.gson
 * These email messages will be put in an ArrayList of Messages
 * @author Stefano Bianchet
 * @author Michael Strefeler
 * @see Message
 **/
public class MessageReader {
    private final ArrayList<Message> messages = new ArrayList<>();

    public MessageReader(){
        Gson gson = new Gson();
        String filename = "/src/main/config/messages.json";

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("").getAbsolutePath() + filename), StandardCharsets.UTF_8))){
            JsonArray jsonArray = gson.fromJson(br, JsonArray.class);
            for(JsonElement je : jsonArray) {
                JsonObject jo = je.getAsJsonObject();
                try{
                    String subject = jo.get("subject").getAsString();
                    String body = jo.get("body").getAsString();
                    messages.add(new Message(subject, body));
                }catch(RuntimeException ex){
                    throw new RuntimeException("There is an error in one of your messages! Fix it in messages.json");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public int getMessageListSize(){
        return messages.size();
    }
}
