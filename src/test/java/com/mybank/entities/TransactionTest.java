package com.mybank.entities;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.mybank.entities.Transaction;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Before;

public class TransactionTest {

	private Transaction testTransaction;

	@Mock
	Transaction mockTransaction;

	@Before
	public void setUp() {

		testTransaction = new Transaction(20);
		mockTransaction = Mockito.mock(Transaction.class);

		GregorianCalendar nextTransactionDateTime = new GregorianCalendar();
		nextTransactionDateTime.add(Calendar.DAY_OF_MONTH, 5);
		Mockito.when(mockTransaction.getTransactionDateTime()).thenReturn(nextTransactionDateTime);

	}

	@Test
	public void getDaysToNextTransaction_ShouldReturnZero_GivenTransactionsMadeOnTheSameDay() {

		long days = testTransaction.getDaysToNextTransaction(testTransaction.getTransactionDateTime());
		assertEquals(0, days);
	}

	@Test
	public void getDaysToNextTransaction_ShouldReturnNumberOfDays_GivenTransactionsMadeOnDifferentDates() {

		long days = testTransaction.getDaysToNextTransaction(mockTransaction.getTransactionDateTime());
		assertEquals(5, days);
	}

	@Test
	public void isMadeToday_ShouldReturnTrue_GivenTransactionsIsMadeToday() {

		assertEquals(true, testTransaction.isMadeToday());
	}

	@Test
	public void isMadeToday_ShouldReturnFalse_GivenTransactionsIsNotMadeTodat() {

		assertEquals(false, mockTransaction.isMadeToday());
	}

}
