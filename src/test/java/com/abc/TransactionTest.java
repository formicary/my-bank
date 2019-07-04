package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * TransactionTest class tests transactions
 * 
 * @version 2.0 03 July 2019
 * @updated by Dhurjati Dasgupta
 */

public class TransactionTest {
	@Test
	public void transaction() {
		Transaction t = new Transaction(5);
		assertTrue(t instanceof Transaction);
	}
}
