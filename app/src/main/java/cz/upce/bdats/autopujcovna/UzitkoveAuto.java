package cz.upce.bdats.autopujcovna;

public class UzitkoveAuto extends Auto {
    // Atributy
    private final float nosnost;

    // Kontruktor
    public UzitkoveAuto(String spz, float nosnost) {
        this(spz, 0.0f, 0, nosnost);
    }

    public UzitkoveAuto(String spz, float stavKm, int pocetVypujceni, float nosnost) {
        super(Typ.UZITKOVY, spz, stavKm, pocetVypujceni);
        this.nosnost = nosnost;
    }

    // Metody
    @Override
    public String toString() {
        return String.format("%s, nosnost: %.2f", super.toString(), nosnost);
    }

    // Gettery
    public float getNosnost() {
        return nosnost;
    }
}