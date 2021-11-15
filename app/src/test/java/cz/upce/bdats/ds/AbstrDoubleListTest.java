package cz.upce.bdats.ds;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.logging.Logger;
import java.util.Iterator;

public class AbstrDoubleListTest {
    private static final Integer INT10 = 10;
    private static final Integer INT20 = 20;
    private static final Integer INT30 = 30;
    private static final Integer INT40 = 40;

    @Test
    public void prazdnyTest() {
        IAbstrDoubleList<Integer> l = new AbstrDoubleList<>();
        assertTrue(l.jePrazdny());
    }

    @Test
    public void neniPrazdnyTest() {
        IAbstrDoubleList<Integer> l = new AbstrDoubleList<>();
        l.vlozPrvni(10);
        assertFalse(l.jePrazdny());
    }

    @Test
    public void prazdnyPoZruseniTest() {
        IAbstrDoubleList<Integer> l = new AbstrDoubleList<>();
        l.vlozPrvni(10);
        l.zrus();
        assertTrue(l.jePrazdny());
    }

    @Test(expected=AbstrDoubleList.ListException.class)
    public void zpristupniAktualniPrazdnySeznamTest() throws Exception {
        IAbstrDoubleList<Integer> l = new AbstrDoubleList<>();
        l.zpristupniAktualni();
        fail();
    }

    @Test(expected=AbstrDoubleList.ListException.class)
    public void zpristupniPrvniPrazdnySeznamTest() throws Exception {
        IAbstrDoubleList<Integer> l = new AbstrDoubleList<>();
        l.zpristupniPrvni();
        fail();
    }

    @Test
    public void zpristupniPrvniPlnySeznamTest() throws Exception {
        IAbstrDoubleList<Integer> l = new AbstrDoubleList<>(INT10, INT20, INT30);
        assertEquals(INT10, l.zpristupniPrvni());
        assertEquals(INT10, l.zpristupniAktualni());
    }

    @Test(expected=AbstrDoubleList.ListException.class)
    public void zpristupniPosledniPrazdnySeznamTest() throws Exception {
        IAbstrDoubleList<Integer> l = new AbstrDoubleList<>();
        l.zpristupniPosledni();
        fail();
    }

    @Test
    public void zpristupniPosledniPlnySeznamTest() throws Exception {
        IAbstrDoubleList<Integer> l = new AbstrDoubleList<>(INT10, INT20, INT30);
        assertEquals(INT30, l.zpristupniPosledni());
        assertEquals(INT30, l.zpristupniAktualni());
    }

    @Test(expected=AbstrDoubleList.ListException.class)
    public void zpristupniNaslednikaPrazdnySeznamTest() throws Exception {
        IAbstrDoubleList<Integer> l = new AbstrDoubleList<>();
        l.zpristupniNaslednika();
        fail();
    }

    @Test(expected=AbstrDoubleList.ListException.class)
    public void zpristupniNaslednikaPosledniTest() throws Exception {
        IAbstrDoubleList<Integer> l = new AbstrDoubleList<>(INT10, INT20, INT30);
        l.zpristupniPosledni();
        l.zpristupniNaslednika();
        fail();
    }

    public void zpristupniNaslednikaPlnySeznamTest() throws Exception {
        IAbstrDoubleList<Integer> l = new AbstrDoubleList<>(INT10, INT20, INT30);
        l.zpristupniPrvni();
        assertEquals(INT20, l.zpristupniNaslednika());
    }

    @Test(expected=AbstrDoubleList.ListException.class)
    public void zpristupniPredchudcePrazdnySeznamTest() throws Exception {
        IAbstrDoubleList l = new AbstrDoubleList();
        l.zpristupniPredchudce();
        fail();
    }

    @Test(expected=AbstrDoubleList.ListException.class)
    public void zpristupniPredchudcePrvniTest() throws Exception {
        IAbstrDoubleList<Integer> l = new AbstrDoubleList<>(INT10, INT20, INT30);
        l.zpristupniPrvni();
        l.zpristupniPredchudce();
        fail();
    }

