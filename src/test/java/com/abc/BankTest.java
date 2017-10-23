package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertSame;

import java.math.BigDecimal;

/**
 * Test cases for the bank class.
 * 
 * @author Christopher J. Smith
 */
public class BankTest {

	@Test
	/**
	 * Testing creating customer summary with multiple customers
	 */
	public void customerSummary() {
		Bank bank = new Bank();
		Customer john = new Customer("John");
		john.openAccount(new Account(Account.AccountType.CHECKING));
		john.openAccount(null);
		Customer bob = new Customer("Bob");
		bob.openAccount(new Account(Account.AccountType.CHECKING));
		bob.openAccount(new Account(Account.AccountType.SAVINGS));
		bob.openAccount(new Account(Account.AccountType.MAXI_SAVINGS));

		bank.addCustomer(john).addCustomer(bob);

		String expected = "Customer Summary\n" +
							" - John (1 account)\n" + 
							" - Bob (3 accounts)";

		assertEquals(expected, bank.getCutomersSummary());
	}

	@Test
	/**
	 * Testing a single checking account annual interest
	 */
	public void checkingAccountAnnual() {
		Bank bank = new Bank();
		Account checkingAccount = new Account(Account.AccountType.CHECKING);
		Customer bill = new Customer("Bill").openAccount(checkingAccount);
		bank.addCustomer(bill);

		checkingAccount.deposit(100);

		assertTrue(bank.getTotalInterestPayableAnually().compareTo(new BigDecimal("0.1")) == 0);
	}

	@Test
	/**
	 * Testing a single checking account daily interest
	 */
	public void checkingAccountDaily() {
		Bank bank = new Bank();
		Account checkingAccount = new Account(Account.AccountType.CHECKING);
		Customer bill = new Customer("Bill").openAccount(checkingAccount);
		bank.addCustomer(bill);

		checkingAccount.deposit(100);

		assertTrue(bank.getTotalInterestPayableDaily().compareTo(new BigDecimal("0.00027")) == 0);
	}

	@Test
	/**
	 * Testing multiple checking accounts annual interest
	 */
	public void checkingAccountsAnnual() {
		Bank bank = new Bank();
		Account checkingAccount = new Account(Account.AccountType.CHECKING);
		Customer bill = new Customer("Bill").openAccount(checkingAccount);
		checkingAccount.deposit(1000000);
		checkingAccount = new Account(Account.AccountType.CHECKING);
		Customer bob = new Customer("Bob").openAccount(checkingAccount);
		checkingAccount.deposit(10);
		checkingAccount = new Account(Account.AccountType.CHECKING);
		Customer jill = new Customer("Jill").openAccount(checkingAccount);
		checkingAccount.deposit(500);
		checkingAccount = new Account(Account.AccountType.CHECKING);
		jill.openAccount(checkingAccount);
		checkingAccount.deposit(475);

		bank.addCustomer(bill).addCustomer(bob).addCustomer(jill);

		assertTrue(bank.getTotalInterestPayableAnually().compareTo(new BigDecimal("1000.985")) == 0);
	}

	@Test
	/**
	 * Testing multiple checking accounts daily interest
	 */
	public void checkingAccountsDaily() {
		Bank bank = new Bank();
		Account checkingAccount = new Account(Account.AccountType.CHECKING);
		Customer bill = new Customer("Bill").openAccount(checkingAccount);
		checkingAccount.deposit(1000000);
		checkingAccount = new Account(Account.AccountType.CHECKING);
		Customer bob = new Customer("Bob").openAccount(checkingAccount);
		checkingAccount.deposit(10);
		checkingAccount = new Account(Account.AccountType.CHECKING);
		Customer jill = new Customer("Jill").openAccount(checkingAccount);
		checkingAccount.deposit(500);
		checkingAccount = new Account(Account.AccountType.CHECKING);
		jill.openAccount(checkingAccount);
		checkingAccount.deposit(475);

		bank.addCustomer(bill).addCustomer(bob).addCustomer(jill);

		assertTrue(bank.getTotalInterestPayableDaily().compareTo(new BigDecimal("2.7026595")) == 0);
	}

	@Test
	/**
	 * Testing a single savings account annual interest in the max threshold
	 */
	public void savingsAccountAnnual() {
		Bank bank = new Bank();
		Account savingsAccount = new Account(Account.AccountType.SAVINGS);
		Customer bill = new Customer("Bill").openAccount(savingsAccount);
		savingsAccount.deposit(1500);

		bank.addCustomer(bill);

		assertTrue(bank.getTotalInterestPayableAnually().compareTo(new BigDecimal("2")) == 0);
	}

