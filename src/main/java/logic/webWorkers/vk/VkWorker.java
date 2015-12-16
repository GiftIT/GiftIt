package logic.webWorkers.vk;

import logic.config.Config;
import logic.webWorkers.Post;
import logic.webWorkers.Worker;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class VkWorker implements Worker {

    public static final String VK_API_VERSION = "5.40";
    private VkGroup[] registeredGroups;
    private int currentGroup;
    private int currentPost;

    private Post[] currentPosts;

    private StringBuffer newGroupInfo;

    private String toJSON(VkGroup g){
        return "{\n\"group_id\":" + g.getId() + ",\n\"last_post\":" + g.getLastPost() + "\"internal_id\":" + g.getInternalId() + "\n}";
    }

    public VkWorker(){
        loadGroupsInfo();
        currentGroup = 0;
        newGroupInfo = new StringBuffer();
        loadCurrentPosts();
    }

    private void loadCurrentPosts(){
        System.out.println("Getting next group");
        currentPosts = registeredGroups[currentGroup].getPosts();
        System.out.println("there are " + currentPosts.length + " relevant posts");
//        if(currentPosts.length > 0)
//            registeredGroups[currentGroup].setLastPost(currentPosts[0]);
        currentPost = 0;
    }

    private void loadGroupsInfo(){
        try {
            String groupConfig = Config.getInstance().getParamenter("groups.config");
            BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(groupConfig)));
            StringBuilder res = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null){
                res.append(line.trim());
            }
            JSONObject ob = new JSONObject(res.toString());
            JSONArray groups = ob.getJSONArray("groups");
            registeredGroups = new VkGroup[groups.length()];
            for(int i = 0; i < groups.length(); i++){
                JSONObject o = groups.getJSONObject(i);
                int id = o.getInt("group_id");
                long date = o.getLong("last_post");
                int internalId = o.getInt("internal_id");
                registeredGroups[i] = new VkGroup(id, internalId, date);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            registeredGroups = new VkGroup[0];
        }
    }

    @Override
    public boolean hasPost() {

        while(currentPost == currentPosts.length && (++currentGroup) < registeredGroups.length){
            loadCurrentPosts();
        }

        return currentPost != currentPosts.length;
    }

    @Override
    public Post getNextPost() {


        System.out.println("getting " + currentPost + " post in group " + currentGroup);

        if(currentPost == currentPosts.length)
            throw new IndexOutOfBoundsException();

        return currentPosts[currentPost++];


    }
}
