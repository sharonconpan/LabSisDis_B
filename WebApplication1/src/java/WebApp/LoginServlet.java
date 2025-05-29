package WebApp;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {

    private final String userCorrecto = "admin";
    private final String passCorrecta = "1234";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String user = request.getParameter("username");
            String pass = request.getParameter("password");

            ArrayList<String> mensajes = new ArrayList<>();

            if (user == null || pass == null || user.isEmpty() || pass.isEmpty()) {
                mensajes.add("Usuario o contraseña no deben estar vacíos.");
                throw new IllegalArgumentException("Campos vacíos");
            }

            if (!user.equals(userCorrecto) || !pass.equals(passCorrecta)) {
                mensajes.add("Usuario o contraseña incorrectos.");
                throw new SecurityException("Credenciales inválidas");
            }

            // Autenticación exitosa
            request.setAttribute("usuario", user);
            request.getRequestDispatcher("bienvenida.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("javax.servlet.jsp.jspException", e); // para JSP 2.3+
            request.setAttribute("mensajes", request.getAttribute("mensajes"));
            request.getRequestDispatcher("errorPage.jsp").forward(request, response);
        }
    }
}
