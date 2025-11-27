package com.el_jobru.repository;

import com.el_jobru.db.HibernateUtil;
import com.el_jobru.models.mission.Mission;
import io.javalin.http.BadRequestResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

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
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return em.createQuery("SELECT m FROM Mission m WHERE m.title = :title", Mission.class)
                    .setParameter("title", title)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new BadRequestResponse("Missão não encontrada com o título: " + title);
        } finally {
            em.close();
        }
    }
}
