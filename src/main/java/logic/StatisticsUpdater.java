package logic;

import logic.webWorkers.Person;
import logic.webWorkers.Post;
import logic.webWorkers.Worker;
import logic.webWorkers.vk.VkWorker;
import model.entity.*;
import model.utility.GenericDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Updates statistics
 */
public class StatisticsUpdater implements Runnable {

    private ArrayList<Worker> workers;

    private GenericDao productDao;

    public StatisticsUpdater() {
        workers = new ArrayList<>();
        ApplicationContext context = new ClassPathXmlApplicationContext("db.xml");
        productDao = (GenericDao) context.getBean("productDao");
    }

    public void addWorker(Worker w) {
        workers.add(w);
    }

    public void dropTable() {

        Set<Category> categories = CategoryContainer.getInstance().getCategories();

        //creates empty database
        for (Category c : categories) {
            Product product = new Product(c.getType());


            productDao.create(product);

        }

    }

    public void updateStatistic() {
        Person.updateYear();
        for (Worker w : workers) {
            while (w.hasPost()) {

                try {
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Post p = w.getNextPost();
                Product product = (Product) productDao.find(p.getCategory().getType());
                Person[] persons = p.getPersons();



                for (Person person : persons) {

                    User user = new User();
                    if (person.hasSex()) {
                        user.setSex(person.getSex());
//                        sex[person.getSex()]++;
                    }
                    if (person.hasAge()) {
                        user.setAge(person.getAgeCategory());
                    }
                    if (person.hasCountry()) {

                        user.setCountry(person.getCountry());
                    }
                    user.setProduct(product);

                    if(product.contains(user.getSex(), user.getAge(), user.getCountry())){
                        product.get(user.getSex(), user.getAge(), user.getCountry()).incAmount();
                    }
                    else {

                        product.getUsers().add(user);
                    }
                }

//                product.getSex().get(0).addLikes(sex[1]);
//                product.getSex().get(1).addLikes(sex[0]);
//                List<AgeCategory> ages = product.getAgeCategories();
//                for (int i = 0; i < age.length; i++) {
//                    ages.get(i).addLikes(age[i]);
//                }

                product.addPosts();
                productDao.update(product);
            }
        }
    }


    @Override
    public void run() {
        this.dropTable();
        this.addWorker(new VkWorker());
        this.updateStatistic();
        System.out.println("finish!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

    public static void main(String[] args){
        System.out.println(System.currentTimeMillis());
        StatisticsUpdater su = new StatisticsUpdater();
        su.run();
    }

}
