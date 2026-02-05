package com.example.tp01dev.repository;

import com.example.tp01dev.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

public class UserRepository {

    // On reçoit l'EntityManager en paramètre
    public void save(EntityManager em, User u) {
        em.persist(u);
    }

    public User findByUsername(EntityManager em, String username) {
        try {
            return em.createQuery("SELECT u FROM User u WHERE u.username = :uname", User.class)
                    .setParameter("uname", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // Plus propre que de catcher une Exception générique
        }
    }
}