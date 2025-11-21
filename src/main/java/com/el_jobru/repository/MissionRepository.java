package com.el_jobru.repository;

import com.el_jobru.db.HibernateUtil;
import com.el_jobru.models.level.Level;
import com.el_jobru.models.mission.Mission;
import com.el_jobru.models.user.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    public Mission update(Mission mission) {
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(mission);
            transaction.commit();
            return mission;
        }
        catch (Exception e) {
            if (transaction.isActive()) { transaction.rollback(); }
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

    public Mission findByTitle(String title) {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            return em.find(Mission.class, title);
        }
    }
}
