package com.example.tp01dev.service;

import com.example.tp01dev.model.User;
import com.example.tp01dev.repository.UserRepository;
import com.example.tp01dev.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private UserRepository userRepository = new UserRepository();

    public boolean inscrire(User u) {
        logger.info("Tentative d'inscription pour l'utilisateur : {}", u.getUsername());
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            // On vérifie si le username est déjà pris
            if (userRepository.findByUsername(em, u.getUsername()) != null) {
                logger.warn("Échec d'inscription : le nom d'utilisateur '{}' est déjà utilisé.", u.getUsername());
                return false;
            }

            userRepository.save(em, u);
            tx.commit();
            logger.info("Utilisateur '{}' inscrit avec succès (ID: {}).", u.getUsername(), u.getId());
            return true;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            logger.error("Erreur technique lors de l'inscription de l'utilisateur : {}", u.getUsername(), e);
            throw e;
        } finally { em.close(); }
    }

    public User authentifier(String username, String password) {
        logger.info("Demande d'authentification reçue pour : {}", username);
        EntityManager em = JPAUtil.getEntityManager();
        try {
            User user = userRepository.findByUsername(em, username);
            if (user != null && user.getPassword().equals(password)) {
                logger.info("Authentification réussie pour '{}'.", username);
                return user;
            }

            logger.warn("Échec d'authentification pour '{}' : identifiants incorrects.", username);
            return null;
        } catch (Exception e) {
            logger.error("Erreur lors du processus d'authentification pour : {}", username, e);
            throw e;
        } finally { em.close(); }
    }
}