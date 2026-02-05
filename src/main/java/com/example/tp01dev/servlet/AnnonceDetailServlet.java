package com.example.tp01dev.servlet;

import com.example.tp01dev.model.Annonce;
import com.example.tp01dev.service.AnnonceService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/annonces/detail")
public class AnnonceDetailServlet extends HttpServlet {
    private AnnonceService annonceService = new AnnonceService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");

        if (idParam != null && !idParam.isEmpty()) {
            Long id = Long.parseLong(idParam);
            Annonce annonce = annonceService.getById(id);

            if (annonce != null) {
                request.setAttribute("annonce", annonce);
                request.getRequestDispatcher("/WEB-INF/annonces/detail.jsp").forward(request, response);
                return;
            }
        }
        // Si pas d'ID ou annonce introuvable, retour à la liste
        response.sendRedirect(request.getContextPath() + "/annonces");
    }
}