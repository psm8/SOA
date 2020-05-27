package repository;

import java.util.List;

public interface CRUDRepository<T> {
    T create(T c) throws Exception;

    T get(Class type, Object id);

    List<T> getByField(Class type, String fieldName, Object value);

    List<T> getAll(Class type);

    T update(T c) throws Exception;

    void delete(Class type, Object id) throws Exception;
}
