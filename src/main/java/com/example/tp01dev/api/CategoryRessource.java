package com.example.tp01dev.api;

import com.example.tp01dev.model.Category;
import com.example.tp01dev.service.CategoryService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Path("/categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoryRessource {

    private static final Logger logger = LoggerFactory.getLogger(CategoryRessource.class);
    private CategoryService categoryService = new CategoryService();

    @GET
    public Response getAll() {
        logger.info("Requête GET /categories - Récupération de la liste complète");
        List<Category> categories = categoryService.listerToutes();
        logger.debug("Nombre de catégories renvoyées : {}", categories.size());
        return Response.ok(categories).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        logger.info("Requête GET /categories/{} - Recherche par ID", id);
        Category cat = categoryService.trouverParId(id);

        if (cat == null) {
            logger.warn("Catégorie ID {} non trouvée", id);
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Catégorie introuvable")
                    .build();
        }

        return Response.ok(cat).build();
    }
}