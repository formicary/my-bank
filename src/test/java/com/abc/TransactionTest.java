package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TransactionTest {
	private static final double DOUBLE_DELTA = 1e-15;

	@Test
	//Test if transactions are correctly created.
	public void transaction() {
		Transaction t = new Transaction(5);
		assertTrue(t instanceof Transaction);
	}

	@Test
	//Test if transactions amounts are correctly recorded.
	public void transactionGetAmount() {
		Transaction t = new Transaction(5.0);
		assertEquals(5.0, t.getAmount(), DOUBLE_DELTA);
	}
}
