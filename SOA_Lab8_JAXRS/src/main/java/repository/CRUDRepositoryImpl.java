package repository;

import org.jboss.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

@Stateless
public class CRUDRepositoryImpl<T> implements CRUDRepository<T>, Serializable {

    private static final Logger log = Logger.getLogger(CRUDRepository.class);

    @PersistenceContext(unitName = "SOA_lab6_1_Library")
    EntityManager em;

    public CRUDRepositoryImpl() {
    }

    @Override
    public T create(T obj) throws Exception{
        try {
            em.persist(obj);
        } catch (PersistenceException e) {
            log.error((e.getMessage()));
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
    public List<T> getByField(Class type, String fieldName, Object value) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(type);
        Root<T> hh = query.from(type);
        query.select(hh)
                .where(cb.equal(hh.get(fieldName), value));
        return em.createQuery(query).getResultList();
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
            log.error((e.getMessage()));
            throw new Exception(e.getMessage());
        }
        return obj;
    }

    @Override
    public void delete(Class type, Object obj) throws Exception{
        try{
            this.em.remove(em.contains(obj) ? obj : em.merge(obj));
        } catch (PersistenceException e) {
        log.error((e.getMessage()));
        throw new Exception(e.getMessage());
        }
    }

}
