package com.example.tp01dev.api;

import com.example.tp01dev.dto.AnnonceDTO;
import com.example.tp01dev.model.AnnonceStatus;
import com.example.tp01dev.service.AnnonceService;
import com.example.tp01dev.security.TokenStore;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Path("/annonces")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AnnonceRessource {

    private static final Logger logger = LoggerFactory.getLogger(AnnonceRessource.class);
    private AnnonceService annonceService = new AnnonceService();

    @Context
    private ContainerRequestContext crc;

    private String getCurrentUserEmail() {
        String authHeader = crc.getHeaderString("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return TokenStore.getEmail(authHeader.substring(7));
        }
        return null;
    }

    @GET
    public Response getAll(
            @QueryParam("kw") String kw,
            @QueryParam("catId") Long catId,
            @QueryParam("status") AnnonceStatus status,
            @QueryParam("page") @DefaultValue("1") int page) {

        logger.info("Requête GET /annonces - Filters: [kw={}, catId={}, status={}, page={}]", kw, catId, status, page);
        List<AnnonceDTO> results = annonceService.listerAnnoncesDTO(kw, catId, status, page);
        logger.debug("Retour de {} annonces", results.size());

        return Response.ok(results).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        logger.info("Requête GET /annonces/{}", id);
        AnnonceDTO dto = annonceService.getDtoById(id);
        return Response.ok(dto).build();
    }

    @POST
    public Response create(@Valid AnnonceDTO dto) {
        logger.info("Requête POST /annonces - Titre: '{}'", dto.getTitle());
        AnnonceDTO created = annonceService.creerAnnonce(dto);
        logger.info("Annonce créée avec succès, ID: {}", created.getId());
        return Response.status(Response.Status.CREATED).entity(created).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid AnnonceDTO dto) {
        String email = getCurrentUserEmail();
        logger.info("Requête PUT /annonces/{} par l'utilisateur: {}", id, email);
        AnnonceDTO updated = annonceService.modifierAnnonce(id, dto, email);
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        String email = getCurrentUserEmail();
        logger.info("Requête DELETE /annonces/{} par l'utilisateur: {}", id, email);
        annonceService.supprimerAnnonce(id, email);
        logger.info("Annonce {} supprimée avec succès", id);
        return Response.noContent().build();
    }

    @PATCH
    @Path("/{id}")
    public Response partialUpdate(@PathParam("id") Long id, @Valid AnnonceDTO dto) {
        String email = getCurrentUserEmail();
        logger.info("Requête PATCH /annonces/{} par l'utilisateur: {}", id, email);
        AnnonceDTO patched = annonceService.patchAnnonce(id, dto, email);
        return Response.ok(patched).build();
    }
}