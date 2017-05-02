package com.norsys;

import java.util.Date;

public class Consommation {

    private Integer id;
    private Biere biere;
    private Date date;
    private String lieu;

    public Consommation(Integer id, Biere biere, Date date, String lieu) {
        this.id = id;
        this.biere = biere;
        this.date = date;
        this.lieu = lieu;
    }

    public Consommation(Biere biere, Date date, String lieu) {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
