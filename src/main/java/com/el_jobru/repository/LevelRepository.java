package com.el_jobru.repository;

import com.el_jobru.db.HibernateUtil;
import com.el_jobru.models.level.Level;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class LevelRepository {
    public Level save(Level level) {
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(level);
            transaction.commit();
            return level;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public List<Level> findAll() {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            String query = "SELECT l FROM Level l";

            return em.createQuery(query, Level.class)
                    .getResultList();
        }
    }
}
