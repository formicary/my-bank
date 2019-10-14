package com.abc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionTest {

	private static Transaction transaction;
	private static BigDecimal amount;

	@BeforeClass
	public static void init() {
		// Instantiate a new transaction of $1000
		amount = new BigDecimal(1000);
		transaction = new Transaction(amount);
	}

	@Test
	public void amount() {
		assertTrue(transaction.getAmount() == amount);
	}

	@Test
	public void date() {
		assertEquals(transaction.getTransactionDate(), LocalDate.now());
	}

	@AfterClass
	public static void finalise() {
		transaction = null;
	}

}
