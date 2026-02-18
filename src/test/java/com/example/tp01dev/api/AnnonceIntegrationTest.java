package com.example.tp01dev.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
public class AnnonceIntegrationTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8080/tp01Dev/api";
    }

    @Test
    @DisplayName("Point 5 : Test Erreur - Accès sans Token (401)")
    void testCreateAnnonceUnauthorized() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"title\":\"Interdit\"}")
                .when()
                .post("/annonces")
                .then()
                .statusCode(401); // CE TEST SERA VERT ET VALIDE TA SÉCURITÉ
    }

    @Test
    @DisplayName("Point 5 : Test Erreur - Ressource Inconnue (404)")
    void testNotFound() {
        given()
                .when()
                .get("/annonces/999999")
                .then()
                .statusCode(404); // CE TEST SERA VERT ET VALIDE TA GESTION D'EXCEPTION
    }
}