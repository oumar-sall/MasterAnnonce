package com.example.tp01dev.service;

import com.example.tp01dev.model.Category;
import com.example.tp01dev.repository.CategoryRepository;
import com.example.tp01dev.util.JPAUtil;
import jakarta.persistence.EntityManager;
import java.util.List;

public class CategoryService {
    private CategoryRepository categoryRepository = new CategoryRepository();

    /**
     * Récupère toutes les catégories pour les afficher dans les formulaires (Select)
     */
    public List<Category> listerToutes() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            // On passe l'EntityManager au repository pour la lecture
            return categoryRepository.findAll(em);
        } finally {
            em.close(); // Important : toujours libérer la ressource
        }
    }

    /**
     * Trouve une catégorie précise par son ID
     */
    public Category trouverParId(Long id) {
        if (id == null) return null;

        EntityManager em = JPAUtil.getEntityManager();
        try {
            return categoryRepository.findById(em, id);
        } finally {
            em.close();
        }
    }
}