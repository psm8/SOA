package repository;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class CRUDRepositoryImpl<T> implements CRUDRepository<T>, Serializable {

    private static final Logger log = Logger.getLogger(CRUDRepository.class.getPackage().getName());

    @PersistenceContext(unitName = "SOA_lab6_1_Library")
    EntityManager em;

    public CRUDRepositoryImpl() {
    }

    @Override
    public T create(T obj) throws Exception{
        try {
            em.merge(obj);
        } catch (PersistenceException e) {
            log.severe((e.getMessage()));
            throw new Exception(e.getMessage());
        }
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
    public T update(T obj) throws Exception{
        try{
            em.merge(obj);
        } catch (PersistenceException e) {
            log.severe((e.getMessage()));
            throw new Exception(e.getMessage());
        }
        return obj;
    }

    @Override
    public void delete(Class type, Object obj) throws Exception{
        try{
            this.em.remove(em.contains(obj) ? obj : em.merge(obj));
        } catch (PersistenceException e) {
            log.severe((e.getMessage()));
            throw new Exception(e.getMessage());
        }
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

    @Override
    public List<T> joinAndGetWithCriteriaQuery(Class type, List<String> joins, List<String> route,  String parameter1, Object parameter2) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(type);
        Root<T> hh = query.from(type);
        hh.join(joins.get(0));
        hh.join(joins.get(1)).join(joins.get(2)).join(joins.get(3));
        Path<Object> path = hh.get(route.get(0));
        for (int i = 1; i < route.size(); i++) {
            path = path.get(route.get(i));
        }
        query.select(hh)
                .where(cb.equal(path.get(parameter1), parameter2));
        return em.createQuery(query).getResultList();
    }
}
