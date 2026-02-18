package com.example.tp01dev.repository;

import com.example.tp01dev.model.Category;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class CategoryRepository {

    private static final Logger logger = LoggerFactory.getLogger(CategoryRepository.class);

    public List<Category> findAll(EntityManager em) {
        logger.debug("Exécution de la requête de récupération de toutes les catégories.");
        try {
            List<Category> results = em.createQuery("SELECT c FROM Category c", Category.class).getResultList();
            logger.debug("{} catégories récupérées de la base.", results.size());
            return results;
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des catégories en base.", e);
            throw e;
        }
    }

    public Category findById(EntityManager em, Long id) {
        logger.debug("Recherche de la catégorie avec l'ID : {}", id);
        Category category = em.find(Category.class, id);
        if (category == null) {
            logger.warn("La catégorie avec l'ID {} n'existe pas en base.", id);
        }
        return category;
    }
}