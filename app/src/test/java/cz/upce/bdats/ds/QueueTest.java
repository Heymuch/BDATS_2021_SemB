package cz.upce.bdats.ds;

import org.junit.Test;
import static org.junit.Assert.*;

public class QueueTest {
    private static final Integer INT1 = 10;
    private static final Integer INT2 = 20;
    private static final Integer INT3 = 30;

    @Test
    public void sizeOfEmptyQueueTest() {
        IQueue<Integer> q = new Queue<>();
        assertEquals(0, q.size());
    }

    @Test
    public void sizeOfQueueAfterPushTest() {
        IQueue<Integer> q = new Queue<>();
        q.push(10);
        assertEquals(1, q.size());
    }

    @Test
    public void sizeOfQueueAfterPopTest() throws Exception {
        IQueue<Integer> q = new Queue<>();
        q.push(10);
        q.pop();
        assertEquals(0, q.size());
    }

    @Test
    public void pushAndPopTest() throws Exception {
        IQueue<Integer> q = new Queue<>();
        q.push(INT1);
        q.push(INT2);
        q.push(INT3);

        assertEquals(INT1, q.pop());
        assertEquals(INT2, q.pop());
        assertEquals(INT3, q.pop());
    }

    @Test
    public void popFromEmptyQueueTest() {
        try {
            IQueue<Integer> q = new Queue<>();
            q.pop();
            fail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}