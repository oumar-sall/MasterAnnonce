package com.example.tp01dev.ressource;

import com.example.tp01dev.dto.AnnonceDTO;
import com.example.tp01dev.api.AnnonceRessource;
import com.example.tp01dev.service.AnnonceService;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AnnonceRessourceTest {

    @Mock
    private AnnonceService annonceService;

    @InjectMocks
    private AnnonceRessource annonceResource;

    @Test
    @DisplayName("GET /annonces/{id} - Success")
    void testGetById_Success() {
        // GIVEN
        AnnonceDTO fakeDto = new AnnonceDTO();
        fakeDto.setId(1L);
        fakeDto.setTitle("Ordinateur Quantum Java 25");

        // On mocke la méthode getDtoById de ton AnnonceService
        when(annonceService.getDtoById(1L)).thenReturn(fakeDto);

        // WHEN
        Response response = annonceResource.getById(1L);

        // THEN
        assertEquals(200, response.getStatus());
        AnnonceDTO result = (AnnonceDTO) response.getEntity();
        assertEquals("Ordinateur Quantum Java 25", result.getTitle());
    }
}