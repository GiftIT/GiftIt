package model.utility;

import java.io.Serializable;

/**
 * Created by Бабенко on 7/1/2015.
 */
public interface GenericDao<T, PK extends Serializable> {
    void create(T object);

    T read(PK key);

    void update(T object);

    void delete(T object);

    T find(String name);


}
