<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <title>JSP - Hello World</title>
    </head>
    <body>
        <h1><%= "Hello World!" %></h1>
        <br/>
        <a href="ripetizioni">Hello Servlet</a>
        <form action="aggiungiDocente" method="get">
            <input type="text" name="nomeAttributo">
            <p><input type="submit" name="submit" value="OK"></p>
        </form>
    </body>
</html>