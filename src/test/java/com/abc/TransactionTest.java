package com.abc;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class TransactionTest {
	@Test
	public void transaction() {
		Transaction t = new Transaction(5, LocalDate.now());
		assertTrue(t instanceof Transaction);
	}
}
