package src.main.java.com.abc;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TransactionTest {
    /*
     * test works
     */
    @Test
    public void transaction() {
        Transaction t = new Transaction(5);
        assertTrue(t instanceof Transaction);
    }
}