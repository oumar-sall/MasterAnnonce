package com.example.tp01dev.service;

import com.example.tp01dev.model.User;
import com.example.tp01dev.repository.UserRepository;
import com.example.tp01dev.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class UserService {
    private UserRepository userRepository = new UserRepository();

    public boolean inscrire(User u) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            // On vérifie si le username est déjà pris
            if (userRepository.findByUsername(em, u.getUsername()) != null) {
                return false;
            }
            userRepository.save(em, u);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally { em.close(); }
    }

    public User authentifier(String username, String password) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            User user = userRepository.findByUsername(em, username);
            if (user != null && user.getPassword().equals(password)) {
                return user;
            }
            return null;
        } finally { em.close(); }
    }
}