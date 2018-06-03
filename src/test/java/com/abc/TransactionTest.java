package com.abc;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertTrue;

/**
 * This class is for testing if a transaction can be created successfully.
 * @author Peng Shao. Modifed based on the exercise provided by Accenture.
 * @version  03/05/2018
 */
public class TransactionTest {
    @Test
    /**
     * This is to test is a transaction can be created successfully.
     */
    public void transaction() {
        Transaction t = new Transaction(new BigDecimal(5));
        assertTrue(t instanceof Transaction);
    }
}
