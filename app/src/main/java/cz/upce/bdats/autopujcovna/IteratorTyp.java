package cz.upce.bdats.autopujcovna;

public enum IteratorTyp {
    // Prvky
    POBOCKY("pobočky"),
    AUTOMOBILY("automobily"),
    VYPUJCENE_AUTOMOBILY("vypůjčené automobily");

    // Atributy
    private String nazev;

    // Konstruktory
    private IteratorTyp(String nazev) {
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