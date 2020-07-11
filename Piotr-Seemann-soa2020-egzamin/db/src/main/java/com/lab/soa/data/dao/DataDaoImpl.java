package com.lab.soa.data.dao;

import com.lab.soa.data.model.Data;
import org.jboss.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class DataDaoImpl implements DataDao {

    private static final Logger LOG = Logger.getLogger(DataDao.class);

    @PersistenceContext(unitName = "SOA_lab6_1_Library")
    EntityManager em;

    @Override
    public Data create(Data data) throws Exception {
        try {
            em.persist(data);
        } catch (PersistenceException e) {
            LOG.error((e.getMessage()));
            throw new Exception(e.getMessage());
        }
        return data;
    }

    @Override
    public Data get(long id) {
        return em.find(Data.class, id);
    }

    @Override
    public List<Data> getAll() {
        CriteriaQuery<Data> cq = em.getCriteriaBuilder().createQuery(Data.class);
        cq.select(cq.from(Data.class));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public Data update(Data data) throws Exception {
        try{
            em.merge(data);
        } catch (PersistenceException e) {
            LOG.error((e.getMessage()));
            throw new Exception(e.getMessage());
        }
        return data;
    }

    @Override
    public void delete(Data data) throws Exception {
        try{
            this.em.remove(em.contains(data) ? data : em.merge(data));
        } catch (PersistenceException e) {
            LOG.error((e.getMessage()));
            throw new Exception(e.getMessage());
        }
    }
}
