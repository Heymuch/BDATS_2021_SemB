package cz.upce.bdats.autopujcovna;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Iterator;

public class AutopujcovnaTest {
    // Konstanty
    private static final Auto A1 = new OsobniAuto("1S1 1111", Barva.CERNA);
    private static final Auto A2 = new OsobniAuto("2S2 2222", Barva.CERVENA);
    private static final Auto A3 = new OsobniAuto("3S3 3333", Barva.ZELENA);
    private static final Auto A4 = new UzitkoveAuto("4S4 4444", 1.4f);
    private static final Auto A5 = new UzitkoveAuto("5S5 5555", 2.5f);
    private static final Auto A6 = new UzitkoveAuto("6S6 6666", 3.6f);

    private static final IPobocka P1 = new Pobocka("P1", A1, A2);
    private static final IPobocka P2 = new Pobocka("P2", A3, A4);
    private static final IPobocka P3 = new Pobocka("P3", A5, A6);
    private static final IPobocka P4 = new Pobocka("P4", A1, A4);
    private static final IPobocka P5 = new Pobocka("P5");

    @Test
    public void vlozPobockuTest() throws Exception {
        IAutopujcovna a = new Autopujcovna("Test");
        a.vlozPobocku(P1, Pozice.PRVNI);
        a.vlozPobocku(P2, Pozice.POSLEDNI);
        a.zpristupniPobocku(Pozice.PRVNI);
        a.vlozPobocku(P3, Pozice.NASLEDNIK);
        a.vlozPobocku(P4, Pozice.PREDCHUDCE);

        assertEquals(P4, a.zpristupniPobocku(Pozice.PRVNI));
        assertEquals(P1, a.zpristupniPobocku(Pozice.NASLEDNIK));
        assertEquals(P3, a.zpristupniPobocku(Pozice.NASLEDNIK));
        assertEquals(P2, a.zpristupniPobocku(Pozice.NASLEDNIK));
    }

    @Test
    public void zpristupniPobockuTest() throws Exception {
        IAutopujcovna a = new Autopujcovna("Test", P1, P2, P3, P4);
        assertEquals(P1, a.zpristupniPobocku(Pozice.PRVNI));
        assertEquals(P2, a.zpristupniPobocku(Pozice.NASLEDNIK));
        assertEquals(P4, a.zpristupniPobocku(Pozice.POSLEDNI));
        assertEquals(P3, a.zpristupniPobocku(Pozice.PREDCHUDCE));
        assertEquals(P3, a.zpristupniPobocku(Pozice.AKTUALNI));
    }

    @Test
    public void zpristupniPobockuNullPoziceTest() throws Exception {
        IAutopujcovna a = new Autopujcovna("Test", P1, P2, P3, P4);
        try {
            a.zpristupniPobocku(null);
            fail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void zpristupniPobockuPrazdnySeznamTest() throws Exception {
        IAutopujcovna a = new Autopujcovna("Test");
        try {
            a.zpristupniPobocku(Pozice.PRVNI);
            fail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void odeberPobockuTest() throws Exception {
        IAutopujcovna a = new Autopujcovna("Test", P1, P2, P3, P4, P1);
        a.zpristupniPobocku(Pozice.PRVNI);
        assertEquals(P1, a.odeberPobocku(Pozice.AKTUALNI));
        assertEquals(P2, a.odeberPobocku(Pozice.PRVNI));
        a.zpristupniPobocku(Pozice.PRVNI);
        assertEquals(P4, a.odeberPobocku(Pozice.NASLEDNIK));
        a.zpristupniPobocku(Pozice.POSLEDNI);
        assertEquals(P3, a.odeberPobocku(Pozice.PREDCHUDCE));
        assertEquals(P1, a.odeberPobocku(Pozice.POSLEDNI));
    }

    @Test
    public void odeberPobockuPrazdnySeznam() throws Exception {
        IAutopujcovna a = new Autopujcovna("Test");
        try {
            a.odeberPobocku(Pozice.PRVNI);
            fail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void odeberPobockuNullPozice() throws Exception {
        IAutopujcovna a = new Autopujcovna("Test", P1, P2, P3, P4);
        try {
            a.odeberPobocku(null);
            fail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void vlozAutoTest() throws Exception {
        IAutopujcovna a = new Autopujcovna("Test", P5, P2, P3);

        a.zpristupniPobocku(Pozice.PRVNI);
        a.vlozAuto(A1, Pozice.PRVNI);
        a.vlozAuto(A2, Pozice.POSLEDNI);
        a.zpristupniAuto(Pozice.PRVNI);
        a.vlozAuto(A3, Pozice.NASLEDNIK);
        a.vlozAuto(A4, Pozice.PREDCHUDCE);

        Iterator<Auto> i = a.zpristupniPobocku(Pozice.AKTUALNI).iterator();
        assertEquals(A4, i.next());
        assertEquals(A1, i.next());
        assertEquals(A3, i.next());
        assertEquals(A2, i.next());
    }

    @Test
    public void vlozAutoNullTest() throws Exception {
        try {
            IAutopujcovna a = new Autopujcovna("Test", P1, P2, P3);
            a.zpristupniPobocku(Pozice.PRVNI);
            a.vlozAuto(null, Pozice.PRVNI);
            fail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}