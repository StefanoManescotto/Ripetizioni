<%--
  Created by IntelliJ IDEA.
  User: MSI
  Date: 12/11/2022
  Time: 08:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>AggiungiDocenteForm</title>
</head>
    <body>
        <form action="aggiungiDocente" method="get">
            <label>Nome Docente</label>
            <input type="text" name="nomeDocente" required>
            <br>
            <label>Cognome Docente</label>
            <input type="text" name="cognomeDocente" required>
            <label>Email Utente</label>
            <input type="email" name="emailUtente" required>
            <label>Password</label>
            <input type="password" name="password" required>
            <p><input type="submit" name="submit" value="OK"></p>
        </form>
    </body>
</html>