    @Test
    public void zpristupniPredchudcePlnySeznamTest() throws Exception {
        IAbstrDoubleList<Integer> l = new AbstrDoubleList<>(INT10, INT20, INT30);
        l.zpristupniPosledni();
        assertEquals(INT20, l.zpristupniPredchudce());
    }

    @Test
    public void vlozPrvniPrazdnySeznamTest() throws Exception {
        IAbstrDoubleList<Integer> l = new AbstrDoubleList<>();
        l.vlozPrvni(INT10);
        assertEquals(INT10, l.zpristupniPrvni());
        assertEquals(INT10, l.zpristupniPosledni());
    }

    @Test
    public void vlozPrvniPlnySeznamTest() throws Exception {
        IAbstrDoubleList<Integer> l = new AbstrDoubleList<>(INT10, INT20, INT30);
        l.vlozPrvni(INT40);
        assertEquals(INT40, l.zpristupniPrvni());
        assertEquals(INT10, l.zpristupniNaslednika());
    }

    @Test
    public void vlozPosledniPradnySeznamTest() throws Exception {
        IAbstrDoubleList<Integer> l = new AbstrDoubleList<>();
        l.vlozPosledni(INT10);
        assertEquals(INT10, l.zpristupniPrvni());
        assertEquals(INT10, l.zpristupniPosledni());
    }

    @Test
    public void vlozPosledniPlnySeznamTest() throws Exception {
        IAbstrDoubleList<Integer> l = new AbstrDoubleList<>(INT10, INT20, INT30);
        l.vlozPosledni(INT40);
        assertEquals(INT40, l.zpristupniPosledni());
        assertEquals(INT30, l.zpristupniPredchudce());
    }

    @Test
    public void vlozNaslednikaPosledniTest() throws Exception {
        IAbstrDoubleList<Integer> l = new AbstrDoubleList<>(INT10, INT20, INT30);
        l.zpristupniPosledni();
        l.vlozNaslednika(INT40);
        assertEquals(INT40, l.zpristupniNaslednika());
        assertEquals(INT30, l.zpristupniPredchudce());
    }

    @Test
    public void vlozNaslednikaPlnySeznamTest() throws Exception {
        IAbstrDoubleList<Integer> l = new AbstrDoubleList<>(INT10, INT20, INT30);
        l.zpristupniPrvni();
        l.vlozNaslednika(INT40);
        assertEquals(INT40, l.zpristupniNaslednika());
        assertEquals(INT20, l.zpristupniNaslednika());
        assertEquals(INT40, l.zpristupniPredchudce());
        assertEquals(INT10, l.zpristupniPredchudce());
    }

    @Test(expected=AbstrDoubleList.ListException.class)
    public void vlozNaslednikaNeniAktualniTest() throws Exception {
        IAbstrDoubleList<Integer> l = new AbstrDoubleList<>();
        l.vlozNaslednika(INT40);
        fail();
    }

    @Test
    public void vlozPredchudcePrvniTest() throws Exception {
        IAbstrDoubleList<Integer> l = new AbstrDoubleList<>(INT10, INT20, INT30);
        l.zpristupniPrvni();
        l.vlozPredchudce(INT40);
        assertEquals(INT40, l.zpristupniPredchudce());
        assertEquals(INT10, l.zpristupniNaslednika());
    }

    @Test
    public void vlozPredchudcePlnySeznamTest() throws Exception {
        IAbstrDoubleList<Integer> l = new AbstrDoubleList<>(INT10, INT20, INT30);
        l.zpristupniPosledni();
        l.vlozPredchudce(INT40);
        assertEquals(INT40, l.zpristupniPredchudce());
        assertEquals(INT20, l.zpristupniPredchudce());
        assertEquals(INT40, l.zpristupniNaslednika());
        assertEquals(INT30, l.zpristupniNaslednika());
    }

    @Test(expected=AbstrDoubleList.ListException.class)
    public void vlozPredchudceNeniAktualniTest() throws Exception {
        IAbstrDoubleList<Integer> l = new AbstrDoubleList<>(INT10, INT20, INT30);
        l.vlozPredchudce(INT40);
        fail();
    }

