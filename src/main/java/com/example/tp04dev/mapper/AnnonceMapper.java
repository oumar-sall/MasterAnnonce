package com.example.tp04dev.mapper;

import com.example.tp04dev.dto.AnnonceDTO;
import com.example.tp04dev.model.Annonce;
import org.mapstruct.*;

@Mapper(componentModel = "spring") // Permet l'injection via @Autowired
public interface AnnonceMapper {

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "author.id", target = "authorId")
    AnnonceDTO toDto(Annonce entity);

    @Mapping(source = "categoryId", target = "category.id")
    @Mapping(source = "authorId", target = "author.id")
    Annonce toEntity(AnnonceDTO dto);

    // Pour le PATCH (Mise à jour partielle)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(AnnonceDTO dto, @MappingTarget Annonce entity);
}