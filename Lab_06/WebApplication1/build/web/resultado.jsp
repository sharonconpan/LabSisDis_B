<%-- 
    Document   : resultado
    Created on : 29 may 2025, 0:47:29
    Author     : LENOVO
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Resultado</title>
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
        <h2>Resumen de la Compra</h2>
        <p>Total a pagar: $<%= request.getAttribute("total") %></p>
        <a href="index.jsp">Realizar otra compra</a>
    </body>
</html>

