package repository;

import java.util.List;
import java.util.Map;

public interface CRUDRepository<T> {
    T create(T c);

    T get(Class type, Object id);

    List<T> getAll(Class type);

    T update(T c);

    void delete(Class type, Object id);

    List<T> getWithCriteriaQuery(Class type, String parameter1, Object parameter2);
}
