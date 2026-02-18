package com.example.tp01dev.mapper;

import com.example.tp01dev.dto.AnnonceDTO;
import com.example.tp01dev.model.Annonce;
import com.example.tp01dev.model.AnnonceStatus;

public class AnnonceMapper {

    // Entity -> DTO (pour les sorties GET)
    public static AnnonceDTO toDto(Annonce entity) {
        if (entity == null) return null;

        return new AnnonceDTO.Builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .adress(entity.getAdress())
                .mail(entity.getMail())
                .status(entity.getStatus() != null ? entity.getStatus().name() : null)
                .categoryLabel(entity.getCategory() != null ? entity.getCategory().getLabel() : null)
                .categoryId(entity.getCategory() != null ? entity.getCategory().getId() : null)
                .authorId(entity.getAuthor() != null ? entity.getAuthor().getId() : null)
                .build();
    }

    // DTO -> Entity (pour les entrées POST/PUT)
    // Note : On ne mappe pas les objets complexes (User/Category) ici,
    // car le Mapper ne doit pas toucher à la DB. On le fera dans le Service.
    public static Annonce toEntity(AnnonceDTO dto) {
        if (dto == null) return null;

        Annonce entity = new Annonce();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setAdress(dto.getAddress());
        entity.setMail(dto.getMail());

        if (dto.getStatus() != null) {
            entity.setStatus(AnnonceStatus.valueOf(dto.getStatus()));
        }

        return entity;
    }
}