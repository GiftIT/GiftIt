package logic;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by vlad on 12/12/15.
 */
public class Person {

    private static int currYear;

    public static void updateYear(){
        currYear = (new Date()).getYear()+1900;
        System.out.println(currYear);
    }


    private int id;
    private int sex;
    private String country;
    private int age;

    private static String composeRequest(Integer[] ids){
        StringBuilder request = new StringBuilder("users.get?user_ids=");
        request.append(ids[0]);
        for(int i = 1; i < ids.length; i++){
            request.append(",").append(ids[i]);
        }
        request.append("&fields[]=sex&fields[]=bdate&fields[]=country&v=").append(Group.VK_API_VERSION);
        return request.toString();
    }

    public static List<Person> createPersons(Integer[] ids){
        HttpUrlConnection connection = HttpUrlConnection.getInstance();
        String request = composeRequest(ids);
        String response = connection.execute(request);

        List<Person> persons = new LinkedList<Person>();

        try {
            JSONArray users = new JSONObject(response).getJSONArray("response");
//            System.out.println(user.getString("first_name"));
//            System.out.println(user.getString("last_name"));
            for(int i = 0; i < users.length(); i++) {
                JSONObject currentObject = users.getJSONObject(i);
                Person current = new Person(ids[i]);
                try {
                    current.sex = currentObject.getInt("sex");
                }catch (JSONException ex){}
                try {
                    current.country = "" + currentObject.getJSONObject("country").getString("id");
                }
                catch (JSONException ex){}
                try{
                    String dateString = currentObject.getString("bdate");

                    int byear = year(dateString);
                    if (byear != 0) {
                        current.age = currYear - byear;
                    }
                }catch (JSONException e){

                }
                persons.add(current);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return persons;
    }

    public Person(int id) {
        this.id = id;
//        getInfo();
    }

    public int getSex() {
        return sex;
    }

    public String getCountry() {
        return country;
    }

    public int getAge() {
        return age;
    }

    private String composeRequest(){
        StringBuilder request = new StringBuilder("users.get?user_id=");
        request.append(id).append("&v=5.41&fields[]=sex&fields[]=bdate&fields[]=country");
        return request.toString();
    }

    private static int year(String date){
        String[] tokens = date.split("\\.");
        if(tokens.length < 3)
            return 0;

        return Integer.parseInt(tokens[2]);

    }


    private void getInfo(){
        HttpUrlConnection connection = HttpUrlConnection.getInstance();
        String request = composeRequest();
        String response = connection.execute(request);
        try {
            JSONObject user = new JSONObject(response).getJSONArray("response").getJSONObject(0);
//            System.out.println(user.getString("first_name"));
//            System.out.println(user.getString("last_name"));
            try {
                this.sex = user.getInt("sex");
            }catch (JSONException ex){

            }
            try {
                this.country = "" + user.getJSONObject("country").getString("id");
            }catch (JSONException ex){

            }
            try {
                String dateString = user.getString("bdate");
                int byear = year(dateString);
                if (byear != 0) {
                    age = currYear - byear;
                }
            }catch (JSONException ex){

            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean hasAge(){
        return age != 0;
    }

    public String toString(){
        return id + " " + sex + " " + country + " " + age;
    }

    public static int getCurrYear() {
        return currYear;
    }

    public static void setCurrYear(int currYear) {
        Person.currYear = currYear;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Person() {
    }
}
