package com.abc;

import org.junit.Test;

import com.abc.Transaction;

import static org.junit.Assert.assertTrue;

/**
 * Transaction test 
 * 
 * @author Others and Fei
 *
 */
public class TransactionTest {
	@Test
	public void transaction() {
		Transaction t = new Transaction(500, "Deposit");
		assertTrue(t instanceof Transaction);
	}
}
