package com.el_jobru.repository;

import com.el_jobru.db.HibernateUtil;
import com.el_jobru.models.diary.Diary;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.Optional;

public class DiaryRepository {

    public Diary save(Diary diary) {
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(diary);
            transaction.commit();
            return diary;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public void delete(Diary diary) {
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.remove(diary);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
        finally { em.close(); }
    }

    public Optional<Diary> findById(Long id) {
        try(EntityManager em = HibernateUtil.getEntityManager()) {
            Diary diary = em.find(Diary.class, id);
            return Optional.ofNullable(diary);
        }
    }
}
