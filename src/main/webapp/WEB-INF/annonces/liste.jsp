<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:forEach var="a" items="${annonces}">
    <div class="annonce-card">
        <h3>${a.title}</h3>
        <p>${a.description}</p>
        <p>Catégorie : ${a.category.label}</p>

        <c:if test="${sessionScope.loggedUser.id == a.author.id}">
            <a href="annonces/action?id=${a.id}&type=publish">Publier</a>
            <a href="annonces/action?id=${a.id}&type=archive">Archiver</a>
            <a href="annonces/edit?id=${a.id}">Modifier</a>
        </c:if>
    </div>
</c:forEach>

<div class="pagination">
    <a href="?page=${currentPage - 1}">Précédent</a>
    <span>Page ${currentPage}</span>
    <a href="?page=${currentPage + 1}">Suivant</a>

    <a href="annonces/form" class="btn btn-add">Déposer une annonce</a>
</div>