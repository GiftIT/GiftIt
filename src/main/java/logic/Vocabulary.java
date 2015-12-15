package logic;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;

/**
 * Contains map, in which to each category corresponds few key words
 * So, I can search through text key words and decide what is text about
 */
public class Vocabulary {

    private ArrayList<Category> records;

    private static Vocabulary instance;// = new Vocabulary("src/resources/vocabulary.json");

    public static Vocabulary getInstance(){
        instance  = new Vocabulary("/vocabulary.json");
        return instance;
    }

    public ArrayList<Category> getCategories(){
        return records;
    }

    private Vocabulary(String file){
        records = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(file)));
            StringBuilder result = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null){
                result.append(line.trim());
            }
            reader.close();
            try {
                JSONArray categories = new JSONObject(result.toString()).getJSONArray("categories");
                for (int i = 0; i < categories.length(); i++) {

                    JSONObject currentObject = categories.getJSONObject(i);
                    String category = currentObject.getString("category");

                    //get keys
                    ArrayList<String> keys = new ArrayList<String>();
                    JSONArray keysArray = currentObject.getJSONArray("keys");
                    for (int j = 0; j < keysArray.length(); j++) {
                        keys.add(keysArray.getString(j));
                    }

                    //get sites
                    ArrayList<Integer> sites = new ArrayList<>();
                    JSONArray sitesArray = currentObject.getJSONArray("sites");
                    for (int j = 0; j < sitesArray.length(); j++) {
                        sites.add(sitesArray.getInt(j));
                    }
                    String url = currentObject.getString("url");
                    String shop = currentObject.getString("shop");

                    Category c = Category.getCategory(category);
                    if(c == null) {
                        Category.addCategory(category, keys, sites, url , shop);
                        c = Category.getCategory(category);
                    }
                    records.add(c);
                }
            }catch (JSONException ex){
                ex.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        Vocabulary v = Vocabulary.getInstance();
        ArrayList<Category> categories = v.getCategories();
        for(Category c : categories){
            System.out.println(c.getType());
            ArrayList<String> keys = c.getKeys();
            for(String key : keys){
                System.out.println(key);
            }
        }
    }

    public ArrayList<Category> getRecords() {
        return records;
    }

    public void setRecords(ArrayList<Category> records) {
        this.records = records;
    }

    public Vocabulary() {
    }
}
