package logic.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * Created by vlad on 12/16/15.
 */
public class Config {

    private static Config instance = new Config("statistics.properties");

    public static Config getInstance(){
        return instance;
    }

    private HashMap<String, String> values;

    private void fillDefaultProperties(){
        values.put("vocabulary.config", "/vocabulary.json");
        values.put("groups.config", "/groups.json");
    }

    public String getParamenter(String key){
        return values.get(key);
    }

    private Config(String fileName){
        values = new HashMap<>();
        fillDefaultProperties();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(fileName),"UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split("=");
                try {
                    values.put(tokens[0].trim(), tokens[1].trim());
                }catch (NullPointerException ex){
                    System.err.println("Invalid parameter: " + line);
                }
            }
        }catch (Exception e){
            System.err.println("Some file errors were occurred. Check file " + fileName);
            System.err.println("Using default settings");
        }
    }


}
