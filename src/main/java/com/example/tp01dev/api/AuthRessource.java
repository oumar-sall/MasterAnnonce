package com.example.tp01dev.api;

import com.example.tp01dev.service.AuthService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@Path("/login")
public class AuthRessource {

    private static final Logger logger = LoggerFactory.getLogger(AuthRessource.class);
    private AuthService authService = new AuthService();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(Map<String, String> credentials) {
        String email = credentials.get("email");
        logger.info("Tentative de login reçue pour l'email : {}", email);

        String token = authService.authenticate(
                email,
                credentials.get("password")
        );

        if (token != null) {
            logger.info("Connexion réussie pour l'utilisateur : {}", email);
            return Response.ok(Map.of("token", token)).build(); // HTTP 200
        }

        // En cas d'échec, on logue un warning pour la sécurité
        logger.warn("Échec de connexion : identifiants invalides pour l'email : {}", email);

        return Response.status(Response.Status.UNAUTHORIZED)
                .entity(Map.of("error", "Email ou mot de passe incorrect"))
                .build(); // HTTP 401
    }
}