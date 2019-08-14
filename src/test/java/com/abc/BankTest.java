package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {
	private static final double DOUBLE_DELTA = 1e-15;

	@Test
	public void customerSummaryTest() {
		Bank bank = new Bank();
		Customer john = new Customer("John");
		john.openAccount(new CheckingAccount());
		bank.addCustomer(john);

		assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
	}
	
	@Test
	public void getFirstCustomerTest() {
		Bank bank = new Bank();
		Customer john = new Customer("John");
		Customer adam = new Customer("Adam");

		bank.addCustomer(john);
		bank.addCustomer(adam);

		assertEquals("John", bank.getFirstCustomer());
	}
	

	@Test
	public void getLastWithdrawTransactionTest() {
		Account checkingAccount = new CheckingAccount();
		
		checkingAccount.deposit(1000);
		checkingAccount.withdraw(100);
		checkingAccount.deposit(3000);
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		checkingAccount.withdraw(700);
		checkingAccount.deposit(1500);

		assertEquals(-700, checkingAccount.getLastWithdrawTransaction().amount, DOUBLE_DELTA);
	}	

	@Test
	public void getOwnerTest() {
		Account checkingAccount = new CheckingAccount();
		assertEquals(checkingAccount.getOwner(), null);

		Customer john = new Customer("John");
		john.openAccount(checkingAccount);

		assertEquals(checkingAccount.getOwner().getName(), john.getName());
	}

	@Test
	public void withdrawTest() {
		Bank bank = new Bank();
		Account checkingAccount = new CheckingAccount();
		Customer bill = new Customer("Bill").openAccount(checkingAccount);
		bank.addCustomer(bill);

		checkingAccount.deposit(100.0);
		checkingAccount.withdraw(50.0);

		assertEquals(50.0, checkingAccount.sumTransactions(), DOUBLE_DELTA);
	}

	@Test
	public void checkingAccountInterestTest() {
		Bank bank = new Bank();
		Account checkingAccount = new CheckingAccount();
		Customer bill = new Customer("Bill").openAccount(checkingAccount);
		bank.addCustomer(bill);

		checkingAccount.deposit(100.0);

		assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
	}

	@Test
	public void savingsAccountInterestTest() {
		Bank bank = new Bank();
		Account checkingAccount = new SavingsAccount();
		bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

		checkingAccount.deposit(500);
		assertEquals(0.5, bank.totalInterestPaid(), DOUBLE_DELTA);

		checkingAccount.deposit(1500.0);
		assertEquals(3.0, bank.totalInterestPaid(), DOUBLE_DELTA);
	}

	@Test
	public void maxiSavingsAccountInterestTest() {
		Bank bank = new Bank();
		Account checkingAccount = new MaxiSavingsAccount();
		bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

		checkingAccount.deposit(3000.0);
		checkingAccount.deposit(2000.0);

		assertEquals(5, bank.totalInterestPaid(), DOUBLE_DELTA);
	}

}
