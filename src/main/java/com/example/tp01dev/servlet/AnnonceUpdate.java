package com.example.tp01dev.servlet;

import com.example.tp01dev.dao.AnnonceDAO;
import com.example.tp01dev.model.Annonce;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/AnnonceUpdate")
public class AnnonceUpdate extends HttpServlet {

    private AnnonceDAO dao = new AnnonceDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Récupération du paramètre "id" de l'URL
        String idParam = request.getParameter("id");

        if (idParam != null) {
            int id = Integer.parseInt(idParam);
            // 2. Recherche de l'annonce correspondante
            Annonce annonce = dao.find(id);

            if (annonce != null) {
                request.setAttribute("annonce", annonce);
                this.getServletContext().getRequestDispatcher("/AnnonceUpdate.jsp").forward(request, response);
                return;
            }
        }
        response.sendRedirect("AnnonceList");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupération des données du formulaire
        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String adress = request.getParameter("adress");
        String mail = request.getParameter("mail");

        // Création de l'objet mis à jour
        Annonce a = new Annonce();
        a.setId(id);
        a.setTitle(title);
        a.setDescription(description);
        a.setAdress(adress);
        a.setMail(mail);

        // Mise à jour en base
        if (dao.update(a)) {
            response.sendRedirect("AnnonceList");
        } else {
            request.setAttribute("error", "Erreur lors de la mise à jour.");
            doGet(request, response);
        }
    }
}
