package com.el_jobru.db;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.hibernate.cfg.AvailableSettings;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class HibernateUtil {
    private static final EntityManagerFactory emf;

    static {
        try {
            Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
            Map<String, String> props = new HashMap<>();

            String databaseUrl = getEnv(dotenv, "DB_URL");

            if (databaseUrl == null) {
                throw new RuntimeException("ERRO: DATABASE_URL não definida no .env ou nas variáveis de ambiente.");
            }

            try {
                URI dbUri = new URI(databaseUrl);

                String username = dbUri.getUserInfo().split(":")[0];
                String password = dbUri.getUserInfo().split(":")[1];

                String host = dbUri.getHost();
                int port = dbUri.getPort(); // Isso está retornando -1
                String path = dbUri.getPath();

                String jdbcUrl;

                if (port == -1) {
                    // Se a porta for -1, omita-a. O driver usará o padrão (5432).
                    jdbcUrl = "jdbc:postgresql://" + host + path;
                } else {
                    // Se a porta for especificada, inclua-a.
                    jdbcUrl = "jdbc:postgresql://" + host + ":" + port + path;
                }

                props.put(AvailableSettings.JAKARTA_JDBC_URL, jdbcUrl);
                props.put(AvailableSettings.JAKARTA_JDBC_USER, username);
                props.put(AvailableSettings.JAKARTA_JDBC_PASSWORD, password);

            } catch (URISyntaxException e) {
                System.err.println("Formato da DATABASE_URL inválido: " + databaseUrl);
                throw new RuntimeException("Erro ao parsear DATABASE_URL", e);
            }

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
