package repository;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Stateless
public class CRUDRepositoryImpl<T> implements CRUDRepository<T> {

    @PersistenceContext(unitName = "SOA_lab6_1_Library")
    EntityManager em;

    public CRUDRepositoryImpl() {
    }

    @Override
    public T create(T obj) {
        em.merge(obj);
        return obj;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get(Class type, Object id) {
        return (T) em.find(type, id);
    }

    @Override
    public List<T> getAll(Class type) {
        CriteriaQuery<T> cq = em.getCriteriaBuilder().createQuery(type);
        cq.select(cq.from(type));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public T update(T obj) {
        em.merge(obj);
        return obj;
    }

    @Override
    public void delete(Class type, Object obj) {
        this.em.remove(em.contains(obj) ? obj : em.merge(obj));
    }

    @Override
    public List<T> getWithCriteriaQuery(Class type, String parameter1, Object parameter2) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(type);
        Root<T> hh = query.from(type);
        query.select(hh)
                .where(cb.equal(hh.get(parameter1), parameter2));
        return em.createQuery(query).getResultList();
    }
}
