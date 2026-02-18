package com.example.tp01dev.exception;

import com.example.tp01dev.shared.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.List;
import java.util.stream.Collectors;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        // On récupère tous les messages d'erreur des annotations Bean Validation
        List<String> errors = exception.getConstraintViolations().stream()
                .map(v -> v.getMessage())
                .collect(Collectors.toList());

        // On construit la réponse JSON normalisée
        ErrorResponse response = new ErrorResponse("VALIDATION_ERROR", errors);

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(response)
                .build();
    }
}