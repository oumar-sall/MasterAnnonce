package com.example.tp01dev.servlet;

import com.example.tp01dev.dao.AnnonceDAO;
import com.example.tp01dev.model.Annonce;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet chargée d'afficher le formulaire de saisie d'annonce.
 * L'URL d'accès sera : http://localhost:8080/TonProjet/AnnonceAdd
 */
@WebServlet("/AnnonceAdd")
public class AnnonceAdd extends HttpServlet {

    /**
     * La méthode doGet est appelée lors d'un accès simple à l'URL.
     * Elle se contente de déléguer l'affichage à la JSP.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Dispatcher vers la JSP située dans WebContent (ou webapp)
        this.getServletContext()
                .getRequestDispatcher("/AnnonceAdd.jsp")
                .forward(request, response);
    }

    /**
     * La méthode doPost est appelée lors de la validation du formulaire.
     * Pour l'instant, on laisse le corps vide, on s'en occupera à l'étape 5.b.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String adress = request.getParameter("adress");
        String mail = request.getParameter("mail");

        if (title == null || title.isEmpty() ||
            description == null || description.isEmpty() ||
            adress == null || adress.isEmpty() ||
            mail == null || mail.isEmpty()) {
            request.setAttribute("error", "Tous les champs sont obligatoires !");
            this.getServletContext().getRequestDispatcher("/AnnonceAdd.jsp").forward(request, response);
        }

        Annonce newAnnonce = new Annonce();
        newAnnonce.setTitle(title);
        newAnnonce.setDescription(description);
        newAnnonce.setAdress(adress);
        newAnnonce.setMail(mail);

        AnnonceDAO dao = new AnnonceDAO();
        boolean success = dao.create(newAnnonce);

        if (success) {
            response.sendRedirect("AnnonceList");
        } else {
            request.setAttribute("error", "Erreur lors de l'insertion en base de donnée");
            this.getServletContext().getRequestDispatcher("/AnnonceAdd.jsp").forward(request, response);
        }
    }
}