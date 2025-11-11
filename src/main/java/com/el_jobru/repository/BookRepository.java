package com.el_jobru.repository;

import com.el_jobru.db.HibernateUtil;
import com.el_jobru.models.book.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class BookRepository {
    public Book save(Book book) {
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction entityTransaction = em.getTransaction();

        try {
            entityTransaction.begin();
            em.persist(book);
            entityTransaction.commit();
            return book;
        } catch (Exception e) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public List<Book> findAll() {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            String query = "SELECT b FROM Book b";

            return em.createQuery(query, Book.class)
                    .getResultList();
        }
    }
}
