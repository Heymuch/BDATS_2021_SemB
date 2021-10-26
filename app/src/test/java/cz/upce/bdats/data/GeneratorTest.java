package cz.upce.bdats.data;

import java.util.Iterator;
import cz.upce.bdats.autopujcovna.Auto;
import cz.upce.bdats.autopujcovna.IPobocka;
import cz.upce.bdats.autopujcovna.IAutopujcovna;
import cz.upce.bdats.autopujcovna.IteratorTyp;

import org.junit.Test;
import static org.junit.Assert.*;

public class GeneratorTest {
    @Test
    public void genSPZTest() {
        for (int i = 0; i < 10; i++) {
            System.out.println(Generator.genSPZ());
        }
        System.out.println();
    }

    @Test
    public void genAutoTest() {
        for (int i = 0; i < 10; i++) {
            System.out.println(Generator.genAuto());
        }
        System.out.println();
    }

    @Test
    public void genPobockaTest() throws Exception {
        for (int i = 0; i < 3; i++) {
            IPobocka p = Generator.genPobocka(5);
            System.out.println(p);
            Iterator<Auto> it = p.iterator();
            while(it.hasNext())
                System.out.println(it.next());
        }
        System.out.println();
    }

    @Test
    public void genAutopujcovnaTest() throws Exception {
        IAutopujcovna a = Generator.genAutopujcovna(3, 5, 0);
        System.out.println(a);
        Iterator<IPobocka> pobocky = a.iterator(IteratorTyp.POBOCKY);
        while(pobocky.hasNext()) {
            IPobocka p = pobocky.next();
            System.out.println(p);
            Iterator<Auto> v = p.iterator();
            while (v.hasNext()) {
                System.out.println(v.next());
            }
        }
        System.out.println();
    }
}