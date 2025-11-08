package com.el_jobru.repository;

import com.el_jobru.db.HibernateUtil;
import com.el_jobru.models.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;

import java.util.Optional;
import java.util.UUID;

public class UserRepository {
    public User save(User user) {
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(user);
            transaction.commit();
            return user;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public Optional<User> findByEmail(String email) {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            return em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", email)
                    .getResultStream()
                    .findFirst();
        }
    }

    public Optional<User> findById(UUID id) {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            User user = em.find(User.class, id);
            return Optional.of(user);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
