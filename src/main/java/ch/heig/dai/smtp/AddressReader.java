package ch.heig.dai.smtp;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.regex.Pattern;


/**
 * AddressReader reads email addresses from a JSON file.
 * @author Michael Strefeler
 * @author Stefano Bianchet
 */
public class AddressReader {
    private final ArrayList<String> victims = new ArrayList<>();

    public AddressReader(){
        Gson gson = new Gson();
        String filename = "/classes/victims.json";
        String regexPattern = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

        Pattern pattern = Pattern.compile(regexPattern);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("").getAbsolutePath() + filename), StandardCharsets.UTF_8))){
            JsonArray jsonArray = gson.fromJson(br, JsonArray.class);
            for(JsonElement je : jsonArray) {
                JsonObject jo = je.getAsJsonObject();
                String email;
                try{
                    email = jo.get("email").getAsString();
                }catch(RuntimeException ex){
                    throw new RuntimeException("null values are not allowed! Fix it in victims.json");
                }

                if(!pattern.matcher(email).matches()){
                    throw new RuntimeException("\"" + email +"\"" + " is an invalid email address! Fix it in victims.json");
                }
                victims.add(email);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<String> getVictims(){
        return victims;
    }

    public int getVictimsSize(){
        return victims.size();
    }
}
