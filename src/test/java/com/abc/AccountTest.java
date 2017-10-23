package com.abc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertSame;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

/**
 * Test cases for the account class
 * 
 * @author Christopher J. Smith
 */
public class AccountTest {

	@Test
	/**
	 * Test creation of an account, check if variable set properly.
	 */
	public void accountCreation() {
		Account am = new Account(Account.AccountType.MAXI_SAVINGS);
		Account as = new Account(Account.AccountType.SAVINGS);
		Account ac = new Account(Account.AccountType.CHECKING);
		assertTrue(am.getAccountType() == Account.AccountType.MAXI_SAVINGS
				&& as.getAccountType() == Account.AccountType.SAVINGS
				&& ac.getAccountType() == Account.AccountType.CHECKING);
	}

	@Test
	/**
	 * Test deposition into a account.
	 */
	public void deposit() {
		Account ac = new Account(Account.AccountType.CHECKING);
		ac.deposit(10.1);
		ac.deposit(6);
		ac.deposit(101);
		assertEquals(3, ac.getNumberTransactions());
	}

	@Test
	/**
	 * Test withdrawing from an account.
	 */
	public void withdraw() {
		Account ac = new Account(Account.AccountType.CHECKING);
		ac.withdraw(32.6);
		ac.withdraw(7);
		ac.withdraw(681);
		ac.withdraw(2);
		ac.withdraw(88888);
		assertEquals(5, ac.getNumberTransactions());
	}

	@Test
	/**
	 * Test calculating annual interest for an account.
	 */
	public void interest() {
		Account am = new Account(Account.AccountType.MAXI_SAVINGS);
		am.deposit(10000);
		am.deposit(666);
		am.deposit(489);
		assertTrue(am.getAnnualInterest().compareTo(new BigDecimal("985.5")) == 0);
	}

	@Test
	/**
	 * Test calculating daily interest for an account.
	 */
	public void interestDaily() {
		Account am = new Account(Account.AccountType.MAXI_SAVINGS);
		am.deposit(10000);
		am.deposit(666);
		am.deposit(489);
		assertTrue(am.getDailyInterest().compareTo(new BigDecimal("2.66085")) == 0);
	}

	@Test
	/**
	 * Test creating a clone list.
	 */
	public void listClone() {
		Account am = new Account(Account.AccountType.MAXI_SAVINGS);
		am.deposit(10000);
		List<Transaction> l = am.getTransactionsClone();
		assertSame(l.get(0), am.getTransactionIterator().next());
	}

	@Test
	/**
	 * Test clone is is a clone and not connected to account.
	 */
	public void listCloneConnected() {
		Account am = new Account(Account.AccountType.MAXI_SAVINGS);
		am.deposit(10000);
		List<Transaction> l = am.getTransactionsClone();
		l.add(new Transaction(1000000));
		assertNotEquals(l.size(), am.getNumberTransactions());
	}

	@Test
	/**
	 * Test iterator is provided and works as expected.
	 */
	public void transactionIterator() {
		Account am = new Account(Account.AccountType.MAXI_SAVINGS);
		am.deposit(10000);
		am.deposit(666);
		am.deposit(489);
		am.deposit(999);
		am.deposit(123456);
		Iterator<Transaction> i = am.getTransactionIterator();
		i.next();
		i.next();
		i.next();
		i.next();
		assertTrue(i.next().getAmount().compareTo(new BigDecimal("123456")) == 0);
	}

	@Test
	/**
	 * Test summing transactions of an account.
	 */
	public void sumTransactions() {
		Account am = new Account(Account.AccountType.MAXI_SAVINGS);
		am.deposit(9894);
		am.deposit(185102);
		am.deposit(new BigDecimal("1896.58"));
		am.deposit(485);
		am.deposit(6886);
		assertTrue(am.getTransactionsSum().compareTo(new BigDecimal("204263.58")) == 0);
	}

	@Test
	/**
	 * Test summing transactions of an account, with withdrawals present.
	 */
	public void sumTransactionsWithdrawals() {
		Account am = new Account(Account.AccountType.MAXI_SAVINGS);
		am.withdraw(9894);
		am.deposit(185102);
		am.deposit(new BigDecimal("1896.58"));
		am.deposit(485);
		am.withdraw(6886);
		assertTrue(am.getTransactionsSum().compareTo(new BigDecimal("170703.58")) == 0);
	}

	@Test
	/**
	 * Test summing transactions of an account, with withdrawals present making
	 * account negative.
	 */
	public void sumTransactionsWithdrawalsDebt() {
		Account am = new Account(Account.AccountType.MAXI_SAVINGS);
		am.withdraw(1000000);
		am.withdraw(64);
		assertTrue(am.getTransactionsSum().compareTo(new BigDecimal("-1000064")) == 0);
	}

	@Test
	/**
	 * Testing transaction exists method.
	 */
	public void transactionsExist() {
		Account am = new Account(Account.AccountType.MAXI_SAVINGS);
		am.deposit(9894);
		am.deposit(185102);
		assertEquals(am.transactionsExist(), true);
	}

	@Test
	/**
	 * Testing transaction exists for no transactions.
	 */
	public void transactionsExistFalse() {
		Account am = new Account(Account.AccountType.MAXI_SAVINGS);
		assertEquals(am.transactionsExist(), false);
	}

	@Test
	/**
	 * Testing account statements are produced correctly.
	 */
	public void statementTest() {
		Account am = new Account(Account.AccountType.MAXI_SAVINGS);
		am.withdraw(9894);
		am.deposit(185102);
		am.deposit(new BigDecimal("1896.58"));
		am.deposit(485);
		am.withdraw(6886);

		String expected = "Maxi Savings Account\n" + 
							"  Withdrawal $9,894.00\n" + 
							"  Deposit $185,102.00\n" + 
							"  Deposit $1,896.58\n" + 
							"  Deposit $485.00\n" + 
							"  Withdrawal $6,886.00\n" + 
							"Total $170,703.58";
		assertEquals(expected, am.getStatement());
	}

	@Test
	/**
	 * Tests the toString method.
	 */
	public void stringCreation() {
		Account ac = new Account(Account.AccountType.CHECKING);
		ac.deposit(10.1);
		ac.deposit(6);
		ac.deposit(101);

		String expected = "Holdings: $117.10  Transactions: 3";
		assertEquals(expected, ac.toString());
	}
}
