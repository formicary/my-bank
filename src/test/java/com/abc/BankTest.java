package com.abc;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.abc.Account;
import com.abc.Bank;
import com.abc.CheckingAccount;
import com.abc.Customer;
import com.abc.DateProvider;
import com.abc.MaxiSavingAccount;
import com.abc.SavingAccount;
import com.abc.Transaction;

import static org.junit.Assert.assertEquals;

/**
 * Tested the Customer Summary and totalInterestPaid function
 * 
 * The totalInterestPaid test only focus on the sum of interest test rather than
 * interest rate calculation test
 * 
 * @author Fei
 * 
 */
public class BankTest {
	private static final double DOUBLE_DELTA = 1e-15;
	private Bank bank;
	private Customer fei;
	private Customer john;

	@Before
	public void setUp() {
		bank = new Bank();
		fei = new Customer("Fei");
		john = new Customer("John");
		bank.addCustomer(fei);
		bank.addCustomer(john);
	}

	@Test
	// Account size 0 and 1 test
	public void customerSummaryTestOneZero() {
		john.openAccount(new CheckingAccount());
		assertEquals(
				"Customer Summary\n - Fei (0 account)\n - John (1 account)",
				bank.customerSummary());
	}

	@Test
	// Account test with all three accounts
	public void customerSummaryTestThree() {
		john.openAccount(new CheckingAccount());
		john.openAccount(new SavingAccount());
		john.openAccount(new MaxiSavingAccount());
		assertEquals(
				"Customer Summary\n - Fei (0 account)\n - John (3 accounts)",
				bank.customerSummary());
	}

	/*
	 * This test only focus on the sum of interest. Whether the interest is
	 * calculated correct or not will be test separately in each account test
	 */
	@Test
	public void totalInterestPaidTest() {
		Account feiChecking = new CheckingAccount();
		Account johnChecking = new CheckingAccount();
		Account johnSaving = new SavingAccount();
		Account johnMaxiSaving = new MaxiSavingAccount();

		fei.openAccount(feiChecking);
		john.openAccount(johnChecking);
		john.openAccount(johnSaving);
		john.openAccount(johnMaxiSaving);

		List<Transaction> feiCheckingTran = feiChecking.getTransactions();
		List<Transaction> johnCheckingTran = johnChecking.getTransactions();
		List<Transaction> johnSavingTran = johnSaving.getTransactions();
		List<Transaction> johnMaxiSavingTran = johnMaxiSaving.getTransactions();

		// Test the transaction that happened 100 days ago
		Date now = DateProvider.getInstance().now();
		Date trans = new Date(now.getTime() - 100 * 1000 * 60 * 60 * 24L);

		// 1000*(1+0.001/365)^100 -1000 = 0.27
		feiCheckingTran.add(new Transaction(1000, trans, "deposit"));

		// 1000*(1+0.001/365)^100 -1000 = 0.27
		johnCheckingTran.add(new Transaction(1000, trans, "deposit"));

		// 1000*(1+0.001/365)^100 -1000 = 0.27
		johnSavingTran.add(new Transaction(1000, trans, "deposit"));

		// 1000*(1+0.05/365)^100 -1000 = 13.79
		johnMaxiSavingTran.add(new Transaction(1000, trans, "deposit"));

		// Sum: 14.60=3*0.27+13.79
		assertEquals(14.60, bank.totalInterestPaid(), DOUBLE_DELTA);
	}

}
