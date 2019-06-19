package test.java.com.abc;

import org.junit.Test;

import main.java.com.abc.Transaction;

import static org.junit.Assert.assertTrue;

import javax.swing.JOptionPane;

public class TransactionTest {
    @Test
    public void transaction() {
        Transaction t = new Transaction(5);
        assertTrue(t instanceof Transaction);
    }
}
