Ce projet est une application web de gestion d'annonces développée dans le cadre du module de développement Java EE. Elle permet de lister, créer, modifier et filtrer des annonces par catégorie et statut.

🚀 Fonctionnalités
Gestion des Annonces : Création (statut DRAFT par défaut), Edition et Affichage.

Authentification : Système de session pour identifier l'auteur de chaque annonce.

Persistance JPA/Hibernate : Stockage des données dans une base PostgreSQL.

Filtrage & Pagination : Navigation fluide dans la liste des annonces.

Catégories : Gestion des catégories via une relation @ManyToOne.

🛠️ Technologies utilisées
Java 21

Jakarta EE 11 (Servlets, JSP, JSTL)

Hibernate 6 (JPA)

PostgreSQL

Tomcat 11

⚙️ Configuration du projet
1. Base de données
Assurez-vous d'avoir une instance PostgreSQL active.

Créez une base de données nommée tp01dev.

Modifiez le fichier src/main/resources/META-INF/persistence.xml avec vos identifiants :

XML
<property name="jakarta.persistence.jdbc.url" value="jdbc:postgresql://localhost:5432/tp01dev"/>
<property name="jakarta.persistence.jdbc.user" value="VOTRE_LOGIN"/>
<property name="jakarta.persistence.jdbc.password" value="VOTRE_MDP"/>
2. Données initiales (SQL)
Pour tester l'application, exécutez ces scripts d'initialisation dans votre outil SQL (pgAdmin) :

SQL
-- Création d'un utilisateur de test
INSERT INTO users (username, password, email) VALUES ('admin', 'admin123', 'admin@example.com');

-- Création des catégories
INSERT INTO category (label) VALUES ('Immobilier'), ('Véhicules'), ('Emploi');
🏃 Lancement
Importez le projet sous IntelliJ IDEA (Maven project).

Configurez un serveur Tomcat 11.

Déployez l'artefact tp01Dev:war exploded.

Accédez à l'application via : http://localhost:8080/tp01Dev/login

📂 Structure du code
com.example.tp01dev.model : Entités JPA (Annonce, User, Category).

com.example.tp01dev.repository : Couche d'accès aux données (DAO).

com.example.tp01dev.service : Logique métier et gestion des transactions.

com.example.tp01dev.servlet : Contrôleurs gérant les requêtes HTTP.

com.example.tp01dev.util : Utilitaires (JPAUtil).
