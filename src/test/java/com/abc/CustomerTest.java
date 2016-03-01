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

import static org.junit.Assert.*;

/**
 * Test customer class
 * 
 * Include the test for customer account transfer
 * 
 * @author Fei
 * 
 */
public class CustomerTest {
	private static final double DOUBLE_DELTA = 1e-15;
	private Bank bank;
	private Customer fei;

	@Before
	public void setUp() {
		bank = new Bank();
		fei = new Customer("Fei");
		bank.addCustomer(fei);
	}

	@Test
	// Test account transfer with not enough funds
	public void accountTransferTestNotEnouthFunds() {
		Account feiChecking = new CheckingAccount();
		Account feiSaving = new SavingAccount();

		fei.openAccount(feiChecking);
		fei.openAccount(feiSaving);
		feiChecking.deposit(500);
		feiSaving.deposit(500);

		// Transaction unsuccessful test
		assertFalse(feiChecking.transfer(600, feiSaving));

		// Account not changed
		assertEquals(500.00, feiChecking.getBalance(), DOUBLE_DELTA);

		// Account not changed
		assertEquals(500.00, feiSaving.getBalance(), DOUBLE_DELTA);
	}

	@Test
	// Test account transfer with enough funds
	public void accountTransferTestEnouthFunds() {
		Account feiChecking = new CheckingAccount();
		Account feiSaving = new SavingAccount();

		fei.openAccount(feiChecking);
		fei.openAccount(feiSaving);
		feiChecking.deposit(500);
		feiSaving.deposit(500);

		// Transaction successful test
		assertTrue(feiChecking.transfer(300, feiSaving));

		// Transfer out account test
		assertEquals(200.00, feiChecking.getBalance(), DOUBLE_DELTA);

		// Transfer in account test
		assertEquals(800.00, feiSaving.getBalance(), DOUBLE_DELTA);

	}

	@Test
	// Test the sum of the interest of all accounts
	public void totalInterestEarnedTest() {
		Account feiChecking = new CheckingAccount();
		Account feiSaving = new SavingAccount();
		Account feiMaxiSaving = new MaxiSavingAccount();

		fei.openAccount(feiChecking);
		fei.openAccount(feiSaving);
		fei.openAccount(feiMaxiSaving);

		List<Transaction> feiCheckingTran = feiChecking.getTransactions();
		List<Transaction> feiSavingTran = feiSaving.getTransactions();
		List<Transaction> feiMaxiSavingTran = feiMaxiSaving.getTransactions();

		// Test the transaction that happened 100 days ago
		Date now = DateProvider.getInstance().now();
		Date trans = new Date(now.getTime() - 100 * 1000 * 60 * 60 * 24L);

		// 1000*(1+0.001/365)^100 -1000 = 0.27
		feiCheckingTran.add(new Transaction(1000, trans, "deposit"));

		// 1000*(1+0.001/365)^100-1000 = 0.27
		feiSavingTran.add(new Transaction(1000, trans, "deposit"));

		// 1000*(1+0.05/365)^100-1000 = 13.79
		feiMaxiSavingTran.add(new Transaction(1000, trans, "deposit"));

		// Sum: 14.33=2*0.27+13.79
		assertEquals(14.33, fei.totalInterestEarned(), DOUBLE_DELTA);
	}

	@Test
	// Get account test for all three accounts
	public void getAccountTest() {
		fei.openAccount(new CheckingAccount());
		assertTrue(fei.getAccount("Checking Account") instanceof CheckingAccount);
		fei.openAccount(new SavingAccount());
		assertTrue(fei.getAccount("Saving Account") instanceof SavingAccount);
		fei.openAccount(new MaxiSavingAccount());
		assertTrue(fei.getAccount("Maxi Saving Account") instanceof MaxiSavingAccount);
	}

	@Test
	// Test customer statement generation
	public void getStatementTest() {

		Account checkingAccount = new CheckingAccount();
		Account savingsAccount = new SavingAccount();

		fei.openAccount(checkingAccount);
		fei.openAccount(savingsAccount);

		checkingAccount.deposit(100.0);
		savingsAccount.deposit(4000.0);
		savingsAccount.withdraw(200.0);

		assertEquals("Statement for Fei\n" + "\n" + "Checking Account\n"
				+ "  deposit $100.00\n" + "Total $100.00\n" + "\n"
				+ "Saving Account\n" + "  deposit $4,000.00\n"
				+ "  withdraw $200.00\n" + "Total $3,800.00\n" + "\n"
				+ "Total In All Accounts $3,900.00", fei.getStatement());
	}

	@Test
	// Test customer single account statement generation
	public void statementForAccountTest() {

		Account savingsAccount = new SavingAccount();
		fei.openAccount(savingsAccount);
		savingsAccount.deposit(4000.0);
		savingsAccount.withdraw(200.0);

		assertEquals("Saving Account\n" + "  deposit $4,000.00\n"
				+ "  withdraw $200.00\n" + "Total $3,800.00",
				fei.statementForAccount(savingsAccount));
	}

}
