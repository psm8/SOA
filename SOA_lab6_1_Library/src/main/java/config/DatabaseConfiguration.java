package config;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DatabaseConfiguration {
    private volatile static EntityManagerFactory factory;

    private static EntityManagerFactory getInstance() {
        if (factory == null) {
            synchronized (EntityManagerFactory.class) {
                if (factory == null) {

                    factory = Persistence.createEntityManagerFactory("SOA_lab6_1_Library");
                }
            }
        }
        return factory;
    }

    public static EntityManager getEntityManager() throws Exception {
        return getInstance().createEntityManager();
    }
}
