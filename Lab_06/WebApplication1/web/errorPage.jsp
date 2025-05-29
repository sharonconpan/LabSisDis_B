<%-- 
    Document   : errorPage
    Created on : 28 may 2025, 23:28:42
    Author     : LENOVO
--%>

<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title>Error</title>
        <style>
    body {
        font-family: 'Segoe UI', sans-serif;
        background-color: #f2f2f2;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        height: 100vh;
        margin: 0;
    }

    form, .container {
        background-color: #ffffff;
        padding: 30px;
        border-radius: 10px;
        box-shadow: 0 5px 15px rgba(0,0,0,0.1);
        width: 320px;
    }

    h2 {
        color: #333333;
        text-align: center;
    }

    input[type="text"],
    input[type="password"],
    input[type="number"] {
        width: 100%;
        padding: 10px;
        margin: 8px 0;
        box-sizing: border-box;
        border: 1px solid #ccc;
        border-radius: 5px;
    }

    input[type="submit"] {
        background-color: #4CAF50;
        color: white;
        border: none;
        padding: 12px;
        width: 100%;
        border-radius: 5px;
        cursor: pointer;
        font-weight: bold;
    }

    input[type="submit"]:hover {
        background-color: #45a049;
    }

    a {
        display: block;
        text-align: center;
        margin-top: 15px;
        color: #2196F3;
        text-decoration: none;
    }

    a:hover {
        text-decoration: underline;
    }

    ul {
        color: red;
        padding-left: 20px;
    }

    p {
        text-align: center;
        color: #444;
    }
</style>

    </head>
    <body>
        <h2>No se pudo ingresar</h2>

        <%-- Mostrar mensajes personalizados si existen --%>
        <%
            java.util.List<String> mensajes = (java.util.List<String>) request.getAttribute("mensajes");
            if (mensajes != null && !mensajes.isEmpty()) {
        %>
        <ul>
            <% for (String mensaje : mensajes) { %>
            <li><%= mensaje %></li>
                <% } %>
        </ul>
        <%
            } else if (exception != null) {
        %>
        <p>Excepción: <%= exception.getClass().getSimpleName() %></p>
        <p>Mensaje: <%= exception.getMessage() %></p>
        <% } else { %>
        <p>Credenciales incorrectas</p>
        <% } %>

        <a href="login.jsp">Volver al login</a>
    </body>
</html>

