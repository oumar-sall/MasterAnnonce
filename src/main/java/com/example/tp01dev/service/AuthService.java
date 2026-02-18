package com.example.tp01dev.service;

import com.example.tp01dev.security.TokenStore;
import com.example.tp01dev.model.User;
import com.example.tp01dev.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    public String authenticate(String email, String password) {
        logger.info("Tentative de connexion pour l'utilisateur : {}", email);
        EntityManager em = JPAUtil.getEntityManager();

        try {
            // 1. Chercher l'utilisateur par email
            User user = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();

            // 2. Vérifier le mot de passe
            if (user.getPassword().equals(password)) {
                logger.info("Authentification réussie pour : {}", email);
                String token = TokenStore.generateToken(email);
                logger.debug("Token généré avec succès pour {}", email);
                return token;
            } else {
                logger.warn("Échec d'authentification : mot de passe incorrect pour {}", email);
            }

        } catch (NoResultException e) {
            logger.warn("Échec d'authentification : aucun utilisateur trouvé avec l'email {}", email);
        } catch (Exception e) {
            logger.error("Erreur technique imprévue lors de l'authentification de {}", email, e);
        } finally {
            em.close();
        }

        return null;
    }
}