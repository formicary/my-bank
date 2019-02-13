package com.abc;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class AccountTest {
    private static final double DOUBLE_DELTA = 1e-15;
	private Account account;
	
    @Before
    public void initialise() {
		account = new Account(Account.AccountType.CHECKING);
    }
	
    @After
    public void tearDown() {
    	account = null;
    }
    
	@Test
	public void createAccountTest() {
		assertTrue(account instanceof Account);
	}
	
	@Test
	public void depositTest() {
		account.deposit(100);
		assertEquals(100, account.getTransactions().get(0).getAmount(), DOUBLE_DELTA);
	}
	
	@Test(expected = IllegalArgumentException.class)
    public void amountThrowsIllegalArgumentExceptionForDepositNegativeAmount() {
		account.deposit(-100);
    }

	@Test(expected = IllegalArgumentException.class)
    public void amountThrowsIllegalArgumentExceptionForDepositZeroAmount() {
		account.deposit(0);
    }
	
	@Test(expected = IllegalArgumentException.class)
    public void amountThrowsIllegalArgumentExceptionForWidthdrawNegativeAmount() {
		account.withdraw(-1000);
    }
	
	@Test(expected = IllegalArgumentException.class)
    public void amountThrowsIllegalArgumentExceptionForWidthdrawZeroAmount() {
		account.withdraw(0);
    }
}
