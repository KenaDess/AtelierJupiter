package com.norsys.dao;

import com.norsys.Biere;
import com.norsys.Consommation;
import com.norsys.dao.exception.BoboException;
import org.apache.tomcat.jni.Local;
import org.assertj.core.util.DateUtil;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ContainerExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static com.norsys.util.DateUtil.getLocalDate;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Classe de tests JUnit 5 pour ConsommationDao.
 */
@Tag("developpement")
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

        @Tag("production")
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

        @Tag("production")
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

        @ParameterizedTest
        @ArgumentsSource(DateArgumentsProvider.class)
        @DisplayName("Vérifier qu'il existe au moins une consommation pour une date")
        void devraitRecupererAuMoinsUneConsommationParDate(LocalDate dateArgument) {
            Assertions.assertFalse(consommationDao.getConsommationsByDate(dateArgument).isEmpty());
        }

    }

    @Nested
    class SauvegardeConsommation {

        @Tag("production")
        @Test
        @DisplayName("Insertion d'une nouvelle consommation")
        void devraitSauvegarderUneConsommation() throws BoboException {
            Consommation consoResultante = consommationDao.saveConsommation(newTripelKarmeliet(), LocalDate.now(), "Seclin");
            Assertions.assertTrue(5 == consoResultante.getId());
        }

        @Test
        @DisplayName("Tentative d'insertion de consommation à Paris, lève une BoboException")
        void devraitLeverExceptionPourParis() {
            Throwable exception = Assertions.assertThrows(BoboException.class, () -> {
                consommationDao.saveConsommation(newTripelKarmeliet(), LocalDate.now(), "Paris");
            });
            Assertions.assertEquals("A Paris, on déguste du vin dans un bar à vin !", exception.getMessage());
        }

        @Tag("production")
        @TestFactory
        @DisplayName("Tentative d'insertion de consommation à Paris selon plusieurs écritures")
        Stream<DynamicTest> devraitLeverExceptionPourParis_testFactory() {
            List<String> ecritures = Arrays.asList("Paris", "paris", "PARIS");
            return ecritures.stream().map(paris -> DynamicTest.dynamicTest("Tentative d'insertion d'une consommation à " + paris, () -> {
                Throwable exception = Assertions.assertThrows(BoboException.class, () -> {
                    consommationDao.saveConsommation(newTripelKarmeliet(), LocalDate.now(), paris);
                });
                Assertions.assertEquals("A Paris, on déguste du vin dans un bar à vin !", exception.getMessage());
            }));
        }

    }

    @Nested
    class SuppressionConsommation {

        @Tag("production")
        @Test
        @DisplayName("Suppression de la première consommation")
        void devraitSupprimerUneConsommation() {
            Assertions.assertTrue(consommationDao.deleteConsommation(0));
        }

        @Tag("production")
        @Test
        @DisplayName("Suppression d'une consommation inexistante")
        void devraitSupprimerAucuneConsommationCarInexistante() {
            Assertions.assertFalse(consommationDao.deleteConsommation(10));
        }

        @ParameterizedTest
        @ValueSource(ints = {5, -1, 10})
        @DisplayName("Suppressions de consommations inexistantes (test paramétré)")
        void devraitSupprimerAucuneConsommationCarInexistante_testParametre(int argument) {
            Assertions.assertFalse(consommationDao.deleteConsommation(argument));
        }

    }

    /* ******************* Utils ****************** */

    private String getDisplayNameConso(Consommation conso) {
        return "Consommation n°" + conso.getId() + " : " + conso.getBiere().getNom() + "/" + conso.getLieu();
    }

    private Biere newTripelKarmeliet() {
        return new Biere("Tripel Karmeliet", "Belgique", 8.4);
    }

    private static class DateArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> arguments(ContainerExtensionContext context) {
            return Stream.of(
                    getLocalDate("01/01/2017"),
                    getLocalDate("01/08/2017"),
                    getLocalDate("17/05/2017"))
                    .map(ObjectArrayArguments::create);
        }
    }

}
