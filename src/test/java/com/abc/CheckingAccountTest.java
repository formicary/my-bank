package com.abc;

import static org.junit.Assert.*;

import org.junit.Test;

public class CheckingAccountTest {
	private static final double DOUBLE_DELTA = 1e-15;
	
	@Test
	public void test() {
		Account a = new AccountFactory(AccountFactory.CHECKING).getAccount();
		long today = (new DateProvider()).now().getTime();
		a.deposit(100);
		a.deposit(300);
		a.withdraw(200);
		assertEquals(0.2, a.interestEarned(), DOUBLE_DELTA);
	}

}
