<%--
  Created by IntelliJ IDEA.
  User: pc
  Date: 29/01/2026
  Time: 16:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.example.tp01dev.model.Annonce" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
  <title>Modifier l'annonce</title>
  <link rel="stylesheet" type="text/css" href="<c:url value='/styles/style.css'/>">
</head>
<body>
<h2>Modifier l'annonce</h2>

<form action="AnnonceUpdate" method="POST">
  <input type="hidden" name="id" value="${annonce.id}">

  <div class="form-group">
    <label>Titre :</label><br>
    <input type="text" name="title" value="${annonce.title}" required>
  </div>

  <div class="form-group">
    <label>Description :</label><br>
    <textarea name="description" required>${annonce.description}</textarea>
  </div>

  <div class="form-group">
    <label>Adresse :</label><br>
    <input type="text" name="adress" value="${annonce.adress}" required>
  </div>

  <div class="form-group">
    <label>Email :</label><br>
    <input type="email" name="mail" value="${annonce.mail}" required>
  </div>

  <div class="form-group" style="margin-top: 10px;">
    <button type="submit">Enregistrer les modifications</button>
    <a href="AnnonceList">Annuler</a>
  </div>
</form>
</body>
</html>
