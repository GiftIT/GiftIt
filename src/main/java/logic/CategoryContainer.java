package logic;

import logic.config.Config;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by vlad on 12/16/15.
 */
public class CategoryContainer {

    private static CategoryContainer instance = new CategoryContainer();

    private Set<Category> categories;

    public static CategoryContainer getInstance(){
        return instance;
    }

    private CategoryContainer(){
        categories = new HashSet<>();
        String fileName = Config.getInstance().getParamenter("vocabulary.config");
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(fileName),"UTF-8"));
            StringBuffer buffer = new StringBuffer();
            String line;
            while((line = reader.readLine()) != null){
                buffer.append(line);
            }
            reader.close();

            try {
                JSONArray categories = new JSONObject(buffer.toString()).getJSONArray("categories");
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

                    Category c = new Category(category, keys, sites, url, shop);
                    this.categories.add(c);
                }
            }catch (JSONException ex){
                ex.printStackTrace();
            }


        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public Set<Category> getCategories(){
        return categories;
    }
}
