package com.example.tp01dev.config;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/api") // Préfixe pour toute l'api
public class RestApllication extends Application{
    // Cette classe reste vide, elle sert juste à activer JAX-RS sur le chemin /api
}
