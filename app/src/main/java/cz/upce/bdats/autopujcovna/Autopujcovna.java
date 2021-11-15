package cz.upce.bdats.autopujcovna;

import java.util.Objects;
import java.util.Iterator;

import cz.upce.bdats.ds.IAbstrDoubleList;
import cz.upce.bdats.ds.AbstrDoubleList;

public class Autopujcovna implements IAutopujcovna {
    // Vnitřní třídy
    public static class AutopujcovnaException extends Exception {
        // Konstanty
        private static final AutopujcovnaException POBOCKA_NULL = new AutopujcovnaException("Pobočka s hodnotou null!");
        private static final AutopujcovnaException POZICE = new AutopujcovnaException("Nepodporovaná pozice!");
        private static final AutopujcovnaException POZICE_NULL = new AutopujcovnaException("Pozice s hodnotou null!");
        private static final AutopujcovnaException AUTO_NULL = new AutopujcovnaException("Auto s hodnotou null!");

        // Konstruktor
        private AutopujcovnaException(String zprava) {
            super(zprava);
        }

        private AutopujcovnaException(String zprava, Throwable pricina) {
            super(zprava, pricina);
        }
    }

    // Atributy
    private IAbstrDoubleList<IPobocka> pobocky;
    private IAbstrDoubleList<Auto> vypujcene;
    private String nazev;

    // Konstruktor
    public Autopujcovna(String nazev) {
        this.nazev = nazev;
        this.pobocky = new AbstrDoubleList<>();
        this.vypujcene = new AbstrDoubleList<>();
    }

    public Autopujcovna(String nazev, IPobocka... pobocky) {
        this(nazev);
        for (IPobocka p : pobocky)
            this.pobocky.vlozPosledni(p);
    }

    // M101 vloží novou pobočku do seznamu na příslušnou pozici (první, poslední, předchůdce, následník)
    @Override
    public void vlozPobocku(IPobocka pobocka, Pozice pozice) throws AutopujcovnaException {
        try {
            if (Objects.isNull(pobocka)) throw AutopujcovnaException.POBOCKA_NULL;

            switch (pozice) {
                case PRVNI: pobocky.vlozPrvni(pobocka); break;
                case POSLEDNI: pobocky.vlozPosledni(pobocka); break;
                case NASLEDNIK: pobocky.vlozNaslednika(pobocka); break;
                case PREDCHUDCE: pobocky.vlozPredchudce(pobocka); break;
                default: throw AutopujcovnaException.POZICE;
            }
        } catch (Exception e) {
            throw new AutopujcovnaException("Chyba při vkládání pobočky!", e);
        }
    }

    // M102 zpřístupní pobočku z požadované pozice (první, poslední, předchůdce, následník, aktuální)
    @Override
    public IPobocka zpristupniPobocku(Pozice pozice) throws AutopujcovnaException {
        try {
            if (Objects.isNull(pozice)) throw AutopujcovnaException.POZICE_NULL;
            switch (pozice) {
                case AKTUALNI: return pobocky.zpristupniAktualni();
                case PRVNI: return pobocky.zpristupniPrvni();
                case POSLEDNI: return pobocky.zpristupniPosledni();
                case PREDCHUDCE: return pobocky.zpristupniPredchudce();
                case NASLEDNIK: return pobocky.zpristupniNaslednika();
                default: throw AutopujcovnaException.POZICE;
            }
        } catch (Exception e) {
            throw new AutopujcovnaException("Chyba při zpřístupňování pobočky!", e);
        }
    }

    // odebere pobočku z požadované pozice (první, poslední, předchůdce, následník, aktuální)
    @Override
    public IPobocka odeberPobocku(Pozice pozice) throws AutopujcovnaException {
        try {
            if (Objects.isNull(pozice)) throw AutopujcovnaException.POZICE_NULL;
            switch (pozice) {
                case AKTUALNI: return pobocky.odeberAktualni();
                case PRVNI: return pobocky.odeberPrvni();
                case POSLEDNI: return pobocky.odeberPosledni();
                case PREDCHUDCE: return pobocky.odeberPredchudce();
                case NASLEDNIK: return pobocky.odeberNaslednika();
                default: throw AutopujcovnaException.POZICE;
            }
        } catch (Exception e) {
            throw new AutopujcovnaException("Chyba při odebírání pobočky!", e);
        }
    }

    // vloží nové auto do seznamu aktuální pobocky na příslušnou pozici
    public void vlozAuto(Auto auto) throws AutopujcovnaException {
        try {
            pobocky.zpristupniAktualni().vlozAuto(auto);
        } catch (Exception e) {
            throw new AutopujcovnaException("Chyba při vkládání auta do aktuální pobočky!", e);
        }
    }

