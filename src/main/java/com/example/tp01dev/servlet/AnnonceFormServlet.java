package com.example.tp01dev.servlet;

import com.example.tp01dev.model.Annonce;
import com.example.tp01dev.model.User;
import com.example.tp01dev.service.AnnonceService;
import com.example.tp01dev.service.CategoryService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/annonces/form")
public class AnnonceFormServlet extends HttpServlet {
    private AnnonceService annonceService = new AnnonceService();
    private CategoryService categoryService = new CategoryService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");

        // Si on a un ID, on charge l'annonce existante pour pré-remplir le formulaire
        if (idParam != null && !idParam.isEmpty()) {
            Long id = Long.parseLong(idParam);
            Annonce existingAnnonce = annonceService.getById(id);
            request.setAttribute("annonce", existingAnnonce);
        }

        // On charge les catégories pour la liste déroulante
        System.out.println(categoryService.listerToutes());
        request.setAttribute("categories", categoryService.listerToutes());

        // Vers le fichier JSP (Exercice 5.2.b & 5.2.c)
        request.getRequestDispatcher("/WEB-INF/annonces/formulaire.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String catIdParam = request.getParameter("categoryId");

        Annonce a;
        if (idParam != null && !idParam.isEmpty()) {
            // MODE MODIFICATION : on récupère l'objet attaché à la DB
            a = annonceService.getById(Long.parseLong(idParam));
        } else {
            // MODE CRÉATION : nouvel objet
            a = new Annonce();
            // Récupération de l'auteur via la session (Exercice 5.1.a)
            User author = (User) request.getSession().getAttribute("loggedUser");
            a.setAuthor(author);
        }

        // Mise à jour des données
        a.setTitle(title);
        a.setDescription(description);

        if (catIdParam != null) {
            a.setCategory(categoryService.trouverParId(Long.parseLong(catIdParam)));
        }

        // Appel du service (Exercice 4 : gère la transaction et le statut DRAFT si nouveau)
        annonceService.enregistrerAnnonce(a);

        // Redirection vers la liste
        response.sendRedirect(request.getContextPath() + "/annonces");
    }
}