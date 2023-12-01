package ch.heig.dai.smtp;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ConfigReader {
    private final int port;
    private final int nbGroups;
    private final String address;

    public ConfigReader(){

        Gson gson = new Gson();
        String filename = "/src/main/config/config.json";

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("").getAbsolutePath() + filename), StandardCharsets.UTF_8))){
            JsonObject jsonObject   = gson.fromJson(br, JsonObject.class);
            this.port               = jsonObject.get("port").getAsInt();
            this.nbGroups           = jsonObject.get("numberOfGroups").getAsInt();
            this.address            = jsonObject.get("address").getAsString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        catch (RuntimeException e) {
            throw new RuntimeException("Invalid config! Fix your config.json file!");
        }
    }

    public String getServerAddress(){
        return address;
    }

    public int getServerPort(){
        return port;
    }

    public int getNumberOfGroups(){
        return nbGroups;
    }
}
