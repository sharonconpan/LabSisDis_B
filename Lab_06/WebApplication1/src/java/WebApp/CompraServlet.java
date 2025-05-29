
package WebApp;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "CompraServlet", value = "/CompraServlet")
public class CompraServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int cantidadA = Integer.parseInt(request.getParameter("cantidadA"));
            int cantidadB = Integer.parseInt(request.getParameter("cantidadB"));
            int cantidadC = Integer.parseInt(request.getParameter("cantidadC"));

            if (cantidadA < 0 || cantidadB < 0 || cantidadC < 0) {
                ArrayList<String> mensajes = new ArrayList<>();
                mensajes.add("Lo siento, ingrese una cantidad positiva.");
                request.setAttribute("mensajes", mensajes);
                throw new IllegalArgumentException("Cantidad negativa ingresada");
            }

            double total = cantidadA * 10.0 + cantidadB * 20.0 + cantidadC * 15.5;

            request.setAttribute("total", total);
            RequestDispatcher rd = request.getRequestDispatcher("resultado.jsp");
            rd.forward(request, response);
        } catch (Exception e) {
            request.setAttribute("javax.servlet.jsp.jspException", e);
            request.getRequestDispatcher("errorPage.jsp").forward(request, response);
        }
    }
}

