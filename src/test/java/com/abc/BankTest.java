package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {
	private static final double DOUBLE_DELTA = 1e-15;
	Bank bank = new Bank();
	
	@Test
	public void customerSummary() {
		Customer john = new Customer("John");
		john.openAccount(new Account(Account.CHECKING));
		bank.addCustomer(john);

		assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
	}

	@Test
	public void checkingAccount() {
		Account checkingAccount = new Account(Account.CHECKING);
		Customer bill = new Customer("Bill").openAccount(checkingAccount);
		bank.addCustomer(bill);

		checkingAccount.deposit(100.0);

		assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
	}

	@Test
	public void savingsAccount() {
		Account savingsAccount = new Account(Account.SAVINGS);
		bank.addCustomer(new Customer("Bill").openAccount(savingsAccount));

		savingsAccount.deposit(1500.0);

		assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
	}

	@Test
	public void maxiSavingsAccountInterest() {
		Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);
		bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));

		maxiSavingsAccount.deposit(100.0);

		assertEquals(5.0, bank.totalInterestPaid(), DOUBLE_DELTA);
	}

	@Test
	public void maxiSavingsAccountWithdrawInterest() {
		Account maxiSavingsAccount = new Account(Account.MAXI_SAVINGS);
		bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));

		maxiSavingsAccount.deposit(1000.0);
		maxiSavingsAccount.withdraw(5);
		maxiSavingsAccount.deposit(5);
		
		assertEquals(1, bank.totalInterestPaid(), DOUBLE_DELTA);
	}

}
