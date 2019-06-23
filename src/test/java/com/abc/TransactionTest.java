package com.abc;

import org.junit.Test;
import com.abc.TestUtils;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TransactionTest {
	
    @Test(expected = IllegalArgumentException.class)
    public void blankTransaction()
    {
    	Transaction t = new Transaction(0);
    }
    @Test
    public void amountPositive() {
    	Transaction tPositive = new Transaction(49.19);
    	assertEquals(49.19, tPositive.amount, TestUtils.DELTA_MONEY);
    }
    @Test
    public void amountNegative() {
    	Transaction tNegative = new Transaction(-10.51);
    	assertEquals(-10.51, tNegative.amount, TestUtils.DELTA_MONEY);
    }
    @Test
    public void dateExists()
    {
    	Transaction t = new Transaction(10.0);
    	assertNotNull(t.getTransactionDate());
    }
}
