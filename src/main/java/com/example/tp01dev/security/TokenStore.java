package com.example.tp01dev.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TokenStore {

    private static final Logger logger = LoggerFactory.getLogger(TokenStore.class);

    // Stockage : Clé = Token, Valeur = Email de l'utilisateur
    private static final Map<String, String> tokens = new HashMap<>();

    public static String generateToken(String email) {
        String token = UUID.randomUUID().toString();
        tokens.put(token, email);

        // Log INFO pour tracer la session
        logger.info("Nouveau token généré pour l'utilisateur : {}", email);
        logger.debug("Détails du store - Nombre de sessions actives : {}", tokens.size());

        return token;
    }

    public static boolean isValid(String token) {
        boolean valid = tokens.containsKey(token);
        if (!valid) {
            logger.warn("Tentative de validation d'un token inconnu ou expiré.");
        } else {
            logger.debug("Validation réussie pour le token : {}...", token.substring(0, 8));
        }
        return valid;
    }

    /**
     * AJOUT : Permet de récupérer l'identité de l'utilisateur
     * à partir de son token pour les règles métier (Exo 7)
     */
    public static String getEmail(String token) {
        String email = tokens.get(token);
        if (email != null) {
            logger.debug("Identité récupérée pour le token : {}", email);
        } else {
            logger.warn("Impossible de trouver un email associé au token fourni.");
        }
        return email;
    }
}