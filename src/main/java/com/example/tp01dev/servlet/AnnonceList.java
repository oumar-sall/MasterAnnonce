package com.example.tp01dev.servlet;

import com.example.tp01dev.dao.AnnonceDAO;
import com.example.tp01dev.model.Annonce;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/AnnonceList")
public class AnnonceList extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Appel au DAO pour récupérer toutes les annonces
        AnnonceDAO dao = new AnnonceDAO();
        List<Annonce> listeAnnonces = dao.findAll();

        // 2. Stockage de la liste dans l'objet request pour la transmettre à la JSP
        request.setAttribute("annonces", listeAnnonces);

        // 3. Affichage de la JSP
        this.getServletContext().getRequestDispatcher("/AnnonceList.jsp").forward(request, response);
    }
}
