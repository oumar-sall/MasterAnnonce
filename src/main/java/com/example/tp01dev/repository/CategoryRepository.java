package com.example.tp01dev.repository;

import com.example.tp01dev.model.Category;
import jakarta.persistence.EntityManager;
import java.util.List;

public class CategoryRepository {

    public List<Category> findAll(EntityManager em) {
        return em.createQuery("SELECT c FROM Category c", Category.class).getResultList();
    }

    public Category findById(EntityManager em, Long id) {
        return em.find(Category.class, id);
    }
}