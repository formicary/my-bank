package com.abc;
import java.util.Calendar;
import java.util.Date;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
/** Transaction test package, the ability of the transaction to create and add a 
 * new item is checked.
 * @author Manolis Tomadakis
 */
public class TransactionTest {
    @Test
    public void transaction() {
        Transaction t;
        t = new Transaction(5, Calendar.getInstance().getTime());
        assertTrue(t instanceof Transaction);
    }
}
