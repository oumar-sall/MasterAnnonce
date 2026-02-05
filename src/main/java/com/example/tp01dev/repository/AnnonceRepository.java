package com.example.tp01dev.repository;

import com.example.tp01dev.model.Annonce;
import com.example.tp01dev.model.AnnonceStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class AnnonceRepository {

    // On passe l'EM en paramètre pour rester dans la même transaction
    public void save(EntityManager em, Annonce a) {
        if (a.getId() == null) em.persist(a);
        else em.merge(a);
    }

    public void delete(EntityManager em, Long id) {
        Annonce a = em.find(Annonce.class, id);
        if (a != null) em.remove(a);
    }

    // La lecture ne nécessite pas forcément d'être dans une transaction complexe
    public List<Annonce> findAdvanced(EntityManager em, String keyword, Long categoryId, AnnonceStatus status, int page, int size) {
        StringBuilder jpql = new StringBuilder("SELECT a FROM Annonce a WHERE 1=1");

        if (keyword != null && !keyword.isEmpty()) jpql.append(" AND (lower(a.title) LIKE lower(:kw))");
        if (categoryId != null) jpql.append(" AND a.category.id = :catId");
        if (status != null) jpql.append(" AND a.status = :status");

        TypedQuery<Annonce> query = em.createQuery(jpql.toString(), Annonce.class);

        if (keyword != null && !keyword.isEmpty()) query.setParameter("kw", "%" + keyword + "%");
        if (categoryId != null) query.setParameter("catId", categoryId);
        if (status != null) query.setParameter("status", status);

        return query.setFirstResult((page - 1) * size).setMaxResults(size).getResultList();
    }
}