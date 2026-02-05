package com.example.tp01dev.service;

import com.example.tp01dev.model.Annonce;
import com.example.tp01dev.model.AnnonceStatus;
import com.example.tp01dev.repository.AnnonceRepository;
import com.example.tp01dev.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class AnnonceService {
    private AnnonceRepository repo = new AnnonceRepository();

    public void enregistrerAnnonce(Annonce a) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            if (a.getId() == null) {
                // C'est une NOUVELLE annonce -> Statut DRAFT par défaut
                a.setStatus(AnnonceStatus.DRAFT);
            }
            // Si l'ID existe, on ne touche pas au statut,
            // JPA mettra à jour uniquement les champs modifiés (titre, desc, etc.)

            repo.save(em, a);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally { em.close(); }
    }

    // c. Publication
    public void publierAnnonce(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Annonce a = em.find(Annonce.class, id);
            if (a != null) a.setStatus(AnnonceStatus.PUBLISHED);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally { em.close(); }
    }

    // d. Archivage
    public void archiverAnnonce(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Annonce a = em.find(Annonce.class, id);
            if (a != null) a.setStatus(AnnonceStatus.ARCHIVED);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally { em.close(); }
    }

    // e. Suppression
    public void supprimerAnnonce(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            repo.delete(em, id);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally { em.close(); }
    }

    // f. Recherche et listing paginé
    public List<Annonce> listerAnnonces(String kw, Long catId, AnnonceStatus status, int page) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return repo.findAdvanced(em, kw, catId, status, page, 10);
        } finally { em.close(); }
    }

    public Annonce getById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Annonce.class, id);
        } finally { em.close(); }
    }

}