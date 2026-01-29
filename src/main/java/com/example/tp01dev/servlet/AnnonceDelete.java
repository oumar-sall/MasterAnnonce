package com.example.tp01dev.servlet;

import com.example.tp01dev.dao.AnnonceDAO;
import com.example.tp01dev.model.Annonce;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/AnnonceDelete")
public class AnnonceDelete extends HttpServlet {

    private AnnonceDAO dao = new AnnonceDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Récupération du parametre “id” de l’URL
        String idParam = request.getParameter("id");

        if (idParam != null) {
            int id = Integer.parseInt(idParam);

            // 2. Préparation de l'objet pour la suppression
            Annonce a = new Annonce();
            a.setId(id);

            // 3. Appel du DAO
            dao.delete(a);
        }

        // 4. Retour automatique à la liste
        response.sendRedirect("AnnonceList");
    }
}
