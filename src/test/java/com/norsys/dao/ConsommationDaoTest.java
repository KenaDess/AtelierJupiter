package com.norsys.dao;

import com.norsys.Biere;
import com.norsys.Consommation;
import com.norsys.dao.exception.BoboException;
import org.assertj.core.util.DateUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static com.norsys.util.DateUtil.getLocalDate;

/**
 * Classe de tests JUnit 4 pour ConsommationDao.
 */
public class ConsommationDaoTest {

    private ConsommationDao consommationDao;

    @Before
    public void setUp() throws Exception {
        this.consommationDao = new ConsommationDao();
    }

    /* ****************** Récupération de consommations ****************** */

    @Test
    public void devraitRecupererToutesLesConsommations() throws Exception {
        List<Consommation> allConsommations = consommationDao.getAllConsommations();

        Assert.assertNotNull(allConsommations);
        Assert.assertEquals(5, allConsommations.size());

        Consommation premiereConso = allConsommations.get(0);
        Assert.assertEquals("Cuvée des trolls", premiereConso.getBiere().getNom());
        Assert.assertEquals("Lille", premiereConso.getLieu());
    }

    @Test
    public void devraitRecupererToutesLesConsommationsParLieu() throws Exception {
        List<Consommation> consoEnnevelin = consommationDao.getConsommationsByLieu("Ennevelin");

        Assert.assertNotNull(consoEnnevelin);
        Assert.assertEquals(2, consoEnnevelin.size());

        Consommation premiereConso = consoEnnevelin.get(0);
        Assert.assertEquals("Angelus", premiereConso.getBiere().getNom());
        Assert.assertEquals("Ennevelin", premiereConso.getLieu());
    }

    @Test
    public void devraitRecupererAucuneConsommationParLieu() throws Exception {
        List<Consommation> consoParis = consommationDao.getConsommationsByLieu("Paris");

        Assert.assertNotNull(consoParis);
        Assert.assertTrue(consoParis.isEmpty());
    }

    @Test
    public void devraitRecupererConsommationParDate() throws Exception {
        Assert.assertFalse(consommationDao.getConsommationsByDate(getLocalDate("01/01/2017")).isEmpty());
        Assert.assertFalse(consommationDao.getConsommationsByDate(getLocalDate("17/05/2017")).isEmpty());
        Assert.assertFalse(consommationDao.getConsommationsByDate(getLocalDate("01/08/2017")).isEmpty());
    }

    /* ******************** Sauvegarde de consommations ****************** */

    @Test
    public void devraitSauverUneConsommation() throws Exception {
        Consommation consoResultante = consommationDao.saveConsommation(newTripelKarmeliet(), LocalDate.now(), "Seclin");
        Assert.assertNotNull(consoResultante);
        Assert.assertTrue(consoResultante.getId() == 5);
    }

    @Test(expected = BoboException.class)
    public void devraitLeverExceptionPourParis() throws Exception {
        consommationDao.saveConsommation(newTripelKarmeliet(), LocalDate.now(), "Paris");
    }

    /* ******************* Suppression de consommations ****************** */

    @Test
    public void devraitSupprimerUneConsommation() throws Exception {
        Assert.assertTrue(consommationDao.deleteConsommation(4));
    }

    @Test
    public void devraitSupprimerAucuneConsommationCarInexistante() throws Exception {
        Assert.assertFalse(consommationDao.deleteConsommation(10));
    }

    /* ******************* Utils ****************** */

    private Biere newTripelKarmeliet() {
        return new Biere("Tripel Karmeliet", "Belgique", 8.4);
    }
}
