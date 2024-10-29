package mmo.project.utils;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.HashMap;
import java.util.Map;

public class HibernateUtil {
    private static final EntityManagerFactory emFactory;

    static {
        // Create a map for configuration properties
        Map<String, Object> configOverrides = new HashMap<>();

        // Set the properties
        configOverrides.put("javax.persistence.jdbc.driver", "org.postgresql.Driver");
        configOverrides.put("javax.persistence.jdbc.url", System.getenv("DB_URL"));
        configOverrides.put("javax.persistence.jdbc.user", System.getenv("DB_USER"));
        configOverrides.put("javax.persistence.jdbc.password", System.getenv("DB_PASSWORD"));
//        configOverrides.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
//        configOverrides.put("hibernate.hbm2ddl.auto", "update"); // For schema updates

        emFactory = Persistence.createEntityManagerFactory("mmo-project-persistence-unit", configOverrides);
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return emFactory;
    }

    public static void shutdown() {
        emFactory.close();
    }
}
