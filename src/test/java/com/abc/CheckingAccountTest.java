package com.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.abc.Account;
import com.abc.Bank;
import com.abc.CheckingAccount;
import com.abc.Customer;
import com.abc.DateProvider;
import com.abc.Transaction;

/**
 * CheckingAccount Test
 * 
 * @author Fei
 * 
 */
public class CheckingAccountTest {
	private static final double DOUBLE_DELTA = 1e-15;
	private Bank bank;
	private Customer fei;
	private Account feiChecking;
	private List<Transaction> feiCheckingTran;

	@Before
	public void setUp() {
		bank = new Bank();
		fei = new Customer("Fei");
		bank.addCustomer(fei);
		feiChecking = new CheckingAccount();
		fei.openAccount(feiChecking);
		feiCheckingTran = feiChecking.getTransactions();
	}

	@Test
	// Normal deposit
	public void depositTest() {
		feiChecking.deposit(500);
		assertTrue(500 == feiChecking.getBalance());
	}

	@Test
	// Normal withdraw
	public void withdrawTestEnoughFunds() {
		feiChecking.deposit(500);
		feiChecking.withdraw(100);
		assertTrue(400 == feiChecking.getBalance());
	}

	@Test
	// Withdraw with no funds in account
	public void withdrawTestNoFunds() {
		assertFalse(feiChecking.withdraw(100));
	}

	@Test
	// Withdraw with not enough funds in account
	public void withdrawTestNotEnoughFunds() {
		feiChecking.deposit(50);
		feiChecking.withdraw(100);
		assertTrue(feiChecking.getBalance() == 50);
	}

	@Test
	// Test with only one transactions
	public void interestEarnedTestOneTrans() {

		// Test the transaction that happened 100 days ago
		Date now = DateProvider.getInstance().now();
		Date trans = new Date(now.getTime() - 100 * 1000 * 60 * 60 * 24L);

		// 1000*(1+0.001/365)^100 -1000 = 0.2740
		feiCheckingTran.add(new Transaction(1000, trans, "deposit"));
		assertEquals(0.27, bank.totalInterestPaid(), DOUBLE_DELTA);
	}

	@Test
	// Test with multiple transactions in an account
	public void interestEarnedTestMultiTrans() {

		// Test the transaction that happened 100,50 and 30 days ago
		Date now = DateProvider.getInstance().now();
		Date trans = new Date(now.getTime() - 100 * 1000 * 60 * 60 * 24L);
		Date trans2 = new Date(now.getTime() - 50 * 1000 * 60 * 60 * 24L);
		Date trans3 = new Date(now.getTime() - 30 * 1000 * 60 * 60 * 24L);

		// 1000*(1+0.001/365)^100 -1000 = 0.27
		feiCheckingTran.add(new Transaction(1000, trans, "deposit"));

		// 1000*(1+0.001/365)^50 -1000 = 0.14
		feiCheckingTran.add(new Transaction(1000, trans2, "deposit"));

		// -500*(1+0.001/365)^30 +500 = -0.04
		feiCheckingTran.add(new Transaction(-500, trans3, "withdraw"));

		// Sum of the interest after three transactions
		assertEquals(0.37, feiChecking.interestEarned(), DOUBLE_DELTA);
	}

}
