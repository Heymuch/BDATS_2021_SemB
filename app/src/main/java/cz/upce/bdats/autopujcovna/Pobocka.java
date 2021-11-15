package cz.upce.bdats.autopujcovna;

import java.util.Objects;
import java.util.Iterator;

import cz.upce.bdats.ds.ITable;
import cz.upce.bdats.ds.IterationType;
import cz.upce.bdats.ds.Table;

public class Pobocka implements IPobocka {
    // Vnitřní třídy
    public static class PobockaException extends Exception {
        // Konstanty
        private static final PobockaException AUTO_NULL = new PobockaException("Automobil s hodnotou null!");
        // private static final PobockaException POZICE = new PobockaException("Pozice není podporovaná!");

        // Konstruktor
        private PobockaException(String zprava) {
            super(zprava);
        }

        private PobockaException(String zprava, Throwable pricina) {
            super(zprava, pricina);
        }
    }

    // Atributy
    private final ITable<String, Auto> tabulka = new Table<>();
    private final String nazev;

    // Konstruktor
    public Pobocka(String nazev) {
        this.nazev = nazev;
    }

    public Pobocka(String nazev, Auto... auta) throws Exception {
        this.nazev = nazev;
        for (Auto a : auta)
            vlozAuto(a);
    }

    // Metody
    @Override
    public String getNazev() {
        return nazev;
    }

    @Override // M101
    public void vlozAuto(Auto auto) throws PobockaException {
        try {
            if (Objects.isNull(auto)) throw PobockaException.AUTO_NULL; // pokud má auto hodnotu null...
            tabulka.add(auto.getSPZ(), auto);
        } catch (Exception e) {
            throw new PobockaException("Chyba při vkládání nového auta!", e);
        }
    }

    @Override // M102
    public Auto zpristupniAuto(String spz) throws PobockaException {
        try {
            return tabulka.find(spz);
        } catch (Exception e) {
            throw new PobockaException("Chyba při zpřístupňování auta!", e);
        }
    }

    @Override // M103
    public Auto odeberAuto(String spz) throws PobockaException {
        try {
            return tabulka.remove(spz);
        } catch (Exception e) {
            throw new PobockaException("Chyba při odebírání auta!", e);
        }
    }

    @Override // M104
    public Iterator<Auto> iterator() {
        return tabulka.iterator(IterationType.BREADTH);
    }

    @Override // M105
    public void zrus() {
        tabulka.clear();
    }

    @Override
    public int pocetAut() {
        return tabulka.size();
    }

    @Override
    public String toString() {
        return String.format("Pobočka %s", nazev);
    }
}