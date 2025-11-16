package com.el_jobru.db;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.hibernate.cfg.AvailableSettings;

import java.util.HashMap;
import java.util.Map;

public class HibernateUtil {
    private static final EntityManagerFactory emf;

    static {
        try {
            Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

            Map<String, String> props = new HashMap<>();

            String dbUrl = getEnv(dotenv, "DB_URL");
            String dbUser = getEnv(dotenv, "DB_USER");
            String dbPass = getEnv(dotenv, "DB_PASSWORD");

            if (dbUrl == null) {
                throw new RuntimeException("ERRO: DB_URL não definida no .env ou nas variáveis de ambiente.");
            }
            if (dbUser == null) {
                throw new RuntimeException("ERRO: DB_USER não definida no .env ou nas variáveis de ambiente.");
            }

            props.put(AvailableSettings.JAKARTA_JDBC_URL, dbUrl);
            props.put(AvailableSettings.JAKARTA_JDBC_USER, dbUser);
            props.put(AvailableSettings.JAKARTA_JDBC_PASSWORD, dbPass);

            props.put("hibernate.hikari.dataSource.cachePrepStmts", "true");
            props.put("hibernate.hikari.dataSource.prepStmtCacheSize", "250");
            props.put("hibernate.hikari.dataSource.prepStmtCacheSqlLimit", "2048");

            emf = Persistence.createEntityManagerFactory("default", props);
        } catch (Throwable ex) {
            System.err.println("Falha ao inicializar o EntityManagerFactory." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    private static String getEnv(Dotenv dotenv, String key) {
        String value = System.getenv(key);
        if (value != null) {
            return value;
        }

        if (dotenv != null) {
            return dotenv.get(key);
        }

        return null;
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
