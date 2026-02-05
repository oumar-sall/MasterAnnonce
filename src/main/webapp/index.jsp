<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/styles/style.css'/>">
</head>
<body>
    <h1><%= "Hello World!" %>
    </h1>
    <br/>
    <a href="hello-servlet">Hello Servlet</a>
    <form action="hello-servlet" method="GET">
        <label for="nom">Entrez votre nom :</label>
        <input type="text" id="nom" name="nom" placeholder="Votre nom" required>
        <input type="submit" value="Envoyer">
    </form>

    <div class="container">
        <h1>Bienvenue sur MasterAnnonce</h1>
        <p>Gestionnaire d'annonces en Java EE / Jakarta EE</p>

        <hr>

        <a href="annonces" class="btn btn-list">Consulter les annonces</a>

        <a href="login" class="btn btn-add">Se connecter</a>
    </div>

</body>
</html>