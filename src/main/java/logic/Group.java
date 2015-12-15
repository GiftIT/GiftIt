package logic;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Created by vlad
 * Uses for get Array of relevant posts from group
 */
public class Group {

    private int id;
    private long lastPost;
    private int internalId;

    public static final String VK_API_VERSION = "5.40";

    public Group(int id, int internalId,  long lastPost) {
        this.id = id;
        this.lastPost = lastPost;
        this.internalId = internalId;
        categories = Vocabulary.getInstance().getCategories();

    }

    private String composeRequest(int offset){
        StringBuilder result = new StringBuilder("wall.get?owner_id=-");
        result.append(id).append("&count=100&offset=").append(offset).append("&v=").append(VK_API_VERSION);
        return result.toString();
    }

    ArrayList<Category> categories;

    /**
     * Inspect text for key words, and try to get category from this text
     * @param text response from vk
     * @return category or null if can't get category
     */
    private Category inspectCategory(String text){


        for(Category c : categories){
            if(c.getAllowedSite().contains(internalId)) {
                ArrayList<String> keys = c.getKeys();

                for (String key : keys) {
                    if (text.contains(key))
                        return c;
                }
            }


        }


        return null;
    }

    public Post[] getPosts(){



        HttpUrlConnection connection = HttpUrlConnection.getInstance();
        String request = composeRequest(0);
        String response = connection.execute(request);

        List<Post> relevantPosts = new LinkedList<Post>();

        try {
            JSONObject json = new JSONObject(response);
            JSONObject vkResponse = json.getJSONObject("response");
            int count = vkResponse.getInt("count");
            JSONArray items = vkResponse.getJSONArray("items");
            long newLastPost = items.getJSONObject(0).getLong("date");

            boolean reachEnd = false;
            int offset = 0;
            while(!reachEnd) {

                for (int i = 0; i < items.length() && !reachEnd; i++) {
                    JSONObject currentObject = items.getJSONObject(i);
                    long date = currentObject.getLong("date");

                    //if current post older than last checked
                    reachEnd = date <= lastPost;

                    int id = currentObject.getInt("id");

                    //TODO: at this point check if post contains relevant info
                    //if it do, add it to array of posts
                    Category category = inspectCategory(currentObject.getString("text").toLowerCase());

                    if(category != null)
                        relevantPosts.add(new Post(this.id, id, category));
                }


                offset += 100;
                reachEnd = reachEnd && (offset < count);

                if(!reachEnd) {
                    response = connection.execute(composeRequest(offset));
                    items = new JSONObject(response).getJSONObject("response").getJSONArray("items");
                }


            }

            lastPost = newLastPost;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return relevantPosts.toArray(new Post[0]);
    }


    public int getId() {
        return id;
    }

    public long getLastPost() {
        return lastPost;
    }


    public Group() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLastPost(long lastPost) {
        this.lastPost = lastPost;
    }

    public int getInternalId() {
        return internalId;
    }

    public void setInternalId(int internalId) {
        this.internalId = internalId;
    }
}
