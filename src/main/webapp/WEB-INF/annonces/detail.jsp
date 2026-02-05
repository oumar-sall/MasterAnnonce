<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>${annonce.title}</title>
</head>
<body>
<h1>${annonce.title}</h1>

<p><strong>Prix / Détails :</strong> ${annonce.description}</p>
<p><strong>Adresse :</strong> ${annonce.adress}</p>
<p><strong>Contact :</strong> ${annonce.mail}</p>
<hr>
<p><strong>Catégorie :</strong> ${annonce.category.label}</p>
<p><strong>Publiée par :</strong> ${annonce.author.username}</p>
<p><strong>Date :</strong> <fmt:formatDate value="${annonce.date}" pattern="dd/MM/yyyy HH:mm" /></p>
<p><strong>Statut :</strong> ${annonce.status}</p>

<hr>
<a href="${pageContext.request.contextPath}/annonces">Retour à la liste</a>

<c:if test="${sessionScope.loggedUser.id == annonce.author.id}">
    <div style="margin-top: 20px; padding: 10px; background: #f0f0f0;">
        <h3>Actions Propriétaire :</h3>
        <a href="${pageContext.request.contextPath}/annonces/form?id=${annonce.id}">Modifier</a> |
        <a href="${pageContext.request.contextPath}/annonces/action?id=${annonce.id}&type=publish">Publier</a> |
        <a href="${pageContext.request.contextPath}/annonces/action?id=${annonce.id}&type=archive">Archiver</a>
    </div>
</c:if>
<a href="annonces" class="btn btn-list">Consulter les annonces</a>
</body>
</html>