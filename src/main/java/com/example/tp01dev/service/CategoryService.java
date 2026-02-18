package com.example.tp01dev.service;

import com.example.tp01dev.model.Category;
import com.example.tp01dev.repository.CategoryRepository;
import com.example.tp01dev.util.JPAUtil;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CategoryService {

    // Initialisation du logger pour CategoryService
    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    private CategoryRepository categoryRepository = new CategoryRepository();

    /**
     * Récupère toutes les catégories pour les afficher dans les formulaires (Select)
     */
    public List<Category> listerToutes() {
        logger.debug("Récupération de la liste complète des catégories.");
        EntityManager em = JPAUtil.getEntityManager();
        try {
            List<Category> categories = categoryRepository.findAll(em);
            logger.debug("{} catégories récupérées.", categories.size());
            return categories;
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des catégories", e);
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * Trouve une catégorie précise par son ID
     */
    public Category trouverParId(Long id) {
        if (id == null) {
            logger.warn("Tentative de recherche de catégorie avec un ID null.");
            return null;
        }

        logger.debug("Recherche de la catégorie ID: {}", id);
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Category cat = categoryRepository.findById(em, id);
            if (cat == null) {
                logger.warn("Aucune catégorie trouvée pour l'ID: {}", id);
            }
            return cat;
        } catch (Exception e) {
            logger.error("Erreur lors de la recherche de la catégorie ID: {}", id, e);
            throw e;
        } finally {
            em.close();
        }
    }
}