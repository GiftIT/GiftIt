package model.utility;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Бабенко on 7/1/2015.
 */
public interface GenericDao<T, PK extends Serializable> {
    void create(T object);

    T read(PK key);

    void update(T object);

    void delete(T object);

    T find(String name);

    List<T> findAll();


}
