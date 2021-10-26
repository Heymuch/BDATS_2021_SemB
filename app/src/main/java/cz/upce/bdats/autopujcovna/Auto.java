package cz.upce.bdats.autopujcovna;

import java.io.Serializable;

public abstract class Auto implements Serializable {
    // Atributy
    private final Typ typ;
    private final String spz;
    private float stavKm = 0;
    private int pocetVypujceni = 0;

    // Konstruktor
    public Auto(Typ typ, String spz) {
        this(typ, spz, 0.0f, 0);
    }

    public Auto(Typ typ, String spz, float stavKm, int pocetVypujceni) {
        this.typ = typ;
        this.spz = spz;
        this.stavKm = stavKm;
        this.pocetVypujceni = pocetVypujceni;
    }

    // Metody
    @Override
    public String toString() {
        return String.format("%s: %s; %.2f km, vypůjčeno %dx", typ, spz, stavKm, pocetVypujceni);
    }

    // Gettery
    public String getSPZ() {
        return spz;
    }

    public float getStavKm() {
        return stavKm;
    }

    public int getPocetVypujceni() {
        return pocetVypujceni;
    }

    public Typ getTyp() {
        return typ;
    }

    // Settery
    public void setStavKm(float km) {
        stavKm = km;
    }

    public void setPocetVypujceni(int pocetVypujceni) {
        this.pocetVypujceni = pocetVypujceni;
    }
}