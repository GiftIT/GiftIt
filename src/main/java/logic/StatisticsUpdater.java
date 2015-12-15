package logic;

import model.entity.AgeCategory;
import model.entity.Country;
import model.entity.Product;
import model.entity.Sex;
import model.utility.GenericDao;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Updates statistics
 */
public class StatisticsUpdater {

    private Group[] registeredGroups;

    //todo: delete and replace with database



    /**
     * Return json representation of group to save information about it
     * @param g group to be saved
     * @return json representation
     */
    private String toJSON(Group g){
        return "{\n" + "\"group_id\":" + g.getId() + ",\n\"last_post\":" + g.getLastPost() + "\n}";
    }

    public StatisticsUpdater(){

        ApplicationContext context = new ClassPathXmlApplicationContext("db.xml");
        GenericDao productDao = (GenericDao) context.getBean("productDao");

//        GenericDao countryDao = (GenericDao) context.getBean("countryDao");

        ArrayList<Category> categories = Vocabulary.getInstance().getCategories();
        for(Category c : categories) {
            Product product = new Product(c.getType());
            Sex m = new Sex("m", 0, product);
            Sex g = new Sex("g", 0, product);
            List<Sex> sex = new ArrayList<>();
            sex.add(m);
            sex.add(g);
            product.setSex(sex);

            AgeCategory a1 = new AgeCategory("0-6", 0, product);
            AgeCategory a2 = new AgeCategory("7-10", 0, product);
            AgeCategory a3 = new AgeCategory("11-15", 0, product);
            AgeCategory a4 = new AgeCategory("16-18", 0, product);
            AgeCategory a5 = new AgeCategory("19-30", 0, product);
            AgeCategory a6 = new AgeCategory("31-40", 0, product);
            AgeCategory a7 = new AgeCategory("41-50", 0, product);
            AgeCategory a8 = new AgeCategory("51-100", 0, product);
            List<AgeCategory> ages = new ArrayList<>();
            ages.add(a1);
            ages.add(a2);
            ages.add(a3);
            ages.add(a4);
            ages.add(a5);
            ages.add(a6);
            ages.add(a7);
            ages.add(a8);


            product.setAgeCategories(ages);

            productDao.create(product);

        }
        //load groups list
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("/groups.json")));
            StringBuilder res = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null){
                res.append(line.trim());
            }
            JSONObject ob = new JSONObject(res.toString());
            JSONArray groups = ob.getJSONArray("groups");
            registeredGroups = new Group[groups.length()];
            for(int i = 0; i < groups.length(); i++){
                JSONObject o = groups.getJSONObject(i);
                int id = o.getInt("group_id");
                long date = o.getLong("last_post");
                int internalId = o.getInt("internal_id");
                registeredGroups[i] = new Group(id, internalId, date);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        String jsonToWrite = "{\n\"groups\":[\n";


        //load info from all registered groups
        for(Group g : registeredGroups){

            //load latest posts
            Post[] posts = g.getPosts();

            //uses for debug
            //todo: delete

            System.out.println("--------------------------------------------------------");
            System.out.println("--------------------------------------------------------");
            System.out.println("--------------------------------------------------------");
            System.out.println("Found " + posts.length + " posts");
            System.out.println("--------------------------------------------------------");
            System.out.println("--------------------------------------------------------");
            System.out.println("--------------------------------------------------------");
            //get info from each post
            for(int i = 0; i < posts.length; i++){
                Post p = posts[i];

                Product product = (Product) productDao.find(p.getCategory().getType());
//                product

                Integer[] ids = p.getIds();

                //uses for debug
                System.out.println(ids.length + " likes");


                Person.updateYear();
                int amount = 400;
                List<Person> allPerson = new LinkedList<Person>();
                for(int j = 0; j < ids.length; ){
                    if(j + amount > ids.length)
                        amount = ids.length - j;

                    Integer[] currentIds = new Integer[amount];
                    System.arraycopy(ids, j, currentIds, 0, amount);
                    allPerson.addAll(Person.createPersons(currentIds));

                    j+= amount;
                }

//                System.out.println(allPerson.size());
                int male = 0;
                int female = 0;
                int a1 = 0;
                int a2 = 0;
                int a3 = 0;
                int a4 = 0;
                int a5 = 0;
                int a6 = 0;
                int a7 = 0;
                int a8 = 0;
                for(Person person : allPerson){

                    if(person.getSex() == 1)
                        female++;
                    else if(person.getSex() == 2)
                        male++;

                    if(person.hasAge()){
                        int age = person.getAge();
                        if(age <= 6)
                            a1++;
                        else if(age > 6 && age <= 10)
                            a2++;
                        else if(age > 10 && age <= 15)
                            a3++;
                        else if(age > 15 && age <= 18)
                            a4++;
                        else if(age > 18 && age <= 30)
                            a5++;
                        else if(age > 30 && age <= 40)
                            a6++;
                        else if(age > 40 && age <= 50)
                            a7++;
                        else
                            a8++;
                    }

                    if(person.getCountry() != null){

                        String personCountry = person.getCountry();

                        List<Country> countries = product.getCountry();
                        boolean found = false;
                        for(Country c : countries){
                            if(c.getCountry().equals(personCountry)) {
                                found = true;
                                c.addLikes();
                                break;
                            }
                        }

                        if(!found){
                            Country newCountry = new Country(personCountry, 0, product);
                            countries.add(newCountry);
                        }

                    }
                }

                product.getSex().get(0).addLikes(male);
                product.getSex().get(1).addLikes(female);

                List<AgeCategory> ages = product.getAgeCategories();
                ages.get(0).addLikes(a1);
                ages.get(1).addLikes(a2);
                ages.get(2).addLikes(a3);
                ages.get(3).addLikes(a4);
                ages.get(4).addLikes(a5);
                ages.get(5).addLikes(a6);
                ages.get(6).addLikes(a7);
                ages.get(7).addLikes(a8);

                product.addPosts();

                productDao.update(product);
//                staticstics.add(current);

            }

        }

        //save groups
        jsonToWrite += toJSON(registeredGroups[0]);

        for(int i = 1; i < registeredGroups.length; i++){
            jsonToWrite +=  ",\n" +toJSON(registeredGroups[i]);
        }

        jsonToWrite += "]\n}";


        //todo: enable saving, for now it is not necessary
//        BufferedWriter writer = null;
//        try {
//            writer = new BufferedWriter(new FileWriter(new File("src/resources/groups.json")));
//            writer.write(jsonToWrite);
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


    }

//    public void show(){
//        for(Staticstics s : staticstics){
//            System.out.println(s.toString());
//        }
//    }

    public static void main(String[] args){
        StatisticsUpdater updater = new StatisticsUpdater();
//        updater.show();
    }

}
