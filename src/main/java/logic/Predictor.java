package logic;

import model.utility.GenericDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;

/**
 * Created by vlad on 12/13/15.
 */
public class Predictor {

    private static double sexWeigter = 0.5;
    private static double ageWeigter = 0.5;
    private static double countryWeigter = 0.3;


    private static final Predictor instance = new Predictor();

    private GenericDao productDao;

    public static Predictor getInstance(){
        return instance;
    }

    private Predictor(){
        ApplicationContext context = new ClassPathXmlApplicationContext("db.xml");
        productDao = (GenericDao) context.getBean("productDao");



    }

    public Map<Category, Double> getPrediction(int keySex, int ageTag, String country){

//        new StatisticsUpdater();
//
//
//        Map<Category, Double> result = new HashMap<>();
//
//        Set<Category> categories = CategoryContainer.getInstance().getCategories();
//
//        for(Category c : categories){
//
//            Product product = (Product) productDao.find(c.getType());
//
//            Sex sex = product.getSex().get(keySex);
//            long sexLikes = sex.getNumberOfLikes();
//            long allSexLikes = sexLikes + product.getSex().get(keySex^1).getNumberOfLikes();
//
//            long agesLikes = 0;
//            long allAgesLikes = 0;
//            List<AgeCategory> ages = product.getAgeCategories();
//            for(AgeCategory a : ages){
//                allAgesLikes += a.getNumberOfLikes();
//                if(a.getAgeTag() == ageTag)
//                    agesLikes = a.getNumberOfLikes();
//            }
//
//            //todo: for country
//            long countryLikes = 0;
//            long allCountryLikes = 0;
//            List<Country> countries = product.getCountry();
//            for(Country country1 : countries){
//                allCountryLikes += country1.getNumberOfLikes();
//                if(country1.getCountry().equals(country)) {
//                    System.out.println("Country hash equals");
//                    countryLikes = country1.getNumberOfLikes();
//                }
//            }
//            if(countryLikes == 0){
//                countryLikes = allCountryLikes;
//            }
//
//
//            //todo: consider weigths, but for now it is okay
//            double sexWeigth = 1.0*sexLikes/allSexLikes;
//            double ageWeigth = 1.0*agesLikes/allAgesLikes;
//            double countryWeigth = 1.0*countryLikes/allCountryLikes;
//
//            double sexW = 1*sexWeigth;
//            double ageW = 1*ageWeigth;
//            double countW = 1*countryWeigth;
//            double sexAgeW = 1*sexWeigth*ageWeigth;
//            double sexCountW = 1*sexWeigth*countryWeigth;
//            double ageCountW = 1*ageWeigth*countryWeigth;
//            double mulW = 1*sexWeigth*ageWeigth*countryWeigth;
//
//            System.out.println(product.getName());
//            System.out.println("s:  " + sexW);
//            System.out.println("a:  " + ageW);
//            System.out.println("c:  " + countW);
//            System.out.println("sa: " + sexAgeW);
//            System.out.println("sc: " + sexCountW);
//            System.out.println("ac: " + ageCountW);
//            System.out.println("sac:" + mulW);
//
//
//
//            double sumWeigth = sexW+ageW+countW+sexAgeW+sexCountW+ageCountW+mulW;
//            result.put(c, sumWeigth);
//        }
//
//        return result;

        return null;
    }

    public static void main(String[] args){
        Predictor p = Predictor.getInstance();
        Map<Category, Double> m = p.getPrediction(1, 2, "2");
        Set<Category> categories = m.keySet();
        Category[] cats = new Category[categories.size()];
        categories.toArray(cats);
        Arrays.sort(cats, new Comparator<Category>() {
            @Override
            public int compare(Category o1, Category o2) {
                return -(int)(100000*(m.get(o1) - m.get(o2)));
            }
        });

        double sum = 0.0;
        for(Category c : cats){
            sum += m.get(c);
        }

        for(Category c : cats){

            System.out.println(String.format("%-10s ->   %7.4f %%", c.getType(),m.get(c)/sum*100));

        }
    }


}
