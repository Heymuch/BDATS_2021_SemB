package cz.upce.bdats.data;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Iterator;

import cz.upce.bdats.autopujcovna.Auto;
import cz.upce.bdats.autopujcovna.OsobniAuto;
import cz.upce.bdats.autopujcovna.UzitkoveAuto;
import cz.upce.bdats.autopujcovna.Barva;
import cz.upce.bdats.autopujcovna.IPobocka;
import cz.upce.bdats.autopujcovna.IteratorTyp;
import cz.upce.bdats.autopujcovna.IAutopujcovna;

public class PersistenceTest {
    private static final Auto A1 = new OsobniAuto("1S1 1111", 1.0f, 1, Barva.CERVENA);
    private static final Auto A2 = new OsobniAuto("2S2 2222", 2.0f, 2, Barva.CERNA);
    private static final Auto A3 = new UzitkoveAuto("3S3 3333", 3.0f, 3, 10.0f);
    private static final Auto A4 = new UzitkoveAuto("4S4 4444", 4.0f, 4, 20.0f);

    @Test
    public void autoToCSVTest() throws Exception {
        String csv1 = Persistence.Automobily.toCSV(A1);
        String csv2 = Persistence.Automobily.toCSV(A2);
        String csv3 = Persistence.Automobily.toCSV(A3);
        String csv4 = Persistence.Automobily.toCSV(A4);

        System.out.println(csv1);
        System.out.println(csv2);
        System.out.println(csv3);
        System.out.println(csv4);
        System.out.println();
    }

    @Test
    public void autoFromCSVTest() throws Exception {
        Auto a1 = Persistence.Automobily.fromCSV(Persistence.Automobily.toCSV(A1));
        Auto a2 = Persistence.Automobily.fromCSV(Persistence.Automobily.toCSV(A2));
        Auto a3 = Persistence.Automobily.fromCSV(Persistence.Automobily.toCSV(A3));
        Auto a4 = Persistence.Automobily.fromCSV(Persistence.Automobily.toCSV(A4));

        System.out.println(a1);
        System.out.println(a2);
        System.out.println(a3);
        System.out.println(a4);
        System.out.println();
    }

    @Test
    public void autoToBinTest() throws Exception {
        byte[] b1 = Persistence.Automobily.toBin(A1);
        byte[] b2 = Persistence.Automobily.toBin(A3);

        System.out.println(Arrays.toString(b1));
        System.out.println(Arrays.toString(b2));
        System.out.println();
    }

    @Test
    public void autoFromBinTest() throws Exception {
        Auto a1 = Persistence.Automobily.fromBin(Persistence.Automobily.toBin(A1));
        Auto a2 = Persistence.Automobily.fromBin(Persistence.Automobily.toBin(A3));

        System.out.println(a1);
        System.out.println(a2);
        System.out.println();
    }

    @Test
    public void pobockaToCSVTest() throws Exception {
        IPobocka p = Generator.genPobocka(3);
        String[] csv = Persistence.Pobocky.toCSV(p);
        for (String s : csv)
            System.out.println(s);
        System.out.println();
    }

    @Test
    public void pobockaFromCSVTest() throws Exception {
        IPobocka p = Generator.genPobocka(5);
        String[] csv = Persistence.Pobocky.toCSV(p);
        p = Persistence.Pobocky.fromCSV(csv);
        System.out.println(p);
        Iterator<Auto> i = p.iterator();
        while(i.hasNext())
            System.out.println(i.next());
        System.out.println();
    }

    @Test
    public void autopujcovnaToCSVTest() throws Exception {
        String[] csv = Persistence.Autopujcovny.toCSV(Generator.genAutopujcovna(3, 3, 3));
        for (String s : csv)
            System.out.println(s);
        System.out.println();
    }

    @Test
    public void autopujcovnaFromCSVTest() throws Exception {
        String[] csv = Persistence.Autopujcovny.toCSV(Generator.genAutopujcovna(3, 3, 3));
        IAutopujcovna a = Persistence.Autopujcovny.fromCSV(csv);
        System.out.println(a);

        Iterator<Auto> v = a.iterator(IteratorTyp.VYPUJCENE_AUTOMOBILY);
        while (v.hasNext())
            System.out.println(v.next());

        Iterator<IPobocka> p = a.iterator(IteratorTyp.POBOCKY);
        while (p.hasNext()) {
            IPobocka pob = p.next();
            System.out.println(pob);
            Iterator<Auto> i = pob.iterator();
            while (i.hasNext()) {
                System.out.println(i.next());
            }
        }
        System.out.println();
    }

}