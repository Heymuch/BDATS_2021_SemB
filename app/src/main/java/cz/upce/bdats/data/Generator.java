package cz.upce.bdats.data;

import java.util.Random;

import cz.upce.bdats.autopujcovna.Auto;
import cz.upce.bdats.autopujcovna.OsobniAuto;
import cz.upce.bdats.autopujcovna.UzitkoveAuto;
import cz.upce.bdats.autopujcovna.Typ;
import cz.upce.bdats.autopujcovna.Barva;
import cz.upce.bdats.autopujcovna.IPobocka;
import cz.upce.bdats.autopujcovna.Pobocka;
import cz.upce.bdats.autopujcovna.Pozice;
import cz.upce.bdats.autopujcovna.IAutopujcovna;
import cz.upce.bdats.autopujcovna.Autopujcovna;

public class Generator {
    // Konstruktor
    private static final Random RNG = new Random();

    public static Typ genTyp() {
        return Typ.values()[RNG.nextInt(Typ.values().length)];
    }

    public static String genSPZ() {
        return String.format("%d%c%d %04d", RNG.nextInt(10), 'A' + RNG.nextInt('Z' - 'A' + 1), RNG.nextInt(10), RNG.nextInt(1000));
    }

    public static float genStavKm() {
        return RNG.nextFloat() * 300000.0f;
    }

    public static int genPocetVypujceni() {
        return RNG.nextInt(21);
    }

    public static Barva genBarva() {
        return Barva.values()[RNG.nextInt(Barva.values().length)];
    }

    public static float genNosnost() {
        return 5.0f + RNG.nextFloat() * 20.0f;
    }

    public static Auto genAuto() {
        switch (genTyp()) {
            case OSOBNI: return new OsobniAuto(genSPZ(), genStavKm(), genPocetVypujceni(), genBarva());
            case UZITKOVY: return new UzitkoveAuto(genSPZ(), genStavKm(), genPocetVypujceni(), genNosnost());
            default: return null;
        }
    }

    public static IPobocka genPobocka(int pocetAut) throws Exception {
        IPobocka p = new Pobocka(genPobockaNazev());
        for (int i = 0; i < pocetAut; i++)
            p.vlozAuto(genAuto());
        return p;
    }

    public static String genPobockaNazev() {
        String[] n = new String[] {
            "Alfa", "Beta", "Gama", "Delta", "Epsilon",
            "Zéta", "Éta", "Théta", "Ióta", "Kappa",
            "Lambda", "Mí", "Ný", "Ksí", "Omikron",
            "Pí", "Ró", "Sigma", "Tau", "Ypsilon",
            "Fí", "Chi", "Psí", "Omega"
        };
        return String.format("%s%d", n[RNG.nextInt(n.length)], RNG.nextInt(9) + 1);
    }

    public static IAutopujcovna genAutopujcovna(int pocetPobocek, int pocetAut, int pocetVypujcenychAut) throws Exception {
        IAutopujcovna a = new Autopujcovna(genAutopujcovnaNazev());
        for (int i = 0; i < pocetVypujcenychAut; i++)
            a.vlozVypujceneAuto(genAuto());
        for (int i = 0; i < pocetPobocek; i++)
            a.vlozPobocku(genPobocka(pocetAut), Pozice.POSLEDNI);
        return a;
    }

    public static String genAutopujcovnaNazev() {
        String[] n = new String[] {
            "Crater", "Virgo", "Boötes", "Centaurus", "Libra",
            "Serpens Caput", "Norma", "Scorpius", "Corona Australis", "Scutum",
            "Sagittarius", "Aquila", "Microscopium", "Capricornus", "Piscis Austrinus",
            "Equuleus", "Aquarius", "Pegasus", "Sculptor", "Pisces",
            "Andromeda", "Triangulum", "Aries", "Perseus", "Cetus",
            "Taurus", "Auriga", "Eridanus", "Orion", "Canis Minor",
            "Monoceros", "Gemini", "Hydra", "Lynx", "Cancer",
            "Sextans", "Leo Minor", "Leo"
        };
        return n[RNG.nextInt(n.length)];
    }
}