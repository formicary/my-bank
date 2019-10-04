package main;

import org.junit.Test;

import helper.AccountTypes;
import main.Account;
import main.Bank;
import main.BankManager;
import main.Customer;

import static org.junit.Assert.assertEquals;

public class BankTest {
	private static final double DOUBLE_DELTA = 1e-15;

	@Test
	public void customerSummary() {
		Bank bank = new Bank();
		Customer john = new Customer(1, "John");
		john.openAccount(new Account(AccountTypes.CHECKING));
		bank.addCustomer(john);
		BankManager bankManager = new BankManager(bank);

		assertEquals("Customer Summary\n - John (1 account)", bankManager.customerSummary());
	}

	@Test
	public void checkingAccount() {
		Bank bank = new Bank();
		Account checkingAccount = new Account(AccountTypes.CHECKING);
		Customer bill = new Customer(2, "Bill").openAccount(checkingAccount);
		bank.addCustomer(bill);
		BankManager bankManager = new BankManager(bank);

		checkingAccount.deposit(100.0);

		assertEquals(0.1, bankManager.totalInterestPaid(), DOUBLE_DELTA);
	}

	@Test
	public void savings_account() {
		Bank bank = new Bank();
		Account checkingAccount = new Account(AccountTypes.SAVINGS);
		bank.addCustomer(new Customer(2, "Bill").openAccount(checkingAccount));
		BankManager bankManager = new BankManager(bank);

		checkingAccount.deposit(1500.0);

		assertEquals(2.0, bankManager.totalInterestPaid(), DOUBLE_DELTA);
	}

	@Test
	public void maxi_savings_account() {
		Bank bank = new Bank();
		Account checkingAccount = new Account(AccountTypes.MAXI_SAVINGS);
		bank.addCustomer(new Customer(2, "Bill").openAccount(checkingAccount));
		BankManager bankManager = new BankManager(bank);

		checkingAccount.deposit(3000.0);

		assertEquals(170.0, bankManager.totalInterestPaid(), DOUBLE_DELTA);
	}

}
