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
import com.abc.SavingAccount;
import com.abc.Transaction;

/**
 * SavingAccountTest
 * 
 * @author Fei
 * 
 */
public class SavingAccountTest {

	private static final double DOUBLE_DELTA = 1e-15;
	private Bank bank;
	private Customer fei;
	private Account feiSaving;
	private List<Transaction> feiSavingTran;

	@Before
	public void setUp() {
		bank = new Bank();
		fei = new Customer("Fei");
		bank.addCustomer(fei);
		feiSaving = new SavingAccount();
		fei.openAccount(feiSaving);
		feiSavingTran = feiSaving.getTransactions();
	}

	@Test
	// Normal deposit test
	public void depositTest() {
		feiSaving.deposit(500);
		assertTrue(500 == feiSaving.getBalance());
	}

	@Test
	// Normal withdraw test
	public void withdrawTestEnoughFunds() {
		feiSaving.deposit(500);
		feiSaving.withdraw(100);
		assertTrue(400 == feiSaving.getBalance());
	}

	@Test
	// Withdraw with no funds test
	public void withdrawTestNoFunds() {
		assertFalse(feiSaving.withdraw(100));
	}

	@Test
	// Withdraw with not enough funds test
	public void withdrawTestNotEnoughFunds() {
		feiSaving.deposit(50);
		feiSaving.withdraw(100);
		assertTrue(feiSaving.getBalance() == 50);
	}

	@Test
	// Interest calculation with funds less than threshold (1000) so
	// the interest rate is 0.1%
	public void interestEarnedTestOneTransLow() {
		// Test the transaction that happened 100 days ago
		Date now = DateProvider.getInstance().now();
		Date trans = new Date(now.getTime() - 100 * 1000 * 60 * 60 * 24L);

		// 1000*(1+0.001/365)^100-1000 = 0.27
		feiSavingTran.add(new Transaction(1000, trans, "deposit"));
		assertEquals(0.27, feiSaving.interestEarned(), DOUBLE_DELTA);
	}

	@Test
	// Interest calculation with funds more than threshold (1000) so
	// the interest rate is 0.2%
	public void interestEarnedTestOneTransHigh() {
		// Test the transaction that happened 100 days ago
		Date now = DateProvider.getInstance().now();
		Date trans = new Date(now.getTime() - 100 * 1000 * 60 * 60 * 24L);

		// 1000*(1+0.001/365)^100-1000 = 0.27
		feiSavingTran.add(new Transaction(2000, trans, "deposit"));
		assertEquals(0.82, feiSaving.interestEarned(), DOUBLE_DELTA);
	}

	@Test
	// Test interest calculate with mix conditions
	// With funds higher and lower than the threshold
	public void interestEarnedTestMultiTrans() {

		Date now = DateProvider.getInstance().now();

		// Generate transaction date of 100 days ago
		Date trans = new Date(now.getTime() - 100 * 1000 * 60 * 60 * 24L);

		// Generate transaction date of 50 days ago
		Date trans2 = new Date(now.getTime() - 50 * 1000 * 60 * 60 * 24L);

		// Generate transaction date of 30 days ago
		Date trans3 = new Date(now.getTime() - 30 * 1000 * 60 * 60 * 24L);
		feiSavingTran.add(new Transaction(1000, trans, "deposit"));
		feiSavingTran.add(new Transaction(1000, trans2, "deposit"));
		feiSavingTran.add(new Transaction(-1500, trans3, "withdraw"));
		/*
		 * According to the rule, the compound daily interest rate is calculated
		 * as: [1000*(1+0.001/365)^50 -1000] + [1000*(1+0.001/365)^20 -1000 +
		 * 1000*(1+0.002/365)^20 -1000] + [500*(1+0.001/365)^30 - 500]=0.34
		 */
		// Sum of the interest after three transactions
		assertEquals(0.34, feiSaving.interestEarned(), DOUBLE_DELTA);
	}

}
