package com.example.tp04dev.repository;

import com.example.tp04dev.model.Annonce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnonceRepository extends JpaRepository<Annonce, Long> {

    List<Annonce> findByTitreContainingIgnoreCase(String keyword);

    // Option la plus adaptée : Faire correspondre le nom au champ de l'entité
    List<Annonce> findByAuthorId(Long authorId);
}