package com.example.tp01dev;

import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String nom = request.getParameter("nom");

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Hello Response</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; text-align: center; margin-top: 100px; }");
        out.println("h1 { color: #4CAF50; }");
        out.println("a { text-decoration: none; color: #2196F3; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");

        if (nom != null && !nom.trim().isEmpty()) {
            out.println("<h1>Hello the World suivi de votre nom, " + nom + "!</h1>" );
        } else {
            out.println("<h1>Hello the world suivi d'aucun nom !</h1>");
            out.println("<p>Aucun nom fourni.</p>");
        }

        out.println("<br><a href='index.jsp'>← Retour au formulaire</a>");
        out.println("</body>");
        out.println("</html>");
    }

    public void destroy() {
    }
}