package repository;

import java.util.List;

public interface CRUDRepository<T> {
    T create(T c);

    T get(T c);

    List<T> getAll(Class type);

    T update(T c);

    void delete(T c);
}
