package com.abc;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import java.util.Calendar;

/**
 * Tester for the methods of the Transaction class.
 * @author Filippos Zofakis
 */
public class TransactionTest {
    @Test // Test transation creation.
    public void testTransactionCreation() {
        Transaction t = new Transaction(5.5);

        // Checking that transaction created is indeed of type Transaction.
        assertTrue(t instanceof Transaction);
        System.out.println("Transaction of correct type created.");
    }
    
    @Test // Test transation amount.
    public void testTransactionAmount() {
        Transaction t = new Transaction(5.5);

        // Checking that the amount recorded is correct.
        assertTrue(5.5 == t.getAmount());
        System.out.println("Transaction amount correct.");
    }
    
    @Test // Test transation amount editing.
    public void testTransactionEdit() {
        Transaction t = new Transaction(5.5);

        // Checking that the transaction edit is recorded correctly.
        t.editAmount(5.6);
        assertTrue(5.6 == t.getAmount());
        System.out.println("Transaction amount edited correctly.");
    }

    @Test // Test transation date recording.
    public void testTransactionDate() {
        Transaction t = new Transaction(5.5);

        // Checking that the recorded date is correct (within 5 milliseconds).
        assertTrue(Calendar.getInstance().compareTo(t.getDate()) <= 5);
        System.out.println("Transaction date recorded correctly.");
    }
}
