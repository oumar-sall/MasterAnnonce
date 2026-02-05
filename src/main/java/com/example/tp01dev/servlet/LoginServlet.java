package com.example.tp01dev.servlet;

import com.example.tp01dev.model.User;
import com.example.tp01dev.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserService userService = new UserService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = request.getParameter("username");
        String pass = request.getParameter("password");

        User authenticatedUser = userService.authentifier(user, pass);

        if (authenticatedUser != null) {
            // On stocke l'utilisateur en session pour le reconnaître plus tard
            request.getSession().setAttribute("loggedUser", authenticatedUser);
            response.sendRedirect(request.getContextPath() + "/annonces");
        } else {
            request.setAttribute("error", "Login ou mot de passe incorrect");
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }
    }
}