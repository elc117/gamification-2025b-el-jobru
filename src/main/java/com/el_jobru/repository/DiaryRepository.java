package com.el_jobru.repository;

import com.el_jobru.db.HibernateUtil;
import com.el_jobru.models.diary.Diary;
import com.el_jobru.models.user.User;
import jakarta.persistence.EntityManager;

import java.util.List;

public class DiaryRepository implements Repository<Diary, Long> {
    public List<Diary> findAll(User user) {
        EntityManager em = HibernateUtil.getEntityManager();
        return em.createQuery("SELECT d FROM Diary d WHERE d.author = :author", Diary.class)
                .setParameter("author", user)
                .getResultList();
    }
}
