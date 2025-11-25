package com.el_jobru.repository;

import com.el_jobru.db.HibernateUtil;
import com.el_jobru.models.mission.Mission;
import jakarta.persistence.EntityManager;

import java.util.List;

public class MissionRepository implements Repository<Mission, Integer> {

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
