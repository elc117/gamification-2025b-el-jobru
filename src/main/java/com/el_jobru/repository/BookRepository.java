package com.el_jobru.repository;

import com.el_jobru.db.HibernateUtil;
import com.el_jobru.models.book.Book;
import jakarta.persistence.EntityManager;

import java.util.List;

public class BookRepository implements Repository<Book, Long> {
    public List<Book> findAll() {
        try (EntityManager em = HibernateUtil.getEntityManager()) {
            String query = "SELECT b FROM Book b";

            return em.createQuery(query, Book.class)
                    .getResultList();
        }
    }
}
