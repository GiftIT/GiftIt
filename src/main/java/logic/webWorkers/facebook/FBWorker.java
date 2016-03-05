package logic.webWorkers.facebook;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import logic.webWorkers.Post;
import logic.webWorkers.Worker;

/**
 * Created by vladislav on 3/5/16.
 */
public class FBWorker implements Worker {

    FacebookClient.AccessToken token;
    private FacebookClient client;

    private static final String appId = "927503130702299";
    private static final String appSecret = "8ef1117782c0ac6b9a7be64ba65c4b4c";

    public FBWorker(){
        getKey();
    }

    public void getKey(){
        FacebookClient.AccessToken accessToken = new DefaultFacebookClient().obtainAppAccessToken(appId, appSecret);
        String accesKey = accessToken.getAccessToken();
        client = new DefaultFacebookClient(accesKey);
    }

    @Override
    public boolean hasPost() {
        return false;
    }

    @Override
    public Post getNextPost() {
        return null;
    }
}
