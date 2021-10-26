package cz.upce.bdats.autopujcovna;

public class OsobniAuto extends Auto {
    // Atributy
    private final Barva barva;

    // Konstruktor
    public OsobniAuto(String spz, Barva barva) {
        this(spz, 0.0f, 0, barva);
    }

    public OsobniAuto(String spz, float stavKm, int pocetVypujceni, Barva barva) {
        super(Typ.OSOBNI, spz, stavKm, pocetVypujceni);
        this.barva = barva;
    }

    // Metody
    @Override
    public String toString() {
        return String.format("%s, barva: %s", super.toString(), barva);
    }

    // Gettery
    public Barva getBarva() {
        return barva;
    }
}