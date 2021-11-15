package cz.upce.bdats.ds;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Iterator;

public class TableTest {
    private static class A {
        String key;
        int value;

        A(String key, int value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return String.format("[%s;%d]", key, value);
        }
    }

    private static final A O1 = new A("A", 1);
    private static final A O2 = new A("B", 2);
    private static final A O3 = new A("C", 3);
    private static final A O4 = new A("D", 4);
    private static final A O5 = new A("E", 5);
    private static final A O6 = new A("F", 6);

    @Test
    public void isNewTableEmptyTest() {
        ITable<String, A> t = new Table<>();
        assertTrue(t.isEmpty());
    }

    @Test
    public void isNotEmptyAfterAddTest() throws Exception {
        ITable<String, A> t = new Table<>();
        t.add(O1.key, O1);
        assertFalse(t.isEmpty());
    }

    @Test
    public void isEmptyAfterClear() throws Exception {
        ITable<String, A> t = new Table<>();
        t.add(O1.key, O1);
        t.clear();
        assertTrue(t.isEmpty());
    }

    @Test
    public void findExistingKeyTest() throws Exception {
        ITable<String, A> t = new Table<>();
        t.add(O3.key, O3);
        t.add(O2.key, O2);
        t.add(O5.key, O5);
        t.add(O1.key, O1);
        t.add(O4.key, O4);

        assertEquals(O1, t.find("A"));
        assertEquals(O3, t.find("C"));
        assertEquals(O2, t.find("B"));
        assertEquals(O5, t.find("E"));
        assertEquals(O4, t.find("D"));
    }

    @Test
    public void findNonExistingKeyTest() throws Exception {
        try {
            ITable<String, A> t = new Table<>();
            t.add(O1.key, O1);
            t.add(O2.key, O2);
            t.add(O3.key, O3);
            t.find(O4.key);
            fail();
        } catch (Exception e) {}
    }

    @Test
    public void findNullKeyTest() {
        try {
            ITable<String, A> t = new Table<>();
            t.add(O1.key, O1);
            t.add(O2.key, O2);
            t.add(O3.key, O3);
            t.find(null);
            fail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void findInEmptyTableTest() {
        try {
            ITable<String, A> t = new Table<>();
            t.find(O1.key);
            fail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void breadthIteratorTest() throws Exception {
        ITable<String, A> t = new Table<>();
        t.add(O3.key, O3);
        t.add(O2.key, O2);
        t.add(O5.key, O5);
        t.add(O1.key, O1);

        Iterator<A> i = t.iterator(IterationType.BREADTH);
        assertEquals(O3, i.next());
        assertEquals(O2, i.next());
        assertEquals(O5, i.next());
        assertEquals(O1, i.next());
    }

    @Test
    public void breadthIteratorEmptyTable() {
        try {
            ITable<String, A> t = new Table<>();
            t.iterator(IterationType.BREADTH);
            fail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void depthIteratorTest() throws Exception {
        ITable<String, A> t = new Table<>();
        t.add(O3.key, O3);
        t.add(O2.key, O2);
        t.add(O5.key, O5);
        t.add(O1.key, O1);

        Iterator<A> i = t.iterator(IterationType.DEPTH);
        assertEquals(O3, i.next());
        assertEquals(O2, i.next());
        assertEquals(O1, i.next());
        assertEquals(O5, i.next());
    }

    @Test
    public void depthIteratorEmptyTableTest() throws Exception {
        try {
            ITable<String, A> t = new Table<>();
            t.iterator(IterationType.DEPTH);
            fail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void removeRootAsLeafTest() throws Exception {
        ITable<String, A> t = new Table<>();
        t.add(O1.key, O1);
        A value = t.remove(O1.key);

        assertEquals(O1, value);
        assertTrue(t.isEmpty());
    }

    @Test
    public void removeRootWithChildren() throws Exception {
        ITable<String, A> t = new Table<>();
        t.add(O3.key, O3);
        t.add(O2.key, O2);
        t.add(O5.key, O5);
        t.add(O1.key, O1);
        t.add(O4.key, O4);
        t.add(O6.key, O6);

        A value = t.remove(O3.key);
        assertEquals(O3, value);

        Iterator<A> it = t.iterator(IterationType.BREADTH);
        assertEquals(O4, it.next());
        assertEquals(O2, it.next());
        assertEquals(O5, it.next());
        assertEquals(O1, it.next());
        assertEquals(O6, it.next());
    }

    @Test
    public void removeLeafTest() throws Exception {
        ITable<String, A> t = new Table<>();
        t.add(O3.key, O3);
        t.add(O1.key, O1);
        t.add(O4.key, O4);
        t.add(O5.key, O5);

        A value = t.remove(O5.key);
        assertEquals(O5, value);

        Iterator<A> it = t.iterator(IterationType.BREADTH);
        assertEquals(O3, it.next());
        assertEquals(O1, it.next());
        assertEquals(O4, it.next());
    }

    @Test
    public void removeWithTwoChildrenTest() throws Exception {
        ITable<String, A> t = new Table<>();
        t.add(O1.key, O1);
        t.add(O3.key, O3);
        t.add(O2.key, O2);
        t.add(O5.key, O5);
        t.add(O4.key, O4);
        t.add(O6.key, O6);

        A value = t.remove(O3.key);
        assertEquals(O3, value);

        Iterator<A> it = t.iterator(IterationType.BREADTH);
        assertEquals(O1, it.next());
        assertEquals(O4, it.next());
        assertEquals(O2, it.next());
        assertEquals(O5, it.next());
        assertEquals(O6, it.next());
    }

    @Test
    public void removeWithLeftChildTest() throws Exception {
        ITable<String, A> t = new Table<>();
        t.add(O1.key, O1);
        t.add(O6.key, O6);
        t.add(O3.key, O3);
        t.add(O2.key, O2);
        t.add(O4.key, O4);

        A value = t.remove(O6.key);
        assertEquals(O6, value);

        Iterator<A> it = t.iterator(IterationType.BREADTH);
        //while (it.hasNext()) System.out.println(it.next());

        assertEquals(O1, it.next());
        assertEquals(O4, it.next());
        assertEquals(O3, it.next());
        assertEquals(O2, it.next());
    }

    @Test
    public void removeWithRightChildTest() throws Exception {
        ITable<String, A> t = new Table<>();
        t.add(O1.key, O1);
        t.add(O2.key, O2);
        t.add(O5.key, O5);
        t.add(O4.key, O4);
        t.add(O6.key, O6);

        A value = t.remove(O2.key);
        assertEquals(O2, value);

        Iterator<A> it = t.iterator(IterationType.BREADTH);
        assertEquals(O1, it.next());
        assertEquals(O4, it.next());
        assertEquals(O5, it.next());
        assertEquals(O6, it.next());
    }

    @Test
    public void removeFromEmptyTableTest() {
        try {
            ITable<String, A> t = new Table<>();
            t.remove(O1.key);
            fail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void removeNullKeyTest() {
        try {
            ITable<String, A> t = new Table<>();
            t.add(O1.key, O1);
            t.remove(null);
            fail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void removeNonExistingKeyTest() {
        try {
            ITable<String, A> t = new Table<>();
            t.add(O1.key, O1);
            t.remove(O2.key);
            fail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}