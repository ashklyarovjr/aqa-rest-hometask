package com.epam.aqa.shkliarov.dao;


import com.epam.aqa.shkliarov.entity.CarEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class CarDAO {

    final static Logger LOGGER = LogManager.getLogger(CarDAO.class);

    private static final String PERSISTENCE_CARS_UNIT = "cars-unit";
    public static final String FIND_ALL_QUERY = "findAllCars";

    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_CARS_UNIT);

    public CarEntity get(int id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        CarEntity entity = null;
        try {
            entity = em.find(CarEntity.class, id);
        } finally {
            em.close();
        }
        return entity;
    }

    public List getAll() {
        EntityManager em = entityManagerFactory.createEntityManager();
        return em.createNamedQuery(FIND_ALL_QUERY).getResultList();
    }

    public void delete(int id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            CarEntity entity = em.find(CarEntity.class, id);
            if (entity != null) {
                em.remove(entity);
            }
            transaction.commit();
        } catch (Exception e) {
            LOGGER.error("Error deleting country: " + e.getMessage());
            transaction.rollback();
        } finally {
            em.close();
        }
    }

    public void put(int id, CarEntity countryEntity) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        countryEntity.setId(id);
        if (em.find(CarEntity.class, id) == null) {
            LOGGER.error("Error putting country: country not found");
        } else {
            try {
                transaction.begin();
                em.merge(countryEntity);
                transaction.commit();
            } catch (Exception e) {
                LOGGER.error("Error putting country: " + e.getMessage());
                transaction.rollback();
            } finally {
                em.close();
            }
        }
    }

    public void save(CarEntity countryEntity) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(countryEntity);
            transaction.commit();
        } catch (Exception e) {
            LOGGER.error("Error saving country: " + e.getMessage());
            transaction.rollback();
        } finally {
            em.close();
        }
    }


}
