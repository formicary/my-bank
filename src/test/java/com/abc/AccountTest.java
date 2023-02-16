package com.abc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.abc.Account.AccountType;

public class AccountTest {

	private final String TEST_CUSTOMER_NAME = "Josh";
	private final double DELTA = 1e-15;

	@Test
	public void testDeposit() {
		Account account = AccountCreation.create(new Customer(TEST_CUSTOMER_NAME), AccountType.SAVINGS);
		account.deposit(150.0);
		account.deposit(350.0);
		account.deposit(100.0);
		assertEquals(600.0, account.getBalance(), DELTA);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDepositWithZeroAmount() {
		Account account = AccountCreation.create(new Customer(TEST_CUSTOMER_NAME), AccountType.SAVINGS);
		account.deposit(0.0);
	}

	@Test
	public void testWithdraw() {
		Account account = AccountCreation.create(new Customer(TEST_CUSTOMER_NAME), AccountType.SAVINGS);
		account.deposit(600.0);
		account.withdraw(100.0);
		assertEquals(500.0, account.getBalance(), DELTA);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWithdrawWithZeroAmount() {
		Account account = AccountCreation.create(new Customer(TEST_CUSTOMER_NAME), AccountType.SAVINGS);
		account.deposit(100.0);
		account.withdraw(0.0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWithdrawIfAmountGreaterThanBalance() {
		Account account = AccountCreation.create(new Customer(TEST_CUSTOMER_NAME), AccountType.SAVINGS);
		account.deposit(100.0);
		account.withdraw(2000.0);
	}

	@Test
	public void testSumTransactions() {
		Account account = AccountCreation.create(new Customer(TEST_CUSTOMER_NAME), AccountType.SAVINGS);
		account.deposit(100.0);
		account.withdraw(20.0);
		account.deposit(200.0);
		account.withdraw(100.0);
		assertEquals(180.0, account.sumTransactions(), DELTA);
	}

}
