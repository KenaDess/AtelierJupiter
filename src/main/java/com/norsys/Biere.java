package com.norsys;

public class Biere {

    private String nom;
    private String origine;
    private double degre;

    public Biere(String nom, String origine, double degre) {
        this.nom = nom;
        this.origine = origine;
        this.degre = degre;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getOrigine() {
        return origine;
    }

    public void setOrigine(String origine) {
        this.origine = origine;
    }

    public double getDegre() {
        return degre;
    }

    public void setDegre(double degre) {
        this.degre = degre;
    }
}
