package cz.upce.bdats.autopujcovna;

public enum Pozice {
    // Prvky
    PRVNI("první"),
    POSLEDNI("poslední"),
    AKTUALNI("aktuální"),
    PREDCHUDCE("předchůdce"),
    NASLEDNIK("následník");

    // Atributy
    private final String nazev;

    // Konstruktor
    private Pozice(String nazev) {
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