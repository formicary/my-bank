package com.abc.model;


import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;


public class AccountTest {
	
	
	@Test
	public void shouldHasEmptyTransactionListAfterCreate(){
		Account accout = AccountBuilder.createChecking().get();
		assertNotNull(accout.getTransactionList());
	}
	
	
	@Test
	public void shouldClalculateBalance(){
		Account accout = AccountBuilder
							.createChecking()
							.withDeposit("100")
							.withInterest("10")
							.withWithdrawal("30")
							.get();
		
		assertEquals("$80.00", accout.getBalance().getFormatted());
	}
	
	
	@Test
	public void shouldCalculateIntrest(){
		Account accout = AccountBuilder
							.createChecking()
							.withDeposit("100")
							.withInterest("10")
							.withWithdrawal("30")
							.get();
		
		assertEquals("$10.00", accout.getIntrestPaid().getFormatted());
	}
	
	
	
	@Test
	public void shouldAddTransaction(){
		Account accout = AccountBuilder.createChecking().get();
		
		assertTrue(accout.getTransactionList().isEmpty());
		
		Transaction transaction = new Transaction(TransactionType.DEPOSIT, new BigDecimal("10"));
		accout.addTransaction(transaction);
		
		assertFalse(accout.getTransactionList().isEmpty());
	}
	
	
	
	@Test(expected = NullPointerException.class)
	public void shouldFailIfTransactionIsNull(){
		Account accout = AccountBuilder.createChecking().get();
	
		accout.addTransaction( null );
	}
}
