package test.com.abc;

import org.junit.Test;
import com.abc.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TransactionTest {
    @Test
    public void transaction() {
        Transaction t = new Transaction(5);
        assertTrue(t instanceof Transaction);
    }

    @Test
    public void testBefore() {
        Transaction t = new Transaction(5);
        assertFalse(t.before(10));
    }
}
