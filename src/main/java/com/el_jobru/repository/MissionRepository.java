package com.el_jobru.repository;

import com.el_jobru.db.HibernateUtil;
import com.el_jobru.models.mission.Mission;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class MissionRepository {
    public Mission save(Mission mission) {
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction entityTransaction = em.getTransaction();

        try {
            entityTransaction.begin();
            em.persist(mission);
            entityTransaction.commit();
            return mission;
        } catch (Exception e) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public List<Mission> findAll() {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            String query = "SELECT m FROM Mission m LEFT JOIN FETCH m.minLevel";

            return em.createQuery(query, Mission.class)
                    .getResultList();
        }
    }
}
