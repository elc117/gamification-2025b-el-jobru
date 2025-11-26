package com.el_jobru.repository;

import com.el_jobru.db.HibernateUtil;
import com.el_jobru.models.BaseObject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;

import java.util.Optional;

public interface Repository<O extends BaseObject<ID>, ID> {

    default O saveOrUpdate(O object) {
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();

        O result = object;

        try {
            transaction.begin();

            if (object.getId() == null) {
                em.persist(object);
            } else {
                result = em.merge(object);
            }

            transaction.commit();
            return result;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e; // Sempre relance a exceção para o Controller saber que deu erro
        } finally {
            em.close();
        }
    }

    default void delete(O object) {
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            O managedObject = em.contains(object) ? object : em.merge(object);
            em.remove(managedObject);

            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    default Optional<O> findById(ID id, Class<O> entityClass) {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            O object = em.find(entityClass, id);
            return Optional.ofNullable(object);
        }
    }
}
