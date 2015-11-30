package com.twilio.verification.repository;

import com.twilio.verification.model.User;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public abstract class Repository<T> {

    protected final EntityManager em;
    protected final String entityName;

    public Repository(Class<T> entity) {

        Map<String, String> properties = getProperties();
        em = Persistence
                .createEntityManagerFactory("servlets-quickstart", properties)
                .createEntityManager();

        this.entityName = entity.getSimpleName();
    }

    public List<T> findAll() {
        Query query = em.createQuery(
                String.format("SELECT e FROM %s e", entityName));

        return query.getResultList();
    }

    public T find(int id) {
        T entity = (T) em.find(User.class, id);
        em.refresh(entity);

        return entity;
    }

    public T create(T entity) {
        getTransaction().begin();
        em.persist(entity);
        getTransaction().commit();

        return entity;
    }

    public T update(T entity) {
        getTransaction().begin();
        T updatedEntity = em.merge(entity);
        getTransaction().commit();

        return updatedEntity;
    }

    public void delete(T entity) {
        getTransaction().begin();
        em.remove(entity);
        getTransaction().commit();
    }

    private EntityTransaction getTransaction() {
        return em.getTransaction();
    }

    private Map<String, String> getProperties() {
        Map<String, String> env = System.getenv();
        Map<String, String> config = new HashMap<>();
        for (String key : env.keySet()) {
            if (key.contains("JDBC_URL")) {
                config.put("javax.persistence.jdbc.url", env.get(key));
            }

            if (key.contains("DB_USER")) {
                config.put("javax.persistence.jdbc.user", env.get(key));
            }

            if (key.contains("DB_PASSWORD")) {
                config.put("javax.persistence.jdbc.password", env.get(key));
            }
        }

        return config;
    }
}
