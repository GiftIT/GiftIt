package logic;

import logic.webWorkers.Person;
import logic.webWorkers.Post;
import logic.webWorkers.Worker;
import logic.webWorkers.vk.VkWorker;
import model.entity.*;
import model.utility.CommonDaoJpa;
import model.utility.GenericDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Updates statistics
 */
public class StatisticsUpdater implements Runnable {

    private ArrayList<Worker> workers;

    private GenericDao productDao;

    ApplicationContext context;

    public StatisticsUpdater() {
        workers = new ArrayList<>();
        context = new ClassPathXmlApplicationContext("db.xml");
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
        int deleted = 0;
        int all = 0;
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
                    all++;
                    if (person.hasSex()) {
                        user.setSex(person.getSex());
//                        sex[person.getSex()]++;
                    } else {
                        deleted++;
                        continue;
                    }
                    ;
                    if (person.hasAge()) {
                        user.setAge(person.getAgeCategory());
                    } else {
                        deleted++;
                        continue;
                    }
                    ;
                    if (person.hasCountry()) {

                        user.setCountry(person.getCountry());
                    } else {
                        deleted++;
                        continue;
                    }
                    ;
                    user.setProduct(product);

                    if (product.contains(user.getSex(), user.getAge(), user.getCountry())) {
                        product.get(user.getSex(), user.getAge(), user.getCountry()).incAmount();
                    } else {

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
        System.out.println(1.0 * deleted / all);
    }

    public String[] getData() {
        GenericDao dao = (GenericDao) context.getBean("userDao");
        List<User> users = dao.findAll();
        List<String> result = new LinkedList<>();
        for (int i = 0; i < users.size(); i++) {
//            if(users.get(i).getProduct().getIdProduct() == 10 || users.get(i).getProduct().getIdProduct() == 5)
//                continue;
            User u = users.get(i);
            String s = new StringBuilder(""+u.getSex()).append(",").append(u.getAge()).append(",").append(u.getCountry()).append(",").append(u.getProduct().getName()).append(",\n").toString();
            for (int j = 0; j < users.get(i).getAmount(); j++) {
                result.add(s);
            }
        }
        return result.toArray(new String[result.size()]);
    }

    @Override
    public void run() {



        this.dropTable();
        this.addWorker(new VkWorker());
        this.updateStatistic();
        System.out.println("finish!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
        StatisticsUpdater su = new StatisticsUpdater();
        su.run();
        String[] data = su.getData();
        StringBuilder b = new StringBuilder();
        for(String s : data){
            b.append(s);
        }
        System.out.println(b.toString().length());
    }

}
