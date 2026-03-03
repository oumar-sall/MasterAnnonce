package com.example.tp04dev.service;

import com.example.tp04dev.dto.AnnonceDTO;
import com.example.tp04dev.mapper.AnnonceMapper;
import com.example.tp04dev.repository.AnnonceRepository;
import com.example.tp04dev.model.Annonce;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // Génère le constructeur pour l'injection
public class AnnonceService {
    private final AnnonceRepository repository;
    private final AnnonceMapper mapper;

    public Page<AnnonceDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toDto);
    }

    public AnnonceDTO create(AnnonceDTO dto) {
        Annonce entity = mapper.toEntity(dto);
        return mapper.toDto(repository.save(entity));
    }

    public AnnonceDTO update(Long id, AnnonceDTO dto) {
        Annonce existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Annonce non trouvée"));
        mapper.updateEntityFromDto(dto, existing);
        return mapper.toDto(repository.save(existing));
    }

    public AnnonceDTO findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDto) // Convertit l'entité en DTO si elle existe
                .orElseThrow(() -> new EntityNotFoundException("Annonce avec l'ID " + id + " non trouvée"));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Impossible de supprimer : Annonce non trouvée");
        }
        repository.deleteById(id);
    }
}