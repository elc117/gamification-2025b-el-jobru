package com.el_jobru.repository;

import com.el_jobru.db.HibernateUtil;
import com.el_jobru.models.chapter.Chapter;
import com.el_jobru.models.diary.Diary;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;

import java.util.List;
import java.util.Optional;

public class ChapterRepository {
    public Chapter save(Chapter chapter) {
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(chapter);
            transaction.commit();
            return chapter;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            em.close();
        }}

    public void delete(Chapter chapter) {
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.remove(chapter);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
        finally { em.close(); }
    }

    public Optional<Chapter> findById(long id) {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            Chapter chapter = em.find(Chapter.class, id);
            return Optional.of(chapter);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public List<Chapter> findAllDiary(Long id) {
        try(EntityManager em = HibernateUtil.getEntityManager()) {
            return em.createQuery("SELECT c FROM Chapter c WHERE c.diary.id = :id", Chapter.class)
                    .setParameter("id", id)
                    .getResultList();
        }
    }
}
