package model.utility;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Babenko on 15.12.2015.
 */
public class ModelApplicationContext implements ApplicationContextAware {

    private static  ApplicationContext applicationContext =null;

    private ModelApplicationContext() {
    }

    public static ApplicationContext getInstance(){
        if(applicationContext == null){
            synchronized(ModelApplicationContext.class){
                if(applicationContext== null){
                    applicationContext= new ClassPathXmlApplicationContext("db.xml");
                }
            }
        }
        return applicationContext;
    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }

}