    @Test
    public void odeberAktualniPlnySeznamTest() throws Exception {
        IAbstrDoubleList<Integer> l = new AbstrDoubleList<>(INT10, INT20, INT30);
        l.zpristupniPrvni();
        l.zpristupniNaslednika();
        assertEquals(INT20, l.odeberAktualni());
        assertEquals(INT10, l.zpristupniPrvni());
        assertEquals(INT30, l.zpristupniNaslednika());
        assertEquals(INT10, l.zpristupniPredchudce());
    }

    @Test
    public void odeberAktualniPrvniTest() throws Exception {
        IAbstrDoubleList<Integer> l = new AbstrDoubleList<>(INT10, INT20, INT30);
        l.zpristupniPrvni();
        assertEquals(INT10, l.odeberAktualni());
        assertEquals(INT20, l.zpristupniPrvni());
    }

    @Test
    public void odeberAktualniPosledniTest() throws Exception {
        IAbstrDoubleList<Integer> l = new AbstrDoubleList<>(INT10, INT20, INT30);
        l.zpristupniPosledni();
        assertEquals(INT30, l.odeberAktualni());
        assertEquals(INT20, l.zpristupniPosledni());
    }

    @Test
    public void odeberAktualniNeniAktualniTest() throws Exception {
        IAbstrDoubleList<Integer> l = new AbstrDoubleList<>(INT10, INT20, INT30);
        try {
            l.odeberAktualni();
            fail();
        } catch (AbstrDoubleList.ListException exc) {
            Logger.getGlobal().info(exc.getMessage());
        }
    }

    @Test
    public void odeberPrvniPlnySeznamTest() throws Exception {
        IAbstrDoubleList<Integer> l = new AbstrDoubleList<>(INT10, INT20, INT30);
        assertEquals(INT10, l.odeberPrvni());
        assertEquals(INT20, l.zpristupniPrvni());
        try {
            l.zpristupniPredchudce();
            fail();
        } catch (AbstrDoubleList.ListException exc) {
            Logger.getGlobal().info(exc.getMessage());
        }
    }

    @Test
    public void odeberPrvniJedenPrvekTest() throws Exception {
        IAbstrDoubleList<Integer> l = new AbstrDoubleList<>(INT10);
        assertEquals(INT10, l.odeberPrvni());
        assertTrue(l.jePrazdny());
    }

    @Test
    public void odeberPrvniPrazdnySeznamTest() throws Exception {
        IAbstrDoubleList l = new AbstrDoubleList<>();
        try {
            l.odeberPrvni();
            fail();
        } catch (AbstrDoubleList.ListException exc) {
            Logger.getGlobal().info(exc.getMessage());
        }
    }

    @Test
    public void odeberPosledniPlnySeznamTest() throws Exception {
        IAbstrDoubleList<Integer> l = new AbstrDoubleList<>(INT10, INT20, INT30);
        assertEquals(INT30, l.odeberPosledni());
        assertEquals(INT20, l.zpristupniPosledni());
        try {
            l.zpristupniNaslednika();
            fail();
        } catch (AbstrDoubleList.ListException exc) {
            Logger.getGlobal().info(exc.getMessage());
        }
    }

    @Test
    public void odeberPosledniJedenPrvekTest() throws Exception {
        IAbstrDoubleList<Integer> l = new AbstrDoubleList<>(INT10);
        assertEquals(INT10, l.odeberPosledni());
        assertTrue(l.jePrazdny());
    }

    @Test
    public void odeberPosledniPrazdnySeznamTest() throws Exception {
        IAbstrDoubleList l = new AbstrDoubleList();
        try {
            l.odeberPosledni();
            fail();
        } catch (AbstrDoubleList.ListException exc) {
            Logger.getGlobal().info(exc.getMessage());
        }
    }

    @Test
    public void odeberNaslednikaTest() throws Exception {
        IAbstrDoubleList<Integer> l = new AbstrDoubleList<>(INT10, INT20, INT30);
        l.zpristupniPrvni();
        assertEquals(INT20, l.odeberNaslednika());
        assertEquals(INT10, l.zpristupniAktualni());
        assertEquals(INT30, l.zpristupniNaslednika());
        assertEquals(INT10, l.zpristupniPredchudce());
    }

    @Test
    public void odeberNaslednikaPosledniTest() throws Exception {
        IAbstrDoubleList<Integer> l = new AbstrDoubleList<>(INT10, INT20, INT30);
        l.zpristupniPrvni();
        l.zpristupniNaslednika();
        assertEquals(INT30, l.odeberNaslednika());
        assertEquals(INT20, l.zpristupniAktualni());
        assertEquals(INT20, l.zpristupniPosledni());
        assertEquals(INT10, l.zpristupniPredchudce());
    }

    @Test
    public void odeberNaslednikaNeniAktualniTest() throws Exception {
        IAbstrDoubleList<Integer> l = new AbstrDoubleList<>(INT10, INT20, INT30);
        try {
            l.odeberNaslednika();
            fail();
        } catch (AbstrDoubleList.ListException e) {
            Logger.getGlobal().info(e.getMessage());
        }
    }

    @Test
    public void odeberNaslednikaKonecTest() throws Exception {
        IAbstrDoubleList<Integer> l = new AbstrDoubleList<>(INT10, INT20, INT30);
        l.zpristupniPosledni();
        try {
            l.odeberNaslednika();
            fail();
        } catch (AbstrDoubleList.ListException e) {
            Logger.getGlobal().info(e.getMessage());
        }
    }

    @Test
    public void odeberPredchudceTest() throws Exception {
        IAbstrDoubleList<Integer> l = new AbstrDoubleList<>(INT10, INT20, INT30);
        l.zpristupniPosledni();
        assertEquals(INT20, l.odeberPredchudce());
        assertEquals(INT30, l.zpristupniAktualni());
        assertEquals(INT10, l.zpristupniPredchudce());
        assertEquals(INT30, l.zpristupniNaslednika());
    }

    @Test
    public void odeberPredchudcePrvniTest() throws Exception {
        IAbstrDoubleList<Integer> l = new AbstrDoubleList<>(INT10, INT20, INT30);
        l.zpristupniPrvni();
        l.zpristupniNaslednika();
        assertEquals(INT10, l.odeberPredchudce());
        assertEquals(INT20, l.zpristupniAktualni());
        assertEquals(INT20, l.zpristupniPrvni());
        try {
            l.zpristupniPredchudce();
            fail();
        } catch (AbstrDoubleList.ListException e) {
            Logger.getGlobal().info(e.getMessage());
        }
    }

    @Test
    public void odeberPredchudceNeniAktualniTest() throws Exception {
        IAbstrDoubleList<Integer> l = new AbstrDoubleList<>(INT10, INT20, INT30);
        try {
            l.odeberPredchudce();
            fail();
        } catch (AbstrDoubleList.ListException e) {
            Logger.getGlobal().info(e.getMessage());
        }
    }

    @Test
    public void odeberPredchudceKonecTest() throws Exception {
        IAbstrDoubleList<Integer> l = new AbstrDoubleList<>(INT10, INT20, INT30);
        l.zpristupniPrvni();
        try {
            l.odeberPredchudce();
            fail();
        } catch (AbstrDoubleList.ListException e) {
            Logger.getGlobal().info(e.getMessage());
        }
    }

    @Test
    public void iteratorTest() throws Exception {
        IAbstrDoubleList<Integer> l = new AbstrDoubleList<>(INT10, INT20, INT30);
        Iterator<Integer> i = l.iterator();
        assertTrue(i.hasNext());
        assertEquals(INT10, i.next());
        assertEquals(INT20, i.next());
        assertEquals(INT30, i.next());
        assertFalse(i.hasNext());
    }

    @Test
    public void iteratorPrazdnySeznamTest() throws Exception {
        Iterator<Integer> i = new AbstrDoubleList<Integer>().iterator();
        try {
            i.next();
            fail();
        } catch (Exception exc) {
            Logger.getGlobal().info(exc.getMessage());
        }
    }
}