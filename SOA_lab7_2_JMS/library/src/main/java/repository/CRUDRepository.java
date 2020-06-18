package repository;

import java.util.List;

public interface CRUDRepository<T> {
    T create(T c) throws Exception;

    T get(Class type, Object id);

    List<T> getAll(Class type);

    T update(T c) throws Exception;

    void delete(Class type, Object id) throws Exception;

    List<T> getWithCriteriaQuery(Class type, String parameter1, Object parameter2);

    List<T> joinAndGetWithCriteriaQuery(Class type, List<String> joins, List<String> route, String parameter1, Object parameter2);
}
