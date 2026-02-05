<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<form action="form" method="post">
    <input type="hidden" name="id" value="${annonce.id}">

    <label>Titre :</label>
    <input type="text" name="title" value="${annonce.title}" required>

    <label>Description :</label>
    <textarea name="description">${annonce.description}</textarea>

    <label>Catégorie :</label>
    <select name="categoryId">
        <c:forEach var="cat" items="${categories}">
            <option value="${cat.id}" ${cat.id == annonce.category.id ? 'selected' : ''}>
                    ${cat.label}
            </option>
        </c:forEach>
    </select>

    <button type="submit">${empty annonce.id ? 'Créer' : 'Modifier'}</button>
</form>