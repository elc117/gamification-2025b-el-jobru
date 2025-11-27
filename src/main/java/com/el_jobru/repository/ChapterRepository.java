package com.el_jobru.repository;

import com.el_jobru.db.HibernateUtil;
import com.el_jobru.models.chapter.Chapter;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ChapterRepository implements Repository<Chapter, Long> {
    public List<Chapter> findAllDiary(Long id) {
        try(EntityManager em = HibernateUtil.getEntityManager()) {
            return em.createQuery("SELECT c FROM Chapter c WHERE c.diary.id = :id", Chapter.class)
                    .setParameter("id", id)
                    .getResultList();
        }
    }
}
