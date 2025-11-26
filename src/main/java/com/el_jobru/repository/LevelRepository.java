package com.el_jobru.repository;

import com.el_jobru.db.HibernateUtil;
import com.el_jobru.models.level.Level;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.util.List;
import java.util.Optional;

public class LevelRepository implements Repository<Level, Integer> {
    public List<Level> findAll() {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            String query = "SELECT l FROM Level l";

            return em.createQuery(query, Level.class)
                    .getResultList();
        }
    }

    public Optional<Level> findByXp(Long xp) {

        try (EntityManager entityManager = HibernateUtil.getEntityManager()) {
//            String jpql = "SELECT l FROM Level l WHERE :xp >= l.minXp AND :xp <= l.maxXp";
             String jpql = "SELECT l FROM Level l WHERE :xp BETWEEN l.minXp AND l.maxXp";

            Level level = entityManager.createQuery(jpql, Level.class)
                    .setParameter("xp", xp)
                    .setMaxResults(1)
                    .getSingleResult();

            return Optional.of(level);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
