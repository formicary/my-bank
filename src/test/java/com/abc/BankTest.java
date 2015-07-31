package com.abc;

import main.java.com.abc.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class BankTest {
	private static final double DOUBLE_DELTA = 1e-15;

	@Test
	public void customerSummaryTwoAccountsTest() {
		Bank bank = new Bank();
		Customer john = new Customer("John");
		john.openAccount(new CheckingAccount());
		john.openAccount(new MaxiSavingsAccount());
		bank.addCustomer(john);

		assertEquals("Customer Summary\n - John (2 accounts)",
				bank.customerSummary());
	}

	@Test
	public void customerSummaryOneAccountsTest() {
		Bank bank = new Bank();
		Customer john = new Customer("John");
		john.openAccount(new CheckingAccount());
		bank.addCustomer(john);

		assertEquals("Customer Summary\n - John (1 account)",
				bank.customerSummary());
	}

	@Test
	public void checkingAccount() {
		Bank bank = new Bank();
		Account checkingAccount = new CheckingAccount();
		Customer bill = new Customer("Bill").openAccount(checkingAccount);
		bank.addCustomer(bill);

		checkingAccount.deposit(100.0);

		assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
	}

	@Test
	public void savings_account() {
		Bank bank = new Bank();
		Account checkingAccount = new SavingsAccount();
		bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

		checkingAccount.deposit(1500.0);

		assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
	}

	@Test
	public void maxi_savings_account() {
		Bank bank = new Bank();
		Account checkingAccount = new MaxiSavingsAccount();
		bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

		checkingAccount.deposit(3000.0);

		assertEquals(150.0, bank.totalInterestPaid(), DOUBLE_DELTA);
	}

	@Test
	public void checking_maxi_savings_account() {
		Bank bank = new Bank();
		Account checkingAccount = new CheckingAccount();
		Account maxiSavingAccount = new MaxiSavingsAccount();
		bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));
		bank.addCustomer(new Customer("Bill").openAccount(maxiSavingAccount));

		checkingAccount.deposit(3000.0);
		maxiSavingAccount.deposit(1000.0);

		// 3000.0*0.001 + 1000.0*0.05 = 53.0
		assertEquals(53.0, bank.totalInterestPaid(), DOUBLE_DELTA);
	}

	@Test
	public void checking_savings_account() {
		Bank bank = new Bank();
		Account checkingAccount = new CheckingAccount();
		Account savingAccount = new SavingsAccount();
		bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));
		bank.addCustomer(new Customer("Bill").openAccount(savingAccount));

		checkingAccount.deposit(3000.0);
		savingAccount.deposit(1500.0);

		assertEquals(5.0, bank.totalInterestPaid(), DOUBLE_DELTA);
	}

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void transferBetweenTwoAccountsTest() {
		Bank bank = new Bank();
		Account checkingAccount = new CheckingAccount();
		Account savingAccount = new SavingsAccount();
		bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));
		bank.addCustomer(new Customer("Bill").openAccount(savingAccount));

		checkingAccount.deposit(3000.0);
		savingAccount.deposit(1500.0);

		bank.transferBetwwenAccounts(checkingAccount, savingAccount, 1000.25);
		assertEquals(1999.75, checkingAccount.sumTransactions(), DOUBLE_DELTA);
		assertEquals(2500.25, savingAccount.sumTransactions(), DOUBLE_DELTA);

	}

	@Test
	public void transferBetweenTwoAccounts_bothSameAccounts_ExceptionTest() {
		Bank bank = new Bank();
		Account checkingAccount = new CheckingAccount();
		Account savingAccount = new SavingsAccount();
		bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));
		bank.addCustomer(new Customer("Bill").openAccount(savingAccount));

		checkingAccount.deposit(3000.0);
		savingAccount.deposit(1500.0);

		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("both accounts are the same");
		bank.transferBetwwenAccounts(checkingAccount, checkingAccount, 1000.25);

	}

	@Test
	public void transferBetweenTwoAccounts_InsufficientFund_ExceptionTest() {
		Bank bank = new Bank();
		Account checkingAccount = new CheckingAccount();
		Account savingAccount = new SavingsAccount();
		bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));
		bank.addCustomer(new Customer("Bill").openAccount(savingAccount));

		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("not sufficient fund into the account");
		bank.transferBetwwenAccounts(checkingAccount, savingAccount, 4000.00);

	}

}
