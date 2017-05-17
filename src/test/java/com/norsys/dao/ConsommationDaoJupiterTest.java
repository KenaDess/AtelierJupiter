package com.norsys.dao;

import com.norsys.domain.Biere;
import com.norsys.domain.Consommation;
import com.norsys.dao.exception.BoboException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ContainerExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

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

            // TODO Vérifier le nom de la bière et le lieu des consommations récupérées
        }

        @Disabled
        @TestFactory
        @DisplayName("Récupérer toutes les consommations (test factory)")
        Stream<DynamicTest> devraitRecupererToutesLesConsommations_testFactory() {
            List<String> nomsBieres = Arrays.asList("Cuvée des trolls", "Angelus", "Rince Cochon", "Duvel", "Cuvée des trolls");
            List<String> lieuxConso = Arrays.asList("Lille", "Ennevelin", "Ennevelin", "Seclin", "Lille");

            List<Consommation> allConsommations = consommationDao.getAllConsommations();

            // TODO Vérifier les noms des bières consommées et le lieu de consommation pour chaque consommation récupérée
            // utiliser la méthode getDisplayNameConso() pour le nom du test :)
            return null;
        }

        @Disabled
        @TestFactory
        @DisplayName("Récupérer les consommations selon le lieu (test factory)")
        Stream<DynamicTest> devraitRecupererConsommationsParLieu_testFactory() {
            List<String> lieux = Arrays.asList("Lille", "Ennevelin", "Seclin", "Paris");
            List<Integer> nombresConsommations = Arrays.asList(2, 2, 1, 0);

            // TODO Vérifier le nombre de consommations récupérées selon le lieu de consommation
            return null;
        }

        @ParameterizedTest
        // TODO Modifier MonArgumentProvider afin de construire un stream d'objets à passer en entrée du test
        @ArgumentsSource(MonArgumentProvider.class)
        @DisplayName("Vérifier qu'il existe au moins une consommation pour une date")
        void devraitRecupererAuMoinsUneConsommationParDate(Object monArgument) {
            // TODO Vérifier qu'il existe au moins une consommation à certaines dates (dates en paramètre du test)
        }

    }

    @Nested
    class SauvegardeConsommation {

        @Test
        @DisplayName("Insertion d'une nouvelle consommation")
        void devraitSauvegarderUneConsommation() throws BoboException {
            // TODO Vérifier que l'insertion d'une nouvelle consommation fonctionne
            // utiliser newTripelKarmeliet()
        }

        @Test
        @DisplayName("Tentative d'insertion de consommation à Paris, lève une BoboException")
        void devraitLeverExceptionPourParis() {
            // TODO Vérifier qu'une exception est lancée et vérifier son message
        }

        @Disabled
        @TestFactory
        @DisplayName("Tentative d'insertion de consommation à Paris selon plusieurs écritures")
        Stream<DynamicTest> devraitLeverExceptionPourParis_testFactory() {
            List<String> ecritures = Arrays.asList("Paris", "paris", "PARIS");

            // TODO Vérifier qu'une BoboException est lancée lorsque l'on tente de sauvegarder une consommation à Paris (selon plusieurs typo)
            return null;
        }

    }

    @Nested
    class SuppressionConsommation {

        @Test
        @DisplayName("Suppression de la première consommation")
        void devraitSupprimerUneConsommation() {
            // TODO Vérifier que la suppression d'une consommation existante fonctionne
        }

        @Test
        @DisplayName("Suppression d'une consommation inexistante")
        void devraitSupprimerAucuneConsommationCarInexistante() {
            // TODO Vérifier que la suppression d'une consommation inexistante ne fontionne pas
        }

        @ParameterizedTest
        @DisplayName("Suppressions de consommations inexistantes (test paramétré)")
        void devraitSupprimerAucuneConsommationCarInexistante_testParametre(int argument) {
            // TODO Vérifier que la suppression de consommations inexistantes ne fonctionne pas (ID de consommations en paramètres)
        }

    }

    /* ******************* Utils ****************** */

    private String getDisplayNameConso(Consommation conso) {
        return "Consommation n°" + conso.getId() + " : " + conso.getBiere().getNom() + "/" + conso.getLieu();
    }

    private Biere newTripelKarmeliet() {
        return new Biere("Tripel Karmeliet", "Belgique", 8.4);
    }

    private static class MonArgumentProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> arguments(ContainerExtensionContext context) {
            // TODO Créer un stream à partir des données en entrée du test
            return null;
        }
    }

}
