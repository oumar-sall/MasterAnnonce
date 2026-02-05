<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Connexion</title>
</head>
<body>
<h2>Connexion</h2>

<%-- Affichage de l'erreur si l'authentification échoue --%>
<p style="color: red;">${error}</p>

<form action="login" method="post">
    <div>
        <label>Nom d'utilisateur :</label>
        <input type="text" name="username" required>
    </div>
    <div>
        <label>Mot de passe :</label>
        <input type="password" name="password" required>
    </div>
    <button type="submit">Se connecter</button>
</form>
</body>
</html>