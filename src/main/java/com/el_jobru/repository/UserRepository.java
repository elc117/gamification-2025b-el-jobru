package com.el_jobru.repository;

import com.el_jobru.db.HibernateUtil;
import com.el_jobru.models.user.Email;
import com.el_jobru.models.user.User;
import jakarta.persistence.EntityManager;

import java.util.Optional;
import java.util.UUID;

public class UserRepository implements Repository<User, UUID>{
    public Optional<User> findByEmail(Email email) {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            return em.createQuery("SELECT u FROM User u WHERE u.email.value = :emailValue", User.class)
                    .setParameter("emailValue", email.value())
                    .getResultStream()
                    .findFirst();
        }
    }
}
