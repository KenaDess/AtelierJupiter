package com.norsys.service;

import com.norsys.domain.Biere;
import com.norsys.domain.Consommation;
import com.norsys.dao.ConsommationDao;
import com.norsys.dao.exception.BoboException;

import java.time.LocalDate;
import java.util.List;

public class ConsommationService {

    private ConsommationDao consommationDao;

    public ConsommationService() {
        this.consommationDao = new ConsommationDao();
    }

    public void setConsommationDao(ConsommationDao consommationDao) {
        this.consommationDao = consommationDao;
    }

    public List<Consommation> getAllConsommations() {
        return consommationDao.getAllConsommations();
    }

    public List<Consommation> getConsommationsByLieu(String lieu) {
        return consommationDao.getConsommationsByLieu(lieu);
    }

    public Consommation saveConsommation(Biere biere, String lieu) throws BoboException {
        return consommationDao.saveConsommation(biere, LocalDate.now(), lieu);
    }

    public boolean deleteConsommation(Integer idConso) {
        return consommationDao.deleteConsommation(idConso);
    }
}
