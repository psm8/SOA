package repository;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class CRUDRepositoryImpl<T> implements CRUDRepository<T> {

    @PersistenceContext(unitName = "SOA_lab6_1_Library")
    EntityManager em;

    public CRUDRepositoryImpl() {
        super();
    }

    @Override
    public T create(T obj) {
        em.persist(obj);
        return obj;
    }

    @Override
    public T get(T c) {
        return null;
    }

    @Override
    public List<T> getAll() {
        return null;
    }

    @Override
    public T update(T c) {
        return null;
    }

    @Override
    public void delete(T c) {

    }
}
