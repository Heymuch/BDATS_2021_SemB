package cz.upce.bdats.autopujcovna;

public enum Typ {
    // Prvky
    OSOBNI("osobní automobil"),
    UZITKOVY("užitkový automobil");

    // Atributy
    private String nazev;

    // Konstruktor
    private Typ(String nazev) {
        this.nazev = nazev;
    }

    // Gettery
    public String getNazev() {
        return nazev;
    }

    // Metody
    @Override
    public String toString() {
        return nazev;
    }
}