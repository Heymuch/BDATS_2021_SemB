package cz.upce.bdats.autopujcovna;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.logging.Logger;

public class PobockaTest {
    // Testovací data
    private static final Auto OSO1 = new OsobniAuto("1SA 1100", Barva.CERNA);
    private static final Auto OSO2 = new OsobniAuto("2SB 2200", Barva.CERVENA);
    private static final Auto NAK1 = new UzitkoveAuto("3SC 3300", 1.5f);
    private static final Auto NAK2 = new UzitkoveAuto("4SD 4400", 3.0f);

    @Test
    public void vlozAutoPrvniTest() throws Exception {
        IPobocka p = new Pobocka("Test");
        p.vlozAuto(OSO1, Pozice.PRVNI);
        p.vlozAuto(OSO2, Pozice.PRVNI);

        Iterator<Auto> i = p.iterator();
        assertEquals(OSO2, i.next());
        assertEquals(OSO1, i.next());
    }

    @Test
    public void vlozAutoPosledniTest() throws Exception {
        IPobocka p = new Pobocka("Test");
        p.vlozAuto(OSO1, Pozice.POSLEDNI);
        p.vlozAuto(OSO2, Pozice.POSLEDNI);

        Iterator<Auto> i = p.iterator();
        assertEquals(OSO1, i.next());
        assertEquals(OSO2, i.next());
    }

    @Test
    public void vloAutoPredchudceTest() throws Exception {
        IPobocka p = new Pobocka("Test", OSO1);
        p.zpristupniAuto(Pozice.PRVNI);
        p.vlozAuto(OSO2, Pozice.PREDCHUDCE);

        Iterator<Auto> i = p.iterator();
        assertEquals(OSO2, i.next());
        assertEquals(OSO1, i.next());
    }

    @Test
    public void vlozAutoNaslednikTest() throws Exception {
        IPobocka p = new Pobocka("Test", OSO1);
        p.zpristupniAuto(Pozice.PRVNI);
        p.vlozAuto(OSO2, Pozice.NASLEDNIK);

        Iterator<Auto> i = p.iterator();
        assertEquals(OSO1, i.next());
        assertEquals(OSO2, i.next());
    }

    @Test
    public void vlozAutoNepodporovanaPoziceTest() throws Exception {
        IPobocka p = new Pobocka("Test");
        try {
            p.vlozAuto(OSO1, Pozice.AKTUALNI);
        } catch (Pobocka.PobockaException e) {
            Logger.getGlobal().info(e.getMessage() + " - " + e.getCause().getMessage());
        }
    }

    @Test
    public void vlozAutoNullAutoTest() throws Exception {
        IPobocka p = new Pobocka("Test");
        try {
            p.vlozAuto(null, Pozice.PRVNI);
        } catch (Pobocka.PobockaException e) {
            Logger.getGlobal().info(e.getMessage() + " - " + e.getCause().getMessage());
        }
    }

    @Test
    public void zpristupniAutoTest() throws Exception {
        IPobocka p = new Pobocka("Test", OSO1, OSO2, NAK1, NAK2);
        assertEquals(OSO1, p.zpristupniAuto(Pozice.PRVNI));
        assertEquals(OSO2, p.zpristupniAuto(Pozice.NASLEDNIK));
        assertEquals(NAK2, p.zpristupniAuto(Pozice.POSLEDNI));
        assertEquals(NAK1, p.zpristupniAuto(Pozice.PREDCHUDCE));
        assertEquals(NAK1, p.zpristupniAuto(Pozice.AKTUALNI));
    }

    @Test
    public void odeberAutoTest() throws Exception {
        IPobocka p = new Pobocka("Test", OSO1, OSO2, NAK1, NAK2, OSO1, OSO2);

        assertEquals(OSO1, p.odeberAuto(Pozice.PRVNI));
        p.zpristupniAuto(Pozice.PRVNI);
        assertEquals(NAK1, p.odeberAuto(Pozice.NASLEDNIK));
        p.zpristupniAuto(Pozice.POSLEDNI);
        assertEquals(OSO1, p.odeberAuto(Pozice.PREDCHUDCE));
        assertEquals(OSO2, p.odeberAuto(Pozice.POSLEDNI));
    }
}