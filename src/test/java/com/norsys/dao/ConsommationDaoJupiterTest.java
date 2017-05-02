package com.norsys.dao;

import com.norsys.Biere;
import com.norsys.Consommation;
import com.norsys.dao.exception.BoboException;
import org.assertj.core.util.DateUtil;
import org.junit.Assert;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Classe de tests JUnit 5 pour ConsommationDao.
 */
public class ConsommationDaoJupiterTest {

    private ConsommationDao consommationDao;

    @BeforeEach
    void setUp() {
        this.consommationDao = new ConsommationDao();
    }

    @Nested
    class RecuperationConsommation {

        @Test
        @DisplayName("Récupérer toutes les consommations (grouped assertions)")
        void devraitRecupererToutesLesConsommations_groupedAssertions() {
            List<Consommation> allConsommations = consommationDao.getAllConsommations();

            Assert.assertNotNull(allConsommations);
            Assertions.assertEquals(5, allConsommations.size());

            Assertions.assertAll("Toutes les consommations",
                    () -> Assertions.assertEquals("Cuvée des trolls", allConsommations.get(0).getBiere().getNom()),
                    () -> Assertions.assertEquals("Lille", allConsommations.get(0).getLieu()),
                    () -> Assertions.assertEquals("Angelus", allConsommations.get(1).getBiere().getNom()),
                    () -> Assertions.assertEquals("Ennevelin", allConsommations.get(1).getLieu())
            );
        }

        @TestFactory
        @DisplayName("Récupérer toutes les consommations (test factory)")
        Stream<DynamicTest> devraitRecupererToutesLesConsommations_testFactory() {
            List<String> nomsBieres = Arrays.asList("Cuvée des trolls", "Angelus", "Rince Cochon", "Duvel", "Cuvée des trolls");
            List<String> lieuxConso = Arrays.asList("Lille", "Ennevelin", "Ennevelin", "Seclin", "Lille");

            List<Consommation> allConsommations = consommationDao.getAllConsommations();

            return allConsommations.stream().map(conso -> DynamicTest.dynamicTest(getDisplayNameConso(conso), () -> {
                int index = allConsommations.indexOf(conso);
                Assertions.assertEquals(nomsBieres.get(index), conso.getBiere().getNom());
                Assertions.assertEquals(lieuxConso.get(index), conso.getLieu());
            }));
        }

        @TestFactory
        @DisplayName("Récupérer les consommations selon le lieu (test factory)")
        Stream<DynamicTest> devraitRecupererConsommationsParLieu_testFactory() {
            List<String> lieux = Arrays.asList("Lille", "Ennevelin", "Seclin", "Paris");
            List<Integer> nombresConsommations = Arrays.asList(2, 2, 1, 0);

            return lieux.stream().map(lieu -> DynamicTest.dynamicTest("On boit combien de bières à " + lieu + " ?", () -> {
                int index = lieux.indexOf(lieu);

                Assertions.assertEquals(consommationDao.getConsommationsByLieu(lieu).size(), nombresConsommations.get(index).intValue());
            }));
        }
    }

    @Nested
    class SauvegardeConsommation {

        @Test
        @DisplayName("Insertion d'une nouvelle consommation")
        void devraitSauvegarderUneConsommation() throws BoboException {
            Consommation consoResultante = consommationDao.saveConsommation(newTripelKarmeliet(), DateUtil.now(), "Seclin");
            Assertions.assertTrue(5 == consoResultante.getId());
        }

        @Test
        @DisplayName("Tentative d'insertion de consommation à Paris, lève une BoboException")
        void devraitLeverExceptionPourParis() {
            Throwable exception = Assertions.assertThrows(BoboException.class, () -> {
                consommationDao.saveConsommation(newTripelKarmeliet(), DateUtil.now(), "Paris");
            });
            Assertions.assertEquals("A Paris, on déguste du vin dans un bar à vin !", exception.getMessage());
        }

        @TestFactory
        @DisplayName("Tentative d'insertion de consommation à Paris selon plusieurs écritures")
        Stream<DynamicTest> devraitLeverExceptionPourParis_testFactory() {
            List<String> ecritures = Arrays.asList("Paris", "paris", "PARIS");
            return ecritures.stream().map(paris -> DynamicTest.dynamicTest("Tentative d'insertion d'une consommation à " + paris, () -> {
                Throwable exception = Assertions.assertThrows(BoboException.class, () -> {
                    consommationDao.saveConsommation(newTripelKarmeliet(), DateUtil.now(), paris);
                });
                Assertions.assertEquals("A Paris, on déguste du vin dans un bar à vin !", exception.getMessage());
            }));
        }
    }

    @Nested
    class SuppressionConsommation {

        @Test
        @DisplayName("Suppression de la première consommation")
        void devraitSupprimerUneConsommation() {
            Assertions.assertTrue(consommationDao.deleteConsommation(0));
        }

        @Test
        @DisplayName("Suppression d'une consommation inexistante")
        void devraitSupprimerAucuneConsommationCarInexistante() {
            Assertions.assertFalse(consommationDao.deleteConsommation(10));
        }

    }

    /* ******************* Utils ****************** */

    private String getDisplayNameConso(Consommation conso) {
        return "Consommation n°" + conso.getId() + " : " + conso.getBiere().getNom() + "/" + conso.getLieu();
    }

    private Biere newTripelKarmeliet() {
        return new Biere("Tripel Karmeliet", "Belgique", 8.4);
    }

}
