package com.el_jobru.repository;

import com.el_jobru.db.HibernateUtil;
import com.el_jobru.models.level.Level;
import com.el_jobru.models.user.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    public Level findById(Integer id) {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            return em.find(Level.class, id);
        }
    }

}
