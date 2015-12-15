package model.utility;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * Created by Бабенко on 7/9/2015.
 */
public class CommonDaoJpa<T, PK extends Serializable> implements GenericDao<T, PK> {

    @Autowired
    private SessionFactory sessionFactory;

    private Class<T> type;

    public CommonDaoJpa() {
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public CommonDaoJpa(Class<T> type) {
        this.type = type;
    }

    @Override
    @Transactional
    public T find(String name) {
        return (T) sessionFactory.getCurrentSession().createCriteria(type).add(Restrictions.eq("name", name)).uniqueResult();

    }


    @Override
    @Transactional
    public void create(T object) {
        try {

            sessionFactory.getCurrentSession().save(object);
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    @Override
    @Transactional
    public T read(PK key) {
        try {
            return (T) sessionFactory.getCurrentSession().get(type, key);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional
    public void update(T object) {
        try {
            sessionFactory.getCurrentSession().update(object);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void delete(T object) {
        try {
            sessionFactory.getCurrentSession().delete(object);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