    // zpřístupní auto z požadované pozice aktuální pobočky (první, poslední, předchůdce, následník, aktuální)
    public Auto zpristupniAuto(String spz) throws AutopujcovnaException {
        try {
            return pobocky.zpristupniAktualni().zpristupniAuto(spz);
        } catch (Exception e) {
            throw new AutopujcovnaException("Chyba při zpřístupňování auta v aktuální pobočce!", e);
        }
    }

    // odebere auto z požadované pozice (první, poslední, předchůdce, následník, aktuální)
    public Auto odeberAuto(String spz) throws AutopujcovnaException {
        try {
            return pobocky.zpristupniAktualni().odeberAuto(spz);
        } catch (Exception e) {
            throw new AutopujcovnaException("Chyba při odebírání auta z aktualní pobočky!", e);
        }
    }
    // odebere auto z požadované pozice aktuální pobočky a vloží ho do seznamu výpůjček (první, poslední, následník, předchůdce, aktuální)
    public Auto vypujcAuto(String spz) throws AutopujcovnaException {
        try {
            Auto a = pobocky.zpristupniAktualni().odeberAuto(spz);
            a.setPocetVypujceni(a.getPocetVypujceni() + 1);
            vypujcene.vlozPosledni(a);
            return a;

        } catch (Exception e) {
            throw new AutopujcovnaException("Chyba při vypůjčování auta z pobočky!", e);
        }
    }

    // odebere auto z požadované pozice výpůjček a vloží ho do seznamu aktuální pobočky (první, poslední, předchůdce, následník, aktuální)
    public Auto vratAuto(Pozice pozice) throws AutopujcovnaException {
        try {
            if (Objects.isNull(pozice)) throw AutopujcovnaException.POZICE_NULL;

            Auto a = null;

            switch (pozice) {
                case PRVNI: a = vypujcene.odeberPrvni(); break;
                case POSLEDNI: a = vypujcene.odeberPosledni(); break;
                case PREDCHUDCE: a = vypujcene.odeberPredchudce(); break;
                case NASLEDNIK: a = vypujcene.odeberNaslednika(); break;
                case AKTUALNI: a = vypujcene.odeberAktualni(); break;
                default: throw AutopujcovnaException.POZICE;
            }

            pobocky.zpristupniAktualni().vlozAuto(a);
            return a;
        } catch (Exception e) {
            throw new AutopujcovnaException("Chyba při vracení auta na pobočku!", e);
        }
    }

    // zpřístupní auto z požadované pozice ze seznamu vypůjčených aut (první, poslední, předchůdce, následník, aktuální)
    public Auto zpristupniVypujceneAuto(Pozice pozice) throws AutopujcovnaException {
        try {
            if (Objects.isNull(pozice)) throw AutopujcovnaException.POZICE_NULL;

            switch (pozice) {
                case PRVNI: return vypujcene.zpristupniPrvni();
                case POSLEDNI: return vypujcene.zpristupniPosledni();
                case NASLEDNIK: return vypujcene.zpristupniNaslednika();
                case PREDCHUDCE: return vypujcene.zpristupniPredchudce();
                case AKTUALNI: return vypujcene.zpristupniAktualni();
                default: throw AutopujcovnaException.POZICE;
            }
        } catch (Exception e) {
            throw new AutopujcovnaException("Chyba při zpřístupňování vypůjčeného auta!", e);
        }
    }

    // vrací požadovaný iterátor poboček/automobilů/vypůjčených automobilů
    public Iterator iterator(IteratorTyp typ) throws AutopujcovnaException {
        try {
            switch (typ) {
                case POBOCKY: return pobocky.iterator();
                case AUTOMOBILY: return zpristupniPobocku(Pozice.AKTUALNI).iterator();
                case VYPUJCENE_AUTOMOBILY: return vypujcene.iterator();
                default: throw new AutopujcovnaException("Neznámý typ iteratoru!");
            }
        } catch (Exception e) {
            throw new AutopujcovnaException("Chyba při tvorbě iterátoru!", e);
        }
    }

    // zruší všechna auta v aktuální pobočce
    public void zrusPobocku() throws AutopujcovnaException {
        try {
            zpristupniPobocku(Pozice.AKTUALNI).zrus();
        } catch (Exception e) {
            throw new AutopujcovnaException("Chyba při rušení všech aut v aktualní pobočce!", e);
        }
    }

    // zruší všechny pobočky
    public void zrus() {
        pobocky.zrus();
        vypujcene.zrus();
    }

    @Override
    public int pocetPobocek() {
        return pobocky.velikost();
    }

    @Override
    public int pocetVypujcenychAut() {
        return vypujcene.velikost();
    }

    @Override
    public void vlozVypujceneAuto(Auto a) throws AutopujcovnaException {
        if (Objects.isNull(a)) throw AutopujcovnaException.AUTO_NULL;
        vypujcene.vlozPosledni(a);
    }

    @Override
    public String getNazev() {
        return nazev;
    }

    @Override
    public String toString() {
        return String.format("Autopůjčovna %s", nazev);
    }
}