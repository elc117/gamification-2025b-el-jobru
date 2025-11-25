package com.el_jobru.repository;

import com.el_jobru.db.HibernateUtil;
import com.el_jobru.models.level.Level;
import jakarta.persistence.EntityManager;

import java.util.List;

public class LevelRepository implements Repository<Level, Integer> {
    public List<Level> findAll() {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            String query = "SELECT l FROM Level l";

            return em.createQuery(query, Level.class)
                    .getResultList();
        }
    }
}