	@Test
	/**
	 * Testing a single savings account daily interest in the max threshold
	 */
	public void savingsAccountDaily() {
		Bank bank = new Bank();
		Account savingsAccount = new Account(Account.AccountType.SAVINGS);
		Customer bill = new Customer("Bill").openAccount(savingsAccount);
		savingsAccount.deposit(1500);

		bank.addCustomer(bill);

		assertTrue(bank.getTotalInterestPayableDaily().compareTo(new BigDecimal("0.0054")) == 0);
	}

	@Test
	/**
	 * Testing multiple savings accounts annual interest in different thresholds
	 */
	public void savingsAccountsAnnual() {
		Bank bank = new Bank();
		Account savingsAccount = new Account(Account.AccountType.SAVINGS);
		Customer bill = new Customer("Bill").openAccount(savingsAccount);
		savingsAccount.deposit(1500);
		savingsAccount = new Account(Account.AccountType.SAVINGS);
		Customer jill = new Customer("Jill").openAccount(savingsAccount);
		savingsAccount.deposit(8285);
		savingsAccount = new Account(Account.AccountType.SAVINGS);
		Customer bob = new Customer("Bob").openAccount(savingsAccount);
		savingsAccount.deposit(800);
		savingsAccount = new Account(Account.AccountType.SAVINGS);
		bob.openAccount(savingsAccount);
		savingsAccount.deposit(2500);

		bank.addCustomer(bill).addCustomer(bob).addCustomer(jill);

		assertTrue(bank.getTotalInterestPayableAnually().compareTo(new BigDecimal("22.37")) == 0);
	}

	@Test
	/**
	 * Testing multiple savings accounts daily interest in different thresholds
	 */
	public void savingsAccountsDaily() {
		Bank bank = new Bank();
		Account savingsAccount = new Account(Account.AccountType.SAVINGS);
		Customer bill = new Customer("Bill").openAccount(savingsAccount);
		savingsAccount.deposit(1500);
		savingsAccount = new Account(Account.AccountType.SAVINGS);
		Customer jill = new Customer("Jill").openAccount(savingsAccount);
		savingsAccount.deposit(8285);
		savingsAccount = new Account(Account.AccountType.SAVINGS);
		Customer bob = new Customer("Bob").openAccount(savingsAccount);
		savingsAccount.deposit(800);
		savingsAccount = new Account(Account.AccountType.SAVINGS);
		bob.openAccount(savingsAccount);
		savingsAccount.deposit(2500);

		bank.addCustomer(bill).addCustomer(bob).addCustomer(jill);

		assertTrue(bank.getTotalInterestPayableDaily().compareTo(new BigDecimal("0.060399")) == 0);
	}

	@Test
	/**
	 * Testing a single maxi account annual interest in the max threshold
	 */
	public void maxiSavingsAccountAnnual() {
		Bank bank = new Bank();
		Account checkingAccount = new Account(Account.AccountType.MAXI_SAVINGS);
		bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

		checkingAccount.deposit(3000);

		assertTrue(bank.getTotalInterestPayableAnually().compareTo(new BigDecimal("170")) == 0);
	}

	@Test
	/**
	 * Testing a single maxi account daily interest in the max threshold
	 */
	public void maxiSavingsAccountDaily() {
		Bank bank = new Bank();
		Account checkingAccount = new Account(Account.AccountType.MAXI_SAVINGS);
		bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

		checkingAccount.deposit(3000);

		assertTrue(bank.getTotalInterestPayableDaily().compareTo(new BigDecimal("0.459")) == 0);
	}

	@Test
	/**
	 * Testing multiple maxi accounts annual interest in different thresholds
	 */
	public void maxiSavingsAccountsAnnual() {
		Bank bank = new Bank();
		Account savingsAccount = new Account(Account.AccountType.MAXI_SAVINGS);
		Customer bill = new Customer("Bill").openAccount(savingsAccount);
		savingsAccount.deposit(3000);
		savingsAccount = new Account(Account.AccountType.MAXI_SAVINGS);
		Customer jill = new Customer("Jill").openAccount(savingsAccount);
		savingsAccount.deposit(5978246);
		savingsAccount = new Account(Account.AccountType.MAXI_SAVINGS);
		Customer bob = new Customer("Bob").openAccount(savingsAccount);
		savingsAccount.deposit(65.45);
		savingsAccount = new Account(Account.AccountType.MAXI_SAVINGS);
		bob.openAccount(savingsAccount);
		savingsAccount.deposit(1888);

		bank.addCustomer(bill).addCustomer(bob).addCustomer(jill);

		assertTrue(bank.getTotalInterestPayableAnually().compareTo(new BigDecimal("597930.309")) == 0);
	}

