package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by vlad on 12/12/15.
 */
public class Category {
    private String type;

    private ArrayList<String> keys;
    private ArrayList<Integer> allowedSite;
    private String url;
    private String shop;

    public Category() {
    }

    public void setKeys(ArrayList<String> keys) {
        this.keys = keys;
    }

    public void setAllowedSite(ArrayList<Integer> allowedSite) {
        this.allowedSite = allowedSite;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public static ArrayList<Category> getCategories() {
        return categories;
    }

    public static void setCategories(ArrayList<Category> categories) {
        Category.categories = categories;
    }

    public String getUrl() {
        return url;
    }

    public String getShop() {
        return shop;
    }

    public ArrayList<String> getKeys() {
        return keys;
    }

    public ArrayList<Integer> getAllowedSite() {
        return allowedSite;
    }

    private static ArrayList<Category> categories = new ArrayList<Category>();

    public static Category getCategory(String categoryName){
        for(Category c : categories){
            if(c.getType().equals(categoryName))
                return c;
        }
        return null;
    }

    public static void addCategory(String type, ArrayList<String> keys, ArrayList<Integer> allowedSite, String url, String shop){
        categories.add(new Category(type, keys, allowedSite, url, shop));
    }



    private Category(String type, ArrayList<String> keys, ArrayList<Integer> allowedSite, String url, String shop) {
        this.type = type;
        this.allowedSite = allowedSite;
        this.keys = keys;
        this.url = url;
        this.shop = shop;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
