package logic;


import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by vlad on 12/12/15.
 */
public class HttpUrlConnection {

    private final String USER_AGENT = "Mozilla/5.0";

    private HttpUrlConnection(){

    }

    private static final HttpUrlConnection instance = new HttpUrlConnection();
    public static HttpUrlConnection getInstance(){
        return instance;
    }

    public String execute(String request){

        String url = "https://api.vk.com/method/" + request;
        StringBuffer response = null;
        try {
            URL obj = new URL(url);
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

            // optional default is GET
            con.setRequestMethod("GET");

            //add request header
            con.setRequestProperty("User-Agent", USER_AGENT);


//            System.out.println("\nSending 'GET' request to URL : " + url);
//            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            System.out.println("Get answer from request:" + request);
            String inputLine;
            response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
//            System.out.println(response.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response.toString();
    }



}
