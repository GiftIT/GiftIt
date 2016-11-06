import model.entity.Product;
import model.utility.GenericDao;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class JUnitTest {
    static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("db.xml");
    static GenericDao<Product, Integer> productDao;

    @BeforeClass
    public static void init() {
        productDao = (GenericDao<Product, Integer>) applicationContext.getBean("productDao");
    }


    @Before
    public void cleanDB() {
        List<Product> products = productDao.findAll();
        for (Product product : products) {
            productDao.delete(product);
        }
    }

    @Test
    public void getBean() {
        assertNotNull(applicationContext.getBean("productDao"));
        assertNotNull(applicationContext.getBean("userDao"));
    }

    @Test
    public void checkDaoOperation() {
        Product product = new Product("jewelery1");
        Product product1 = new Product("jewelery2");
        Product product2 = new Product("jewelery3");
        Product product3 = new Product("jewelery4");
        productDao.create(product);
        productDao.create(product1);
        productDao.create(product2);
        productDao.create(product3);
        List<Product> allProduct = productDao.findAll();
        assertTrue(!allProduct.isEmpty());
    }

    @Test
    public void newProductDaoShouldBeEmpty() {
        List<Product> allProduct = productDao.findAll();
        assertTrue(allProduct.isEmpty());
    }


}
