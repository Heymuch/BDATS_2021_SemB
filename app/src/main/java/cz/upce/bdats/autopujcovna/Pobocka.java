package cz.upce.bdats.autopujcovna;

import java.util.Objects;
import java.util.Iterator;

import cz.upce.bdats.ds.IAbstrDoubleList;
import cz.upce.bdats.ds.AbstrDoubleList;

public class Pobocka implements IPobocka {
    // Vnitřní třídy
    public static class PobockaException extends Exception {
        // Konstanty
        private static final PobockaException AUTO_NULL = new PobockaException("Automobil s hodnotou null!");
        private static final PobockaException POZICE = new PobockaException("Pozice není podporovaná!");

        // Konstruktor
        private PobockaException(String zprava) {
            super(zprava);
        }

        private PobockaException(String zprava, Throwable pricina) {
            super(zprava, pricina);
        }
    }

    // Atributy
    private final IAbstrDoubleList<Auto> seznam = new AbstrDoubleList<>();
    private final String nazev;

    // Konstruktor
    public Pobocka(String nazev) {
        this.nazev = nazev;
    }

    public Pobocka(String nazev, Auto... auta) {
        this.nazev = nazev;
        for (Auto a : auta)
            seznam.vlozPosledni(a);
    }

    // Metody
    @Override
    public String getNazev() {
        return nazev;
    }

    @Override // M101
    public void vlozAuto(Auto auto, Pozice pozice) throws PobockaException {
        try {
            if (Objects.isNull(auto)) throw PobockaException.AUTO_NULL;

            switch (pozice) {
                case PRVNI: seznam.vlozPrvni(auto); break;
                case POSLEDNI: seznam.vlozPosledni(auto); break;
                case PREDCHUDCE: seznam.vlozPredchudce(auto); break;
                case NASLEDNIK: seznam.vlozNaslednika(auto); break;
                default: throw PobockaException.POZICE;
            }
        } catch (Exception e) {
            throw new PobockaException("Chyba při vkládání nového auta!", e);
        }
    }

    @Override // M102
    public Auto zpristupniAuto(Pozice pozice) throws PobockaException {
        try {
            switch (pozice) {
                case PRVNI: return seznam.zpristupniPrvni();
                case POSLEDNI: return seznam.zpristupniPosledni();
                case PREDCHUDCE: return seznam.zpristupniPredchudce();
                case NASLEDNIK: return seznam.zpristupniNaslednika();
                case AKTUALNI: return seznam.zpristupniAktualni();
                default: throw PobockaException.POZICE;
            }
        } catch (Exception e) {
            throw new PobockaException("Chyba při zpřístupňování auta!", e);
        }
    }

    @Override // M103
    public Auto odeberAuto(Pozice pozice) throws PobockaException {
        try {
            switch (pozice) {
                case PRVNI: return seznam.odeberPrvni();
                case POSLEDNI: return seznam.odeberPosledni();
                case NASLEDNIK: return seznam.odeberNaslednika();
                case PREDCHUDCE: return seznam.odeberPredchudce();
                case AKTUALNI: return seznam.odeberAktualni();
                default: throw PobockaException.POZICE;
            }
        } catch (Exception e) {
            throw new PobockaException("Chyba při odebírání auta!", e);
        }
    }

    @Override // M104
    public Iterator<Auto> iterator() {
        return seznam.iterator();
    }

    @Override // M105
    public void zrus() {
        seznam.zrus();
    }

    @Override
    public int pocetAut() {
        return seznam.velikost();
    }

    @Override
    public String toString() {
        return String.format("Pobočka %s", nazev);
    }
}