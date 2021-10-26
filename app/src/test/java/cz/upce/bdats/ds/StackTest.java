package cz.upce.bdats.ds;

import org.junit.Test;
import static org.junit.Assert.*;

public class StackTest {
    private static final Integer INT1 = 10;
    private static final Integer INT2 = 20;
    private static final Integer INT3 = 30;

    @Test
    public void sizeOfEmptyStackTest() {
        IStack<Integer> s = new Stack<>();
        assertEquals(0, s.size());
    }

    @Test
    public void sizeOfStackAfterPushTest() {
        IStack<Integer> s = new Stack<>();
        s.push(10);
        assertEquals(1, s.size());
    }

    @Test
    public void sizeOfStackAfterPopTest() throws Exception {
        IStack<Integer> s = new Stack<>();
        s.push(10);
        s.pop();
        assertEquals(0, s.size());
    }

    @Test
    public void pushAndPopTest() throws Exception {
        IStack<Integer> s = new Stack<>();
        s.push(INT1);
        s.push(INT2);
        s.push(INT3);

        assertEquals(INT3, s.pop());
        assertEquals(INT2, s.pop());
        assertEquals(INT1, s.pop());
    }

    @Test
    public void popFromEmptyStackTest() {
        try {
            IStack<Integer> s = new Stack<>();
            s.pop();
            fail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}