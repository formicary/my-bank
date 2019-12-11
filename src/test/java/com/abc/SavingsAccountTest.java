package com.abc;

import static org.junit.Assert.*;

import org.junit.Test;

public class SavingsAccountTest {
	private static final double DOUBLE_DELTA = 1e-15;
	
	@Test
	public void test() {
		Account a = new AccountFactory(AccountFactory.SAVINGS).getAccount();

		a.deposit(1000);
		a.deposit(3000);
		a.withdraw(2000);
		// 1000 * 0.001 + 1000 * 0.002 = 3;
		assertEquals(3, a.interestEarned(), DOUBLE_DELTA);
	}
	
	@Test
	public void test2() {
		Account a = new AccountFactory(AccountFactory.SAVINGS).getAccount();

		a.deposit(4000);
		a.withdraw(3800);
		// 200 *0.001
		assertEquals(0.2, a.interestEarned(), DOUBLE_DELTA);
	}
	
	@Test
	public void test3() {
		Account a = new AccountFactory(AccountFactory.SAVINGS).getAccount();
	
		a.deposit(4000);
		a.withdraw(4000);
		assertEquals(0, a.interestEarned(), DOUBLE_DELTA);
	}

}
