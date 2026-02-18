package com.example.tp01dev.repository;

import com.example.tp01dev.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserRepository {

    private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);

    // On reçoit l'EntityManager en paramètre
    public void save(EntityManager em, User u) {
        logger.debug("Tentative de persistance de l'utilisateur : {}", u.getUsername());
        try {
            em.persist(u);
            logger.info("Utilisateur '{}' persisté avec succès en base de données.", u.getUsername());
        } catch (Exception e) {
            logger.error("Erreur critique lors de la persistance de l'utilisateur '{}'", u.getUsername(), e);
            throw e;
        }
    }

    public User findByUsername(EntityManager em, String username) {
        logger.debug("Recherche de l'utilisateur par username : {}", username);
        try {
            User user = em.createQuery("SELECT u FROM User u WHERE u.username = :uname", User.class)
                    .setParameter("uname", username)
                    .getSingleResult();
            logger.debug("Utilisateur '{}' trouvé (ID: {}).", username, user.getId());
            return user;
        } catch (NoResultException e) {
            logger.debug("Aucun utilisateur trouvé pour le username : {}", username);
            return null;
        } catch (Exception e) {
            logger.error("Erreur lors de la requête de recherche pour l'utilisateur : {}", username, e);
            throw e;
        }
    }
}