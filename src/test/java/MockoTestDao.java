import model.entity.Product;
import model.utility.GenericDao;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.verification.Times;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class MockoTestDao {
    ApplicationContext applicationContext = new ClassPathXmlApplicationContext("db.xml");

    GenericDao<Product, Integer> productDao;
    private Product product;

    @Before
    public void setupMock() {
        product = mock(Product.class);
        productDao = mock(GenericDao.class);
    }


    @Test
    public void testMockCreation() {
        assertNotNull(product);
    }

    @Test
    public void testFindAll() {
        List all = new LinkedList();
        all.add(new Product("books"));
        all.add(new Product("toys"));

        //MOCK ALERT: return mocked result set on find
        when(productDao.findAll()).thenReturn(all);

        //call the main method you want to test
        List result = productDao.findAll();

        //MOCK ALERT: verify the method was called
        verify(productDao).findAll();
    }


    @Test
    public void testNullReturnIfNoDataFound() {
        List all = new LinkedList();

        List<Product> allProducts = productDao.findAll();
        for (Product product : allProducts) {
            productDao.delete(product);
        }

        //return mocked result set on find
        when(productDao.findAll()).thenReturn(all);

        //call the main method you want to test
        List result = productDao.findAll();

        //verify the method was called
        verify(productDao, new Times(2)).findAll();

        //verify null result was returned
        assertEquals(0, result.size());
    }
    
    @Test
    public void testResponsesOnFind() {
        when(productDao.find("cars")).thenReturn(new Product("cars"));
        Product found = productDao.find("cars");
        assertEquals("cars", found.getName());
        Product notFound = productDao.find("candy");
        assertNull(notFound);
    }
}
