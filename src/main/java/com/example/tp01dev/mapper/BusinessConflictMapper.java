package com.example.tp01dev.mapper;

import com.example.tp01dev.exception.BusinessConflictException;
import com.example.tp01dev.shared.ErrorResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.List;

@Provider
public class BusinessConflictMapper implements ExceptionMapper<BusinessConflictException> {
    @Override
    public Response toResponse(BusinessConflictException ex) {
        return Response.status(Response.Status.CONFLICT)
                .entity(new ErrorResponse("BUSINESS_CONFLICT", List.of(ex.getMessage())))
                .build();
    }
}