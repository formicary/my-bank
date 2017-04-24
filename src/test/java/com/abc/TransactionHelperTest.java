package com.abc;

import static org.junit.Assert.*;

import org.joda.time.LocalDate;
import org.junit.Test;

import com.abc.implementation.Transaction;
import com.abc.implementation.TransactionHelper;
import com.abc.interfaces.ITransaction;
import com.abc.interfaces.ITransactionHelper;

public class TransactionHelperTest {
	private static final double DOUBLE_DELTA = 1e-15;
	
	@Test
	public void getDaysDifference()
	{
		LocalDate date1 = new LocalDate(2017, 1, 1);
		LocalDate date2 = new LocalDate(2017, 1, 5);
		
		ITransaction transaction1 = new Transaction(50, date1);
		ITransaction transaction2 = new Transaction(50, date2);
		
		ITransactionHelper helper = new TransactionHelper();
		assertEquals(4, helper.getDaysDifference(transaction1, transaction2), DOUBLE_DELTA) ;
	}
	
	@Test
	public void isWithdrawnWithinPeriodTrue()
	{
		LocalDate date1 = new LocalDate(2017, 1, 1);
		LocalDate date2 = new LocalDate(2017, 1, 5);
		
		ITransaction transaction1 = new Transaction(50, date1);
		ITransaction transaction2 = new Transaction(-50, date2);
		
		transaction1.setNextTransaction(transaction2);
		
		ITransactionHelper helper = new TransactionHelper();
		
		assertTrue(helper.isWithdrawnWithin(transaction1, 10));
	}
	
	@Test
	public void isWithdrawnWithinPeriodFalse()
	{
		LocalDate date1 = new LocalDate(2017, 1, 1);
		LocalDate date2 = new LocalDate(2017, 1, 15);
		
		ITransaction transaction1 = new Transaction(50, date1);
		ITransaction transaction2 = new Transaction(-50, date2);
		
		transaction1.setNextTransaction(transaction2);
		
		ITransactionHelper helper = new TransactionHelper();
		
		assertFalse(helper.isWithdrawnWithin(transaction1, 10));
	}
}
