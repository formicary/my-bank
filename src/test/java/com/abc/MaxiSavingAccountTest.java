package com.test;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.abc.Account;
import com.abc.Bank;
import com.abc.Customer;
import com.abc.DateProvider;
import com.abc.MaxiSavingAccount;
import com.abc.Transaction;

/**
 * Maxi Saving account test
 * 
 * @author Fei
 * 
 */
public class MaxiSavingAccountTest {

	private static final double DOUBLE_DELTA = 1e-15;
	private Bank bank;
	private Customer fei;
	private Account feiMaxiSaving;
	private List<Transaction> feiMaxSavingTran;

	@Before
	public void setUp() {
		bank = new Bank();
		fei = new Customer("Fei");
		bank.addCustomer(fei);
		feiMaxiSaving = new MaxiSavingAccount();
		fei.openAccount(feiMaxiSaving);
		feiMaxSavingTran = feiMaxiSaving.getTransactions();
	}

	@Test
	// Normal deposit test
	public void depositTest() {
		feiMaxiSaving.deposit(500);
		assertTrue(500 == feiMaxiSaving.getBalance());
	}

	@Test
	// Normal withdraw test
	public void withdrawTestEnoughFunds() {
		feiMaxiSaving.deposit(500);
		feiMaxiSaving.withdraw(100);
		assertTrue(400 == feiMaxiSaving.getBalance());
	}

	@Test
	// Withdraw with no funds test
	public void withdrawTestNoFunds() {
		assertFalse(feiMaxiSaving.withdraw(100));
	}

	@Test
	// Withdraw with not enough funds test
	public void withdrawTestNotEnoughFunds() {
		feiMaxiSaving.deposit(50);
		feiMaxiSaving.withdraw(100);
		assertTrue(feiMaxiSaving.getBalance() == 50);
	}

	@Test
	// Interest calculation test with no withdraw in past 10 days
	// so the interest rate is 5%
	public void interestEarnedTestOneTransNoWithdraw() {
		// Test the transaction that happened 100 days ago
		Date now = DateProvider.getInstance().now();
		Date trans = new Date(now.getTime() - 100 * 1000 * 60 * 60 * 24L);

		// 1000*(1+0.001/365)^100-1000=13.79
		feiMaxSavingTran.add(new Transaction(1000, trans, "deposit"));
		assertEquals(13.79, feiMaxiSaving.interestEarned(), DOUBLE_DELTA);
	}

	@Test
	// Interest calculation test with withdraw in past 10 days
	// so the interest rate is 0.1%
	public void interestEarnedTestOneTransWithdraw() {

		Date now = DateProvider.getInstance().now();
		// Generate transaction date of 100 days ago
		Date trans = new Date(now.getTime() - 100 * 1000 * 60 * 60 * 24L);
		// Generate transaction date of 3 days ago
		Date trans2 = new Date(now.getTime() - 3 * 1000 * 60 * 60 * 24L);
		// Deposit 100 days ago and withdraw 3 days ago
		feiMaxSavingTran.add(new Transaction(1000, trans, "deposit"));
		feiMaxSavingTran.add(new Transaction(-100, trans2, "withdraw"));
		/*
		 * According to the rule, we have withdraw within last 10 days (3 in
		 * this case) the interest rate is 0.001: [1000*(1+0.001/365)^100 -1000]
		 * - [(100*(1+0.001/365)^3 -100)]=0.27
		 */
		assertEquals(0.27, feiMaxiSaving.interestEarned(), DOUBLE_DELTA);
	}

}
