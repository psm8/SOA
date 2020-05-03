package repository;

import javax.persistence.EntityManager;

public class CatalogRepositoryImpl {

    EntityManager em;

    public CatalogRepositoryImpl(EntityManager em) {
        this.em = em;
    }
}
