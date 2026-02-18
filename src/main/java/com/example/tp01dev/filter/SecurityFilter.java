package com.example.tp01dev.filter;

import com.example.tp01dev.security.TokenStore;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@Provider
public class SecurityFilter implements ContainerRequestFilter {

    // Initialisation du logger statique
    private static final Logger logger = LoggerFactory.getLogger(SecurityFilter.class);

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String path = requestContext.getUriInfo().getPath();
        String method = requestContext.getMethod();

        // Log de debug pour voir chaque requête qui passe dans le filtre
        logger.debug("Interception de la requête : {} {}", method, path);

        // 1. On laisse passer le login et les GET (consultation libre)
        if (path.equals("login") || method.equalsIgnoreCase("GET")) {
            logger.debug("Accès libre autorisé pour le chemin : {}", path);
            return;
        }

        // 2. On bloque le reste si le token n'est pas valide
        String authHeader = requestContext.getHeaderString("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.warn("Tentative d'accès non autorisé (Header Authorization manquant ou mal formé) sur : {}", path);
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Accès refusé : Token manquant.")
                    .build());
            return;
        }

        String token = authHeader.substring(7);
        if (!TokenStore.isValid(token)) {
            logger.error("Token invalide ou expiré utilisé pour la ressource : {}", path);
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Accès refusé : Token invalide.")
                    .build());
        } else {
            logger.info("Authentification réussie pour la requête : {} {}", method, path);
        }
    }
}