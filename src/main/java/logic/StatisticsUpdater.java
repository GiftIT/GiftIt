package logic;

import logic.webWorkers.Person;
import logic.webWorkers.Post;
import logic.webWorkers.Worker;
import logic.webWorkers.vk.VkGroup;
import logic.webWorkers.vk.VkPost;
import logic.webWorkers.vk.VkWorker;
import model.entity.AgeCategory;
import model.entity.Country;
import model.entity.Product;
import model.entity.Sex;
import model.utility.GenericDao;
import org.json.JSONArray;
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

    private ArrayList<Worker> workers;

    private GenericDao productDao;

    public StatisticsUpdater(){
        workers = new ArrayList<>();
        ApplicationContext context = new ClassPathXmlApplicationContext("db.xml");
        productDao = (GenericDao) context.getBean("productDao");
    }

    public void addWorker(Worker w){
        workers.add(w);
    }

    public void dropTable(){

        Set<Category> categories = CategoryContainer.getInstance().getCategories();

        //creates empty database
        for(Category c : categories) {
            Product product = new Product(c.getType());
            Sex g = new Sex("g", 0, product);
            Sex m = new Sex("m", 0, product);
            List<Sex> sex = new ArrayList<>();
            sex.add(m);
            sex.add(g);
            product.setSex(sex);

            AgeCategory a1 = new AgeCategory(0, 0, product);
            AgeCategory a2 = new AgeCategory(1, 0, product);
            AgeCategory a3 = new AgeCategory(2, 0, product);
            AgeCategory a4 = new AgeCategory(3, 0, product);
            AgeCategory a5 = new AgeCategory(4, 0, product);
            AgeCategory a6 = new AgeCategory(5, 0, product);
            AgeCategory a7 = new AgeCategory(6, 0, product);
            AgeCategory a8 = new AgeCategory(7, 0, product);
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

    }

    public void updateStatistic(){
        Person.updateYear();
        for(Worker w : workers){
            while(w.hasPost()){

                try {
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Post p = w.getNextPost();
                Product product = (Product) productDao.find(p.getCategory().getType());
                Person[] persons = p.getPersons();

                int[] sex = new int[2];
                int[] age = new int[8];

                for(Person person : persons) {
                    if(person.hasSex()) {
                        sex[person.getSex()]++;
                    }
                    if(person.hasAge()) {
                        age[person.getAgeCategory()]++;
                    }
                    if(person.hasCountry()) {
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
                            Country newCountry = new Country(personCountry, 1, product);
                            countries.add(newCountry);
                        }
                    }
                }

                product.getSex().get(0).addLikes(sex[0]);
                product.getSex().get(1).addLikes(sex[1]);
                List<AgeCategory> ages = product.getAgeCategories();
                for(int i = 0; i < age.length; i++) {
                    ages.get(i).addLikes(age[i]);
                }
                product.addPosts();
                productDao.update(product);
            }
        }
    }



    public static void main(String[] args){
        StatisticsUpdater updater = new StatisticsUpdater();
        updater.dropTable();
        updater.addWorker(new VkWorker());
        updater.updateStatistic();
//        updater.show();
    }

}
