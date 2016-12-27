package logic.connectors;


import javax.naming.OperationNotSupportedException;
import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Performs api request to different resources. It depends on created class
 */
public abstract class HttpUrlConnection {


    public static final int VK = 1;
    public static final int FB = 2;
    public static final int TW = 3;
    public static final int IN = 4;
    public static final int OK = 5;


    private final String USER_AGENT = "Mozilla/5.0";

    private String apiAddress;

    protected HttpUrlConnection(String apiAddress) {
        this.apiAddress = apiAddress;
    }


    public static HttpUrlConnection getConnection(int type){
        switch (type){
            case VK:
                return VkApiConnection.getInstance();
            default:
                return null;
        }
    }


    /**
      * Performs POST request to the server
      * This method have not  been tested yet. So use it on your own risk
      * @param request text which send to the server
      * @return response from the server
      */
    public String executePOST(String request) throws OperationNotSupportedException{
        throw new OperationNotSupportedException("This operation is not supported yet");
    }

//    /**
//     * Performs POST request to the server
//     * This method have not  been tested yet. So use it on your own risk
//     * @param request text which s
//     * @return
//     */
//    public String executePOST(String request){
//
//        String urlParameters  = "param1=a&param2=b&param3=c";
//        byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
//        int    postDataLength = postData.length;
//        String request        = "http://example.com/index.php";
//        URL    url            = new URL( request );
//        HttpURLConnection conn= (HttpURLConnection) url.openConnection();
//        conn.setDoOutput( true );
//        conn.setInstanceFollowRedirects( false );
//        conn.setRequestMethod( "POST" );
//        conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
//        conn.setRequestProperty( "charset", "utf-8");
//        conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
//        conn.setUseCaches( false );
//        try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
//            wr.write( postData );
//        }
//
//        String url = apiAddress + request;
//        StringBuffer response = new StringBuffer();
//        try {
//            URL obj = new URL(url);
//            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
//            con.setRequestMethod("POST");
//            con.setRequestProperty("User-Agent", USER_AGENT);
//            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//
//            String inputLine;
//            while ((inputLine = in.readLine()) != null) {
//                response.append(inputLine);
//            }
//            in.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return response.toString();
//    }


    /**
     * Performs GET request to the server
     * If server does not correspond, tries to connect with exponential backoff
     * @param request text which send to the server
     * @return response from the server or empty string if error
     */
    public String executeGET(String request){
        String url = apiAddress + request;
        StringBuffer response = new StringBuffer();
        try {
            URL obj = new URL(url);
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", USER_AGENT);

            BufferedReader in = null;
            int sleepTime = 100;
            final int maxSleepTime = 3600;
            while (in == null && sleepTime <= maxSleepTime) {
                try {
                    //if server does not correspond this line will produce an exception
                    in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                } catch (IOException e) {
                    sleepTime *= 2;
                    System.out.println("Cannot create input stream." +
                            "Trying again with exponential backoff in " + sleepTime + "ms");
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }
            if(in == null) {
                //todo:ask Vitaliy Babenko how to replace with logger
                System.err.println("Cannot connect to the server");
                System.out.println("Check link: " + url);
                return "";
            }
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.toString();
    }



}
