package com.el_jobru.repository;

import com.el_jobru.db.HibernateUtil;
import com.el_jobru.models.BaseObject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;

import java.util.Optional;

public interface Repository<O extends BaseObject<ID>, ID> {

    default O saveOrUpdate(O object){
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            if(object.getId() == null) {
                em.persist(object);
            } else {
                return em.merge(object);
            }
            transaction.commit();
            return object;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    default void delete(O object){
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.remove(object);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    default Optional<O> findById(ID id, Class<O> entityClass) {
        try(EntityManager em = HibernateUtil.getEntityManager()){
            O object = em.find(entityClass, id);
            return Optional.of(object);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
