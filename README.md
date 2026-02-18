# 📑 API de Petites Annonces - Projet Industrialisé

Ce projet est une application de gestion de petites annonces développée en **Jakarta EE**. Il a été conçu pour répondre aux exigences d'industrialisation (Exercice 10), incluant la traçabilité, la performance et la documentation.

---

## 🛠️ Stack Technique
* **Backend** : Jakarta EE 10 (JAX-RS, JPA, Bean Validation)
* **Serveur d'application** : Apache Tomcat 10+
* **Base de données** : PostgreSQL
* **Logging** : SLF4J + Logback
* **Tests** : JUnit 5, RestAssured

---

## 🚀 Installation et Lancement

### 1. Prérequis
* Java 17 ou supérieur
* Maven 3.8+
* Une base de données PostgreSQL active

### 2. Configuration Database
Modifiez le fichier `src/main/resources/META-INF/persistence.xml` avec vos accès :
```xml
<property name="jakarta.persistence.jdbc.url" value="jdbc:postgresql://localhost:5432/votre_db"/>
<property name="jakarta.persistence.jdbc.user" value="votre_utilisateur"/>
<property name="jakarta.persistence.jdbc.password" value="votre_mot_de_passe"/>

# Compiler et packager l'application (génère le .war)
mvn clean package

Déployez le fichier .war généré dans le dossier webapps de votre serveur Tomcat.

🧪 Stratégie de Tests
L'exécution des tests est séparée via les plugins Maven Surefire et Failsafe :

Tests Unitaires (mvn test) : Vérifient la logique des Mappers et des Services avec Mockito. Ils excluent les classes finissant par *IntegrationTest.

Tests d'Intégration (mvn verify) : Lancent les tests de bout en bout (RestAssured) nécessitant un serveur et une base de données.

Pourquoi cette séparation ? Cela permet un cycle de feedback rapide : les tests unitaires s'exécutent en quelques secondes à chaque modification, tandis que les tests d'intégration, plus lourds, sont réservés aux phases de validation finale.

J'avais eu un problème pour les tests d'endpoints. C'est pour ça que j'ai créer un fichier pour les plugins de mockito.

📊 Industrialisation
1. Logging Structuré (SLF4J)
L'application utilise SLF4J pour une gestion professionnelle des journaux :

INFO : Traces métier (ex: "Annonce ID 12 créée par l'utilisateur X").

WARN : Alertes de sécurité (ex: "Tentative de modification sans token valide").

ERROR : Capture des exceptions avec stacktrace complète pour le débuggage.

DEBUG : Détails techniques (requêtes JPQL générées, nombre de résultats).

2. Tests de Charge Simples
Inclus dans src/test/java/.../LoadTest.java. Ce test utilise un ExecutorService pour simuler des requêtes concurrentes massives. Il permet de valider la stabilité du pool de connexions JPA et la gestion des accès multi-threads.

3. Documentation API (OpenAPI)
L'API suit les standards OpenAPI. La documentation (Swagger) est accessible via l'endpoint :
GET /api/openapi.json (ou .yaml).
Elle détaille chaque ressource (/annonces, /categories, /login), les codes de retour HTTP (200, 201, 401, 404) et les schémas de données attendus.

🔐 Sécurité & Règles Métier
Authentification Stateless : Utilisation d'un SecurityFilter interceptant les headers Authorization: Bearer <token>.

Isolation des données : Un utilisateur ne peut modifier ou supprimer que ses propres annonces.

Cycle de vie :

Modification interdite si l'annonce est au statut PUBLISHED.

Suppression autorisée uniquement si l'annonce est préalablement ARCHIVED.