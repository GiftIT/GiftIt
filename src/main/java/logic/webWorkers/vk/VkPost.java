package logic.webWorkers.vk;


import logic.Category;
import logic.connectors.HttpUrlConnection;
import logic.webWorkers.Person;
import logic.webWorkers.Post;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by vlad on 12/12/15.
 */
public class VkPost extends Post{

    private int ownerId;
    private int id;
    private HttpUrlConnection connection;
    public VkPost(int ownerId, int id, Category category) {
        super(category);
        this.ownerId = ownerId;
        this.id = id;
        connection = HttpUrlConnection.getConnection(HttpUrlConnection.VK);
    }

    private String composeRequest(int offset){
        StringBuilder result = new StringBuilder("likes.getList?type=post&owner_id=-");
        result.append(ownerId).append("&item_id=").append(id).append("&count=400&offset=").append(offset).append("&v=").append(VkWorker.VK_API_VERSION);
        return result.toString();
    }

    private static String composeRequest(List<Integer> ids){
        StringBuilder request = new StringBuilder("users.get?user_ids=");
        Iterator<Integer> iterator = ids.iterator();
        request.append(ids.iterator().next());
        while(iterator.hasNext()){
            request.append(",").append(iterator.next());
        }
        request.append("&fields[]=sex&fields[]=bdate&fields[]=country&v=").append(VkWorker.VK_API_VERSION);
        return request.toString();
    }

    @Override
    public Person[] getPersons() {

        String request = composeRequest(0);
        String response = connection.executeGET(request);

        List<Person> persons = new LinkedList<>();

        if(!response.isEmpty()) {
            List<Integer> peopleIds = new LinkedList<Integer>();
            try {
                JSONObject json = new JSONObject(response);
                JSONObject vkResponse = json.getJSONObject("response");
                int count = vkResponse.getInt("count");
                JSONArray items = vkResponse.getJSONArray("items");

                System.out.println(count + " likes");

                boolean reachEnd = false;
                int offset = 0;
                while (!reachEnd) {
                    peopleIds.clear();
                    for (int i = 0; i < items.length(); i++) {
                        peopleIds.add(items.getInt(i));
                    }



                    persons.addAll(loadInfo(peopleIds));

                    reachEnd = offset < count;

                    if (!reachEnd) {
                        offset += 400;
                        response = connection.executeGET(composeRequest(offset));
                        items = new JSONObject(response).getJSONObject("response").getJSONArray("items");
                    }


                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }



        return persons.toArray(new Person[persons.size()]);
    }

    private List<Person> loadInfo(List<Integer> ids){


        String request = composeRequest(ids);
        String response = connection.executeGET(request);

        List<Person> persons = new LinkedList<Person>();
        if(!response.isEmpty()) {
            JSONArray users = new JSONObject(response).getJSONArray("response");

            for (int i = 0; i < users.length(); i++) {
                JSONObject currentObject = users.getJSONObject(i);
                Person current = new Person();
                if (currentObject.has("sex")) {

                    current.setSex(currentObject.getInt("sex") - 1);
                }

                if (currentObject.has("country")) {
                    current.setCountry(currentObject.getJSONObject("country").getString("title"));
                }
                if (currentObject.has("bdate")) {
                    String dateString = currentObject.getString("bdate");

                    int byear = year(dateString);
                    if (byear != 0) {
                        current.setAge(Person.getYear() - byear);
                    }
                }
                persons.add(current);
            }
        }
        return persons;
    }


    private static int year(String date){
        String[] tokens = date.split("\\.");
        if(tokens.length < 3)
            return 0;

        return Integer.parseInt(tokens[2]);

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

}
