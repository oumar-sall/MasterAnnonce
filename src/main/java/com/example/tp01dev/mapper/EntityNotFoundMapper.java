package com.example.tp01dev.mapper;

import com.example.tp01dev.exception.EntityNotFoundException;
import com.example.tp01dev.shared.ErrorResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.List;

@Provider
public class EntityNotFoundMapper implements ExceptionMapper<EntityNotFoundException> {
    @Override
    public Response toResponse(EntityNotFoundException ex) {
        ErrorResponse response = new ErrorResponse("NOT_FOUND", List.of(ex.getMessage()));
        return Response.status(Response.Status.NOT_FOUND).entity(response).build();
    }
}