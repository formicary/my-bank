package com.abc;

import static org.junit.Assert.*;

import org.junit.Test;

public class MaxiSavingsAccountTest {
	private static final double DOUBLE_DELTA = 1e-15;
	
	@Test
	public void test() {
		Account a = new AccountFactory(AccountFactory.MAXI_SAVINGS).getAccount();

		a.deposit(1000);
		a.deposit(3000);
		a.withdraw(2000);
		
		// 0.001 * 1 * 2000 = 2
		assertEquals(2, a.interestEarned(), DOUBLE_DELTA);
	}

}
