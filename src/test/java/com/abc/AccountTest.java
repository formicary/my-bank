package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountTest {
	
    private static final double DOUBLE_DELTA = 1e-15;
	
	@Test
	public void deposit_PositiveAmountGiven_ShouldReturnIncreasedBalance() {
		Account account = new Account(1);
		account.deposit(5);
		
		assertEquals(5.0, account.getBalance(),DOUBLE_DELTA);
	}

	@Test(expected = IllegalArgumentException.class) 
	public void deposit_NegativeAmountGiven_ShouldThrowIllegalArgumentException() {
		Account account = new Account(1);
		account.deposit(-5);
	}
	
	@Test
	public void withdraw_PositiveAmountGiven_ShouldReturnReducedBalance() {
		Account account = new Account(1);
		account.deposit(5);
		account.withdraw(3);
		
		assertEquals(2.0, account.getBalance(),DOUBLE_DELTA);
	}

	@Test(expected = IllegalArgumentException.class) 
	public void withdraw_NegativeAmountGiven_ShouldThrowIllegalArgumentException() {
		Account account = new Account(1);
		account.deposit(5);
		account.withdraw(-2);
	}


}
