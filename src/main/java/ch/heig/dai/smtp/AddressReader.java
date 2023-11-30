package ch.heig.dai.smtp;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class AddressReader {
    private ArrayList<String> victims = new ArrayList<>();

    // todo: check if not null
    public AddressReader(){
        Gson gson = new Gson();
        String filename = "/src/main/config/victims.json";
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("").getAbsolutePath() + filename), StandardCharsets.UTF_8))){
            JsonArray jsonArray = gson.fromJson(br, JsonArray.class);
            for(JsonElement je : jsonArray) {
                JsonObject jo = je.getAsJsonObject();
                victims.add(jo.get("email").getAsString());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<String> getVictims(){
        return victims;
    }
}
