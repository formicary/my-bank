package com.abc;

import static org.junit.Assert.assertEquals;

import java.time.Instant;

import org.junit.Test;

import com.abc.Account.Account;
import com.abc.Account.AccountType;
import com.abc.Account.CreateAccount;

public class AccountTest {

	@Test
	public void checking_account_valid_deposit_test() {
		Account acc = CreateAccount.createAccount(AccountType.CHECKING);
		acc.deposit(200.0);
		assertEquals(200.0, acc.getBalance(), -Double.MAX_VALUE);
	}

	@Test(expected = IllegalArgumentException.class)
	public void checking_account_invalid_deposit_test() {
		Account acc = CreateAccount.createAccount(AccountType.CHECKING);
		acc.deposit(-100.0);
	}

	@Test
	public void checking_account_valid_withdrawal_test() {
		Account acc = CreateAccount.createAccount(AccountType.CHECKING);
		acc.deposit(1000.0);
		acc.withdraw(500.0);
		assertEquals(500.0, acc.getBalance(), -Double.MAX_VALUE);
	}

	@Test(expected = IllegalArgumentException.class)
	public void checking_account_invalid_withdrawal_test() {
		Account acc = CreateAccount.createAccount(AccountType.CHECKING);
		acc.deposit(50.0);
		acc.withdraw(100.0);
	}

	@Test
	public void balance_test() {
		Account savingsAcc = CreateAccount.createAccount(AccountType.SAVINGS);
		savingsAcc.deposit(500.0);
		savingsAcc.deposit(300.0);
		savingsAcc.withdraw(200.0);
		assertEquals(600.0, savingsAcc.getBalance(), -Double.MAX_VALUE);
	}

	@Test
	public void checking_account_interest_test() {
		Account acc = CreateAccount.createAccount(AccountType.CHECKING);
		acc.deposit(1000.0);
		assertEquals(1.0, acc.earnedInterest(), -Double.MAX_VALUE);
	}

	@Test
	public void savings_account_interest_below_thousand_test() {
		Account acc = CreateAccount.createAccount(AccountType.SAVINGS);
		acc.deposit(1000.0);
		assertEquals(1.0, acc.earnedInterest(), -Double.MAX_VALUE);
	}

	@Test
	public void savings_account_interest_over_thousand_test() {
		Account acc = CreateAccount.createAccount(AccountType.SAVINGS);
		acc.deposit(6500.0);
		assertEquals(12.0, acc.earnedInterest(), -Double.MAX_VALUE);
	}

	@Test
	public void maxi_savings_account_withdrawal_within_ten_days_test() {
		Account maxiSavingsAcc = CreateAccount.createAccount(AccountType.MAXI_SAVINGS);
		maxiSavingsAcc.deposit(50000.0);
		maxiSavingsAcc.withdraw(1000.0);
		assertEquals(49.0, maxiSavingsAcc.earnedInterest(), -Double.MAX_VALUE);
	}

	@Test
	public void maxi_savings_account_interest_no_withdrawal_test() {
		Account maxiSavingsAcc = CreateAccount.createAccount(AccountType.MAXI_SAVINGS);
		maxiSavingsAcc.deposit(5000.0);
		assertEquals(250, maxiSavingsAcc.earnedInterest(), -Double.MAX_VALUE);
	}

	@Test
	public void maxi_savings_account_interest_no_withdrawal_ten_days_test() {
		// Override the date set on the transaction object to test interest (Maxi
		// Savings Account)
		Instant date = Instant.parse("2020-01-01T00:00:01.00Z");
		Account maxiSavingsAcc = CreateAccount.createAccount(AccountType.MAXI_SAVINGS);
		maxiSavingsAcc.deposit(4000.0);
		maxiSavingsAcc.getTransactions().get(0).setTransactionDate(date);
		maxiSavingsAcc.withdraw(2000.0);
		maxiSavingsAcc.getTransactions().get(1).setTransactionDate(date);
		assertEquals(100.0, maxiSavingsAcc.earnedInterest(), -Double.MAX_VALUE);
	}
}
