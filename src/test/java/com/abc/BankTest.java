package com.abc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.abc.account.Account;
import com.abc.account.CheckingAccount;
import com.abc.account.MaxiSavingsAccount;
import com.abc.account.SavingsAccount;

public class BankTest {
	private static final double DOUBLE_DELTA = 1e-15;

	private DateProvider dp = new DateProvider();

	@Test
	public void customerSummary() {
		Bank bank = new Bank();
		Customer john = new Customer("John");
		john.openAccount(new CheckingAccount(dp));
		bank.addCustomer(john);

		assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
	}

	@Test
	public void checkingAccount() {
		Bank bank = new Bank();
		Account checkingAccount = new CheckingAccount(dp);
		Customer bill = new Customer("Bill").openAccount(checkingAccount);
		bank.addCustomer(bill);

		checkingAccount.deposit(100.0);

		assertEquals(0.1, bank.totalInterestPaid(), DOUBLE_DELTA);
	}

	@Test
	public void savings_account() {
		Bank bank = new Bank();
		Account savingsAccount = new SavingsAccount(dp);
		bank.addCustomer(new Customer("Bill").openAccount(savingsAccount));

		savingsAccount.deposit(1500.0);

		assertEquals(2.0, bank.totalInterestPaid(), DOUBLE_DELTA);
	}

	@Test
	public void maxiSavingsAccount() {
		Bank bank = new Bank();
		Account maxiSavingsAccount = new MaxiSavingsAccount(dp);
		bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));

		maxiSavingsAccount.deposit(3000.0);

		assertEquals(170.0, bank.totalInterestPaid(), DOUBLE_DELTA);
	}

	@Test
	public void maxiSavingsAccountMediumDepoNoWithdrawalWithinTenDays() {
		Bank bank = new Bank();
		Account maxiSavingsAccount = new MaxiSavingsAccount(dp);
		bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));

		maxiSavingsAccount.deposit(1500.0);

		assertEquals(45.0, maxiSavingsAccount.interestEarned(), DOUBLE_DELTA);
	}

	@Test
	public void maxiSavingsAccountMediumDepoHaveWithdrawalWithinTenDays() {
		Bank bank = new Bank();
		Account maxiSavingsAccount = new MaxiSavingsAccount(dp);
		bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));

		maxiSavingsAccount.deposit(1500.0);
		maxiSavingsAccount.withdraw(1.0);

		assertEquals(20.499, maxiSavingsAccount.interestEarned(), DOUBLE_DELTA);
	}

	@Test
	public void maxiSavingsAccountLargeDepoWithdrawalWithinTenDaysHaveNoEffect() {
		Bank bank = new Bank();
		Account maxiSavingsAccount = new MaxiSavingsAccount(dp);
		bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));

		maxiSavingsAccount.deposit(2500.0);
		double interestEarnedNoWithdrawal = maxiSavingsAccount.interestEarned();

		maxiSavingsAccount.withdraw(1.0);
		maxiSavingsAccount.deposit(1.0);

		double interestEarnedHaveWithdrawal = maxiSavingsAccount.interestEarned();

		assertEquals(interestEarnedNoWithdrawal, interestEarnedHaveWithdrawal, DOUBLE_DELTA);
	}

	@Test
	public void maxiSavingsAccountSmallDepoWithdrawalWithinTenDaysHaveNoEffect() {
		Bank bank = new Bank();
		Account maxiSavingsAccount = new MaxiSavingsAccount(dp);
		bank.addCustomer(new Customer("Bill").openAccount(maxiSavingsAccount));

		maxiSavingsAccount.deposit(500.0);
		double interestEarnedNoWithdrawal = maxiSavingsAccount.interestEarned();

		maxiSavingsAccount.withdraw(1.0);
		maxiSavingsAccount.deposit(1.0);

		double interestEarnedHaveWithdrawal = maxiSavingsAccount.interestEarned();

		assertEquals(interestEarnedNoWithdrawal, interestEarnedHaveWithdrawal, DOUBLE_DELTA);
	}

}
