package com.norsys.dao;


import com.norsys.domain.Biere;
import com.norsys.domain.Consommation;
import com.norsys.dao.exception.BoboException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.norsys.util.DateUtil.getLocalDate;

public class ConsommationDao {

    private static final String PARIS = "paris";

    private List<Consommation> baseDeDonnes;

    public ConsommationDao() {
        baseDeDonnes = new ArrayList<>();

        Biere cuveeDesTrolls = new Biere("Cuvée des trolls", "France", 3.5);
        Biere angelus = new Biere("Angelus", "Belgique", 4);
        Biere duvel = new Biere("Duvel", "France", 5);
        Biere rinceCochon = new Biere("Rince Cochon", "Belgique", 6.5);

        Consommation conso = new Consommation(0, cuveeDesTrolls, getLocalDate("17/05/2017"), "Lille");
        Consommation conso2 = new Consommation(1, angelus, getLocalDate("01/01/2017"), "Ennevelin");
        Consommation conso3 = new Consommation(2, rinceCochon, getLocalDate("01/08/2017"), "Ennevelin");
        Consommation conso4 = new Consommation(3, duvel, getLocalDate("17/05/2017"), "Seclin");
        Consommation conso5 = new Consommation(4, cuveeDesTrolls, getLocalDate("18/05/2017"), "Lille");

        this.baseDeDonnes.addAll(Arrays.asList(conso, conso2, conso3, conso4, conso5));
    }

    public List<Consommation> getAllConsommations() {
        return this.baseDeDonnes;
    }

    public List<Consommation> getConsommationsByLieu(String lieu) {
        return this.baseDeDonnes.stream()
                .filter(conso -> conso.getLieu().toLowerCase().equals(lieu.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Consommation> getConsommationsByDate(LocalDate date) {
        return this.baseDeDonnes.stream()
                .filter(conso -> conso.getDate().isEqual(date))
                .collect(Collectors.toList());
    }

    public Consommation saveConsommation(Biere biere, LocalDate date, String lieu) throws BoboException {
        if (PARIS.equals(lieu.toLowerCase())) {
            throw new BoboException();
        }
        Consommation consommation = new Consommation(this.baseDeDonnes.size(), biere, date, lieu);
        this.baseDeDonnes.add(consommation);
        return consommation;
    }

    public boolean deleteConsommation(Integer idConso) {
        return this.baseDeDonnes.removeIf(conso -> conso.getId().equals(idConso));
    }
}
