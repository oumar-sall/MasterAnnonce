package com.example.tp04dev.dto;

import lombok.*;

@Data // @Data génère Getter, Setter, RequiredArgsConstructor, toString, equals et hashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnnonceDTO {
    private Long id;
    private String titre;
    private String description;
    private Double prix;

    // Vérifie bien l'orthographe exacte ici :
    private Long categoryId;
    private Long authorId;
}