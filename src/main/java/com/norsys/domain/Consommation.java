package com.norsys.domain;

import java.time.LocalDate;

public class Consommation {

    private Integer id;
    private Biere biere;
    private LocalDate date;
    private String lieu;

    public Consommation(Integer id, Biere biere, LocalDate date, String lieu) {
        this.id = id;
        this.biere = biere;
        this.date = date;
        this.lieu = lieu;
    }

    public Consommation(Biere biere, LocalDate date, String lieu) {
        this.biere = biere;
        this.date = date;
        this.lieu = lieu;
    }

    public Biere getBiere() {
        return biere;
    }

    public void setBiere(Biere biere) {
        this.biere = biere;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
