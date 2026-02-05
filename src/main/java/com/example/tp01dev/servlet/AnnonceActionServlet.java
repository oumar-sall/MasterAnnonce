package com.example.tp01dev.servlet;

import com.example.tp01dev.service.AnnonceService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/annonces/action")
public class AnnonceActionServlet extends HttpServlet {
    private AnnonceService annonceService = new AnnonceService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idParam = request.getParameter("id");

        if (idParam != null && !idParam.isEmpty()){
            Long id = Long.parseLong(idParam);
            String action = request.getParameter("type"); // "publish" ou "archive"

            if ("publish".equals(action)) {
                annonceService.publierAnnonce(id);
            } else if ("archive".equals(action)) {
                annonceService.archiverAnnonce(id);
            }

            response.sendRedirect(request.getContextPath() + "/annonces");
        } else {
            response.sendRedirect(request.getContextPath() + "/annonces");
        }
    }
}