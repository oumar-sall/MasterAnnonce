package com.example.tp01dev.service;

import com.example.tp01dev.dto.AnnonceDTO;
import com.example.tp01dev.exception.BusinessConflictException;
import com.example.tp01dev.exception.EntityNotFoundException;
import com.example.tp01dev.mapper.AnnonceMapper;
import com.example.tp01dev.model.*;
import com.example.tp01dev.repository.AnnonceRepository;
import com.example.tp01dev.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.OptimisticLockException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class AnnonceService {

    private static final Logger logger = LoggerFactory.getLogger(AnnonceService.class);
    private AnnonceRepository repo = new AnnonceRepository();

    public List<AnnonceDTO> listerAnnoncesDTO(String kw, Long catId, AnnonceStatus status, int page) {
        logger.debug("Recherche avancée d'annonces [kw: {}, cat: {}, status: {}, page: {}]", kw, catId, status, page);
        EntityManager em = JPAUtil.getEntityManager();
        try {
            List<Annonce> entities = repo.findAdvanced(em, kw, catId, status, page, 10);
            return entities.stream()
                    .map(AnnonceMapper::toDto)
                    .collect(Collectors.toList());
        } finally { em.close(); }
    }

    public AnnonceDTO getDtoById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Annonce a = em.find(Annonce.class, id);
            if (a == null) {
                logger.warn("Tentative d'accès à une annonce inexistante (ID: {})", id);
                throw new EntityNotFoundException("Annonce with id " + id + " not found");
            }
            return AnnonceMapper.toDto(a);
        } finally { em.close(); }
    }

    public AnnonceDTO creerAnnonce(AnnonceDTO dto) {
        logger.info("Tentative de création d'une nouvelle annonce : '{}'", dto.getTitle());
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Annonce a = AnnonceMapper.toEntity(dto);
            a.setStatus(AnnonceStatus.DRAFT);

            if (dto.getCategoryId() != null) {
                Category cat = em.find(Category.class, dto.getCategoryId());
                if (cat == null) {
                    logger.error("Échec création : Catégorie {} introuvable", dto.getCategoryId());
                    throw new EntityNotFoundException("Category not found");
                }
                a.setCategory(cat);
            }

            if (dto.getAuthorId() != null) {
                User author = em.find(User.class, dto.getAuthorId());
                if (author == null) {
                    logger.error("Échec création : Auteur {} introuvable", dto.getAuthorId());
                    throw new EntityNotFoundException("Author not found");
                }
                a.setAuthor(author);
            }

            repo.save(em, a);
            tx.commit();
            logger.info("Annonce créée avec succès (ID: {})", a.getId());
            return AnnonceMapper.toDto(a);
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            logger.error("Erreur technique lors de la création de l'annonce", e);
            throw e;
        } finally { em.close(); }
    }

    public AnnonceDTO modifierAnnonce(Long id, AnnonceDTO dto, String currentUserEmail) {
        logger.info("Utilisateur {} tente de modifier l'annonce ID: {}", currentUserEmail, id);
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Annonce a = em.find(Annonce.class, id);
            if (a == null) throw new EntityNotFoundException("Annonce not found");

            if (!a.getAuthor().getEmail().equals(currentUserEmail)) {
                logger.warn("Refus de modification : l'utilisateur {} n'est pas l'auteur de l'annonce {}", currentUserEmail, id);
                throw new BusinessConflictException("Action interdite : vous n'êtes pas l'auteur de cette annonce.");
            }

            if (a.getStatus() == AnnonceStatus.PUBLISHED) {
                logger.warn("Refus de modification : l'annonce {} est déjà publiée", id);
                throw new BusinessConflictException("Une annonce publiée ne peut plus être modifiée.");
            }

            a.setTitle(dto.getTitle());
            a.setDescription(dto.getDescription());
            a.setAdress(dto.getAddress());
            a.setMail(dto.getMail());

            if (dto.getCategoryId() != null) a.setCategory(em.find(Category.class, dto.getCategoryId()));

            repo.save(em, a);
            tx.commit();
            logger.info("Annonce ID: {} modifiée avec succès par {}", id, currentUserEmail);
            return AnnonceMapper.toDto(a);
        } catch (OptimisticLockException e) {
            if (tx.isActive()) tx.rollback();
            logger.warn("Concurrence détectée sur l'annonce ID: {} pour l'utilisateur {}", id, currentUserEmail);
            throw new BusinessConflictException("L'annonce a été modifiée par un autre utilisateur. Veuillez rafraîchir.");
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            logger.error("Erreur lors de la modification de l'annonce {}", id, e);
            throw e;
        } finally { em.close(); }
    }

    public void supprimerAnnonce(Long id, String currentUserEmail) {
        logger.info("Utilisateur {} demande la suppression de l'annonce ID: {}", currentUserEmail, id);
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Annonce a = em.find(Annonce.class, id);
            if (a == null) throw new EntityNotFoundException("Annonce not found");

            if (!a.getAuthor().getEmail().equals(currentUserEmail)) {
                logger.warn("Suppression refusée : {} n'est pas propriétaire de {}", currentUserEmail, id);
                throw new BusinessConflictException("Action interdite : vous n'êtes pas l'auteur.");
            }

            if (a.getStatus() != AnnonceStatus.ARCHIVED) {
                logger.warn("Suppression refusée : l'annonce {} n'est pas encore archivée", id);
                throw new BusinessConflictException("L'annonce doit être ARCHIVED avant d'être supprimée définitivement.");
            }

            repo.delete(em, id);
            tx.commit();
            logger.info("Annonce ID: {} supprimée définitivement par {}", id, currentUserEmail);
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            logger.error("Erreur lors de la suppression de l'annonce {}", id, e);
            throw e;
        } finally { em.close(); }
    }

    public AnnonceDTO patchAnnonce(Long id, AnnonceDTO dto, String currentUserEmail) {
        logger.info("PATCH partiel sur annonce ID: {} par {}", id, currentUserEmail);
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Annonce a = em.find(Annonce.class, id);
            if (a == null) throw new EntityNotFoundException("Annonce not found");

            if (!a.getAuthor().getEmail().equals(currentUserEmail)) {
                logger.warn("PATCH refusé : utilisateur {} non autorisé sur l'annonce {}", currentUserEmail, id);
                throw new BusinessConflictException("Action interdite.");
            }

            if (dto.getTitle() != null) a.setTitle(dto.getTitle());
            if (dto.getDescription() != null) a.setDescription(dto.getDescription());
            if (dto.getAddress() != null) a.setAdress(dto.getAddress());
            if (dto.getMail() != null) a.setMail(dto.getMail());

            if (dto.getStatus() != null) {
                logger.info("Changement de statut pour l'annonce ID: {} -> {}", id, dto.getStatus());
                a.setStatus(AnnonceStatus.valueOf(dto.getStatus()));
            }

            repo.save(em, a);
            tx.commit();
            return AnnonceMapper.toDto(a);
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            logger.error("Erreur lors du PATCH sur l'annonce {}", id, e);
            throw e;
        } finally { em.close(); }
    }
}