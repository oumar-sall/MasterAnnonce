package com.example.tp01dev.servlet;

import com.example.tp01dev.model.Annonce;
import com.example.tp01dev.model.AnnonceStatus;
import com.example.tp01dev.model.Category;
import com.example.tp01dev.service.AnnonceService;
import com.example.tp01dev.service.CategoryService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/annonces")
public class AnnonceListServlet extends HttpServlet {
    private AnnonceService annonceService = new AnnonceService();
    private CategoryService categoryService = new CategoryService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Récupération des paramètres de recherche
        String kw = request.getParameter("kw");
        String catStr = request.getParameter("catId");
        Long catId = (catStr != null && !catStr.isEmpty()) ? Long.parseLong(catStr) : null;

        // 2. Gestion de la pagination
        int page = 1;
        String pageStr = request.getParameter("page");
        if (pageStr != null) page = Integer.parseInt(pageStr);

        // 3. Récupération des données
        // On affiche par défaut les annonces PUBLISHED pour tout le monde
        List<Annonce> annonces = annonceService.listerAnnonces(kw, catId, AnnonceStatus.PUBLISHED, page);
        List<Category> categories = categoryService.listerToutes();

        // 4. Transmission à la JSP
        request.setAttribute("annonces", annonces);
        request.setAttribute("categories", categories);
        request.setAttribute("currentPage", page);

        request.getRequestDispatcher("/WEB-INF/annonces/liste.jsp").forward(request, response);
    }
}