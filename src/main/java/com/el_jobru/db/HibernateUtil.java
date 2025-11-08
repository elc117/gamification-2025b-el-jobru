package com.el_jobru.db;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.HashMap;
import java.util.Map;

public class HibernateUtil {
    private static final EntityManagerFactory emf;

    static {
        try {
            Dotenv dotenv = Dotenv.load();

            Map<String, String> props = new HashMap<>();

            // Propriedades específicas do Hikari que o Hibernate espera
            props.put("hibernate.hikari.jdbcUrl", dotenv.get("DB_URL"));
            props.put("hibernate.hikari.dataSource.user", dotenv.get("DB_USER"));
            props.put("hibernate.hikari.dataSource.password", dotenv.get("DB_PASSWORD"));

            props.put("hibernate.hikari.dataSource.cachePrepStmts", "true");
            props.put("hibernate.hikari.dataSource.prepStmtCacheSize", "250");
            props.put("hibernate.hikari.dataSource.prepStmtCacheSqlLimit", "2048");

            emf = Persistence.createEntityManagerFactory("default", props);
        } catch (Throwable ex) {
            System.err.println("Falha ao inicializar o EntityManagerFactory." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
