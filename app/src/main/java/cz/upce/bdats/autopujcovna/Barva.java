package cz.upce.bdats.autopujcovna;

public enum Barva {
    // Prvky
    CERNA("černá"),
    BILA("bíla"),
    CERVENA("červená"),
    MODRA("modrá"),
    ZELENA("zelená");

    // Atributy
    private final String nazev;

    // Konstruktor
    private Barva(String nazev) {
        this.nazev = nazev;
    }

    // Metody
    @Override
    public String toString() {
        return nazev;
    }

    // Gettery
    public String getNazev() {
        return nazev;
    }
}