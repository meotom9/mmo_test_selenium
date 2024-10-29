package mmo.project.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import mmo.project.model.MmoConfig;
import mmo.project.utils.HibernateUtil;

import java.util.Vector;


public class MmoConfigDAO {

    public Vector<MmoConfig> getAllConfigs() {
        EntityManager entityManager = HibernateUtil.getEntityManagerFactory().createEntityManager();
        try {
            TypedQuery<MmoConfig> query = entityManager.createNamedQuery("MmoConfig.findAll", MmoConfig.class);
            return new Vector<MmoConfig>(query.getResultList());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }
}
