<%--
  Created by IntelliJ IDEA.
  User: pc
  Date: 29/01/2026
  Time: 16:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.example.tp01dev.model.Annonce" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
  <title>Liste des Annonces</title>
  <link rel="stylesheet" type="text/css" href="<c:url value='/styles/style.css'/>">
</head>
<body>

<h2>Liste des annonces disponibles</h2>

<a href="AnnonceAdd" class="btn-add">Déposer une nouvelle annonce</a>

<table>
  <thead>
  <tr>
    <th>ID</th>
    <th>Titre</th>
    <th>Description</th>
    <th>Adresse</th>
    <th>Email</th>
    <th>Date</th>
  </tr>
  </thead>
  <tbody>
  <c:forEach items="${annonces}" var="annonce">
    <tr>
      <td><c:out value="${annonce.id}"/></td>
      <td><c:out value="${annonce.title}"/></td>
      <td><c:out value="${annonce.description}"/></td>
      <td><c:out value="${annonce.adress}"/></td>
      <td><c:out value="${annonce.mail}"/></td>
      <td>
        <a href="AnnonceUpdate?id=${annonce.id}" style="color: #007bff;">Modifier</a> |
        <a href="AnnonceDelete?id=${annonce.id}" style="color: #dc3545;" onclick="return confirm('Sûr ?')">Supprimer</a>
      </td>
    </tr>
  </c:forEach>
  </tbody>
</table>

</body>
</html>
