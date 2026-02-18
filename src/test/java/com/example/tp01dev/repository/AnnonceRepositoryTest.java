package com.example.tp01dev.repository;

import com.example.tp01dev.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class AnnonceRepositoryTest {

    private static EntityManagerFactory emf;
    private EntityManager em;
    private AnnonceRepository repo = new AnnonceRepository();

    @BeforeAll
    static void init() {
        // Charge le persistence.xml de src/test/resources (H2)
        emf = Persistence.createEntityManagerFactory("TestAnnoncePU");
    }

    @BeforeEach
    void setup() {
        em = emf.createEntityManager();
        em.getTransaction().begin();

        // NETTOYAGE : Évite les erreurs de doublons si la base n'est pas réinitialisée
        em.createQuery("DELETE FROM Annonce").executeUpdate();
        em.createQuery("DELETE FROM Category").executeUpdate();
        em.createQuery("DELETE FROM User").executeUpdate();

        // Point 6 : Chargement du jeu de données
        User author = new User();
        author.setEmail("test@univ.fr");
        author.setPassword("test1234"); // IMPORTANT : Évite l'erreur NOT NULL
        author.setUsername("testuser");
        em.persist(author);

        Category cat = new Category();
        cat.setLabel("Informatique");
        em.persist(cat);

        for (int i = 1; i <= 15; i++) {
            Annonce a = new Annonce();
            a.setTitle("Annonce Test " + i);
            a.setStatus(AnnonceStatus.PUBLISHED);
            a.setAuthor(author);
            a.setCategory(cat);
            em.persist(a);
        }

        em.getTransaction().commit();
    }

    @AfterEach
    void tearDown() {
        if (em.isOpen()) em.close();
    }

    @AfterAll
    static void close() {
        if (emf != null) emf.close();
    }

    @Test
    @DisplayName("Test Pagination : Page 1 (10 éléments)")
    void testPaginationPage1() {
        List<Annonce> result = repo.findAdvanced(em, null, null, null, 1, 10);
        assertEquals(10, result.size());
        // On s'attend à voir la 15 car c'est la plus récente
        assertEquals("Annonce Test 15", result.get(0).getTitle());
    }

    @Test
    @DisplayName("Test Pagination : Page 2 (5 éléments restants)")
    void testPaginationPage2() {
        List<Annonce> page1 = repo.findAdvanced(em, null, null, null, 1, 10);
        List<Annonce> page2 = repo.findAdvanced(em, null, null, null, 2, 10);

        // 1. Vérifier la taille
        assertEquals(5, page2.size(), "La page 2 devrait contenir les 5 éléments restants");

        // 2. Vérifier que le premier élément de la page 2 n'est pas dans la page 1
        // C'est un test beaucoup plus robuste que de parier sur le titre exact
        String titlePage2 = page2.get(0).getTitle();
        boolean foundInPage1 = page1.stream().anyMatch(a -> a.getTitle().equals(titlePage2));

        assertFalse(foundInPage1, "L'élément de la page 2 ne devrait pas être présent en page 1");
    }
}