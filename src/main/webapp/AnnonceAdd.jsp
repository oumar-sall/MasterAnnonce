<%--
  Created by IntelliJ IDEA.
  User: pc
  Date: 29/01/2026
  Time: 15:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Ajouter une Annonce</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/styles/style.css'/>">
</head>
<body>

<h2>Déposer une nouvelle annonce</h2>

<%-- L'attribut action pointe vers l'URL de la Servlet --%>
<form action="AnnonceAdd" method="POST">

    <div class="form-group">
        <label for="title">Titre :</label>
        <input type="text" id="title" name="title" required maxlength="64">
    </div>

    <div class="form-group">
        <label for="description">Description :</label>
        <textarea id="description" name="description" required maxlength="256"></textarea>
    </div>

    <div class="form-group">
        <label for="adress">Adresse :</label>
        <input type="text" id="adress" name="adress" required maxlength="64">
    </div>

    <div class="form-group">
        <label for="mail">Email :</label>
        <input type="email" id="mail" name="mail" required maxlength="64">
    </div>

    <button type="submit">Enregistrer l'annonce</button>

</form>

</body>
</html>
