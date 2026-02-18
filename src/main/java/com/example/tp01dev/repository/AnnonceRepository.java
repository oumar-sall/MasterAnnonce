package com.example.tp01dev.repository;

import com.example.tp01dev.model.Annonce;
import com.example.tp01dev.model.AnnonceStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AnnonceRepository {

    private static final Logger logger = LoggerFactory.getLogger(AnnonceRepository.class);

    public void save(EntityManager em, Annonce a) {
        if (a.getId() == null) {
            logger.debug("Persistance d'une nouvelle annonce en base.");
            em.persist(a);
        } else {
            logger.debug("Mise à jour (merge) de l'annonce ID: {}", a.getId());
            em.merge(a);
        }
    }

    public void delete(EntityManager em, Long id) {
        Annonce a = em.find(Annonce.class, id);
        if (a != null) {
            logger.info("Suppression physique de l'annonce ID: {} de la base de données.", id);
            em.remove(a);
        } else {
            logger.warn("Tentative de suppression : annonce ID {} introuvable en base.", id);
        }
    }

    // Recherche avancée avec Pagination pour l'API REST
    public List<Annonce> findAdvanced(EntityManager em, String kw, Long catId, AnnonceStatus status, int page, int size) {
        StringBuilder jpql = new StringBuilder("SELECT a FROM Annonce a WHERE 1=1");

        if (kw != null && !kw.isEmpty()) jpql.append(" AND (a.title LIKE :kw OR a.description LIKE :kw)");
        if (catId != null) jpql.append(" AND a.category.id = :catId");
        if (status != null) jpql.append(" AND a.status = :status");

        jpql.append(" ORDER BY a.date DESC");

        logger.debug("Construction JPQL : {}", jpql);

        TypedQuery<Annonce> query = em.createQuery(jpql.toString(), Annonce.class);
        if (kw != null && !kw.isEmpty()) query.setParameter("kw", "%" + kw + "%");
        if (catId != null) query.setParameter("catId", catId);
        if (status != null) query.setParameter("status", status);

        // Application de la pagination
        int offset = (page - 1) * size;
        query.setFirstResult(offset);
        query.setMaxResults(size);

        logger.debug("Exécution requête paginée - Offset: {}, Limit: {}", offset, size);

        List<Annonce> results = query.getResultList();
        logger.debug("{} résultats trouvés pour cette page.", results.size());

        return results;
    }
}