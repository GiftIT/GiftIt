package logic;

import logic.connectors.VkApiConnection;

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


    public Category(String type, ArrayList<String> keys, ArrayList<Integer> allowedSite, String url, String shop) {
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

    public ArrayList<String> getKeys() {
        return keys;
    }

    public void setKeys(ArrayList<String> keys) {
        this.keys = keys;
    }

    public ArrayList<Integer> getAllowedSite() {
        return allowedSite;
    }

    public void setAllowedSite(ArrayList<Integer> allowedSite) {
        this.allowedSite = allowedSite;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

}
