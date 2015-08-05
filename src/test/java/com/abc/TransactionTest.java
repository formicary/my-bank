package test.java.com.abc;

import static org.junit.Assert.assertTrue;
import main.java.com.abc.Transaction;

import org.junit.Test;

public class TransactionTest {
    @Test
    public void transaction() {
        Transaction t = new Transaction(5);
        assertTrue(t instanceof Transaction);
    }
}