	@Test
	/**
	 * Testing multiple maxi accounts daily interest in different thresholds
	 */
	public void maxiSavingsAccountsDaily() {
		Bank bank = new Bank();
		Account savingsAccount = new Account(Account.AccountType.MAXI_SAVINGS);
		Customer bill = new Customer("Bill").openAccount(savingsAccount);
		savingsAccount.deposit(3000);
		savingsAccount = new Account(Account.AccountType.MAXI_SAVINGS);
		Customer jill = new Customer("Jill").openAccount(savingsAccount);
		savingsAccount.deposit(5978246);
		savingsAccount = new Account(Account.AccountType.MAXI_SAVINGS);
		Customer bob = new Customer("Bob").openAccount(savingsAccount);
		savingsAccount.deposit(65.45);
		savingsAccount = new Account(Account.AccountType.MAXI_SAVINGS);
		bob.openAccount(savingsAccount);
		savingsAccount.deposit(1888);

		bank.addCustomer(bill).addCustomer(bob).addCustomer(jill);

		assertTrue(bank.getTotalInterestPayableDaily().compareTo(new BigDecimal("1614.4118343")) == 0);
	}

	@Test
	/**
	 * Testing correct customer is returned as first customer
	 */
	public void fistCustomer() {
		Bank bank = new Bank();
		Customer bill = new Customer("Bill");
		Customer bob = new Customer("Bob");
		Customer jill = new Customer("Jill");

		bank.addCustomer(bill).addCustomer(bob).addCustomer(jill);

		assertSame(bill, bank.getFirstCustomer());
	}

	@Test
	/**
	 * Testing null is returned if there are no customers when requesting the first
	 * customer
	 */
	public void fistCustomerNull() {
		Bank bank = new Bank();

		assertSame(null, bank.getFirstCustomer());
	}

	@Test
	/**
	 * Test adding annual interest to all customer accounts.
	 */
	public void addInterest() {
		Bank bank = new Bank();
		Customer bill = new Customer("Bill");
		Customer bob = new Customer("Bob");

		Account ac = new Account(Account.AccountType.CHECKING);
		ac.deposit(100);
		bill.openAccount(ac);
		Account as = new Account(Account.AccountType.SAVINGS);
		as.deposit(5000);
		bob.openAccount(as);

		bank.addCustomer(bob).addCustomer(bill);
		bank.applyInterest();

		assertTrue(bill.getTotalAccountHoldings().compareTo(new BigDecimal("100.1")) == 0
				&& bob.getTotalAccountHoldings().compareTo(new BigDecimal("5009")) == 0);
	}

	@Test
	/**
	 * Test adding daily interest to all customer accounts.
	 */
	public void addInterestDaily() {
		Bank bank = new Bank();
		Customer bill = new Customer("Bill");
		Customer bob = new Customer("Bob");

		Account ac = new Account(Account.AccountType.CHECKING);
		ac.deposit(100);
		bill.openAccount(ac);
		Account as = new Account(Account.AccountType.SAVINGS);
		as.deposit(5000);
		bob.openAccount(as);

		bank.addCustomer(bob).addCustomer(bill);
		bank.applyDailyInterest();

		assertTrue(bill.getTotalAccountHoldings().compareTo(new BigDecimal("100.0002")) == 0
				&& bob.getTotalAccountHoldings().compareTo(new BigDecimal("5000.0243")) == 0);
	}

	@Test
	/**
	 * Test summing all customer holdings.
	 */
	public void totalHoldings() {
		Bank bank = new Bank();
		Customer bill = new Customer("Bill");
		Customer bob = new Customer("Bob");

		Account ac = new Account(Account.AccountType.CHECKING);
		ac.deposit(100);
		bill.openAccount(ac);
		Account as = new Account(Account.AccountType.SAVINGS);
		as.deposit(5000);
		bob.openAccount(as);

		bank.addCustomer(bob).addCustomer(bill);

		assertTrue(bank.getTotalHoldings().compareTo(new BigDecimal("5100")) == 0);
	}

	@Test
	/**
	 * Test toString method.
	 */
	public void stringCreation() {
		Bank bank = new Bank();
		Customer bill = new Customer("Bill");
		Customer bob = new Customer("Bob");

		Account ac = new Account(Account.AccountType.CHECKING);
		ac.deposit(100);
		bill.openAccount(ac);
		bank.addCustomer(bob).addCustomer(bill);

		String expected = "Customers: 2  Total Holdings: $100.00";
		assertEquals(expected, bank.toString());
	}
}
