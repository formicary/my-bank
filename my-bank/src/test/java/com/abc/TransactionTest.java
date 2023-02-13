package com.abc;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TransactionTest {

	@Test
	public void testTransaction() {
		Transaction transaction = new Transaction(Transaction.TransactionType.DEPOSIT, 100.0);
		assertEquals(100.0, transaction.getAmount(), 0.001);
		assertEquals(Transaction.TransactionType.DEPOSIT, transaction.transactionType);
		assertNotNull(transaction.getTransactionDate());
	}

	@Test
	public void testGetAmount_Withdraw() {
		Transaction transaction = new Transaction(Transaction.TransactionType.WITHDRAW, 100.0);
		assertEquals(-100.0, transaction.getAmount(), 0.001);
	}
}
