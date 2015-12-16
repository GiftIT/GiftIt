package logic;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by vlad on 12/12/15.
 */
public class Post {

    private int ownerId;
    private int id;
    private Category category;

    public Category getCategory() {
        return category;
    }

    public Post(int ownerId, int id, Category category) {
        this.ownerId = ownerId;
        this.id = id;
        this.category = category;
    }

    private String composeRequest(int offset){
        StringBuilder result = new StringBuilder("likes.getList?type=post&owner_id=-");
        result.append(ownerId).append("&item_id=").append(id).append("&count=1000&offset=").append(offset).append("&v=").append(Group.VK_API_VERSION);
        return result.toString();
    }

    public Integer[] getIds(){

        HttpUrlConnection connection = HttpUrlConnection.getInstance();
        String request = composeRequest(0);
        String response = connection.execute(request);

        List<Integer> peopleIds = new LinkedList<Integer>();

        try {
            JSONObject json = new JSONObject(response);
            JSONObject vkResponse = json.getJSONObject("response");
            int count = vkResponse.getInt("count");
            JSONArray items = vkResponse.getJSONArray("items");

            boolean reachEnd = false;
            int offset = 0;
            while(!reachEnd) {

                for (int i = 0; i < items.length(); i++) {
                    peopleIds.add(items.getInt(i));
                }

                reachEnd = offset < count;

                if(!reachEnd) {
                    offset += 1000;
                    response = connection.execute(composeRequest(offset));
                    items = new JSONObject(response).getJSONObject("response").getJSONArray("items");
                }


            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return peopleIds.toArray(new Integer[0]);

    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Post() {
    }
}
