package com.abc;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;


public class AccountTest {

	private static final double DOUBLE_DELTA = 1e-10;

	private Account acc2, acc3, acc4;
	Transaction t1, t2, t3, t4, t5, t6;

	@Before
	public void before() {
		acc2 = new Account(AccountType.SAVINGS);
		acc3 = new Account(AccountType.MAXI_SAVINGS);
		acc4 = new Account(AccountType.MAXI_SAVINGS);

		t1 = Mockito.mock(Transaction.class);
		t2 = Mockito.mock(Transaction.class);
		t3 = Mockito.mock(Transaction.class);

		Mockito.when(t1.getType()).thenReturn(TransactionType.DEPOSIT);
		Mockito.when(t2.getType()).thenReturn(TransactionType.WITHDRAWAL);
		Mockito.when(t3.getType()).thenReturn(TransactionType.INTEREST_EARNED);

		Mockito.when(t1.getAmount()).thenReturn(20000.0);
		Mockito.when(t2.getAmount()).thenReturn(-3000.0);
		Mockito.when(t3.getAmount()).thenReturn(0.0);

		t4 = Mockito.mock(Transaction.class);
		t5 = Mockito.mock(Transaction.class);
		t6 = Mockito.mock(Transaction.class);

		Mockito.when(t4.getType()).thenReturn(TransactionType.DEPOSIT);
		Mockito.when(t5.getType()).thenReturn(TransactionType.WITHDRAWAL);
		Mockito.when(t6.getType()).thenReturn(TransactionType.DEPOSIT);

		Mockito.when(t4.getAmount()).thenReturn(4000.0);
		Mockito.when(t5.getAmount()).thenReturn(-3000.0);
		Mockito.when(t6.getAmount()).thenReturn(9000.0);

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -20);
		java.util.Date d1 = cal.getTime();
		Mockito.when(t1.getDate()).thenReturn(d1);
		Mockito.when(t4.getDate()).thenReturn(d1);

		Calendar cal2 = Calendar.getInstance();
		cal2.add(Calendar.DATE, -15);
		java.util.Date d2 = cal2.getTime();
		Mockito.when(t2.getDate()).thenReturn(d2);
		Mockito.when(t5.getDate()).thenReturn(d2);

		Calendar cal3 = Calendar.getInstance();
		cal3.add(Calendar.DATE, -2);
		java.util.Date d3 = cal3.getTime();
		Mockito.when(t3.getDate()).thenReturn(d3);
		Mockito.when(t6.getDate()).thenReturn(d3);

		acc2.transactions.add(t1);
		acc2.transactions.add(t2);
		acc2.transactions.add(t3);

		acc3.transactions.add(t4);
		acc3.transactions.add(t5);
		acc3.transactions.add(t6);

	}

	@Test(expected = IllegalArgumentException.class)
	public void withdrawNegative() {
		Account acc = new Account(AccountType.CHECKING);
		acc.withdraw(-0.1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void depositNegative() {
		Account acc = new Account(AccountType.CHECKING);
		acc.deposit(-0.1);
	}

	@Test
	public void depositCorrect() {
		Account acc = new Account(AccountType.CHECKING);
		acc.deposit(150);
		assertEquals(acc.getCurrentBalance(), 150, DOUBLE_DELTA);
	}

	@Test(expected = IllegalArgumentException.class)
	public void withdrawTooMuch() {
		Account acc = new Account(AccountType.CHECKING);
		acc.deposit(150);
		acc.withdraw(150.0001);
	}

	@Test
	public void withdrawCorrect() {
		Account acc = new Account(AccountType.CHECKING);
		acc.deposit(150);
		acc.withdraw(100);
		assertEquals(acc.getCurrentBalance(), 50, DOUBLE_DELTA);
	}

	@Test
	public void getLastTransactionTypeMethods_NoTransactionsFound() {
		Account acc = new Account(AccountType.CHECKING);

		assertEquals(acc.getDaysSinceLastTransaction(), 0, DOUBLE_DELTA);

		assertEquals(acc.getLastTransaction().getType(), TransactionType.EMPTY);

		assertEquals(acc.getLastTransactionByType(TransactionType.DEPOSIT).getType(), TransactionType.EMPTY);

		acc.transactions.add(new Transaction(10, TransactionType.DEPOSIT));

		assertEquals(acc.getLastTransactionByType(TransactionType.WITHDRAWAL).getType(), TransactionType.EMPTY);
	}

	@Test
	public void getLastTransactionTypeMethods_ValidExistingTransactions() {

		assertEquals(acc2.getDaysSinceLastTransaction(), 2, DOUBLE_DELTA);

		assertEquals(acc2.getLastTransaction().getType(), TransactionType.INTEREST_EARNED);

		DateProvider dp = new DateProvider();
		long diff = dp.daysAppart(acc2.getLastTransactionByType(TransactionType.WITHDRAWAL).getDate(), dp.now());
		assertEquals(diff, 15, DOUBLE_DELTA);

	}

	@Test
	public void sumTransactionsTest() {
		Account acc = new Account(AccountType.CHECKING);

		assertEquals(acc.sumTransactions(), 0, DOUBLE_DELTA);

		assertEquals(acc3.sumTransactions(), 10000, DOUBLE_DELTA);
	}

	@Test
	public void updateBalanceAndInterestEarnedTest() {
		
		acc4.deposit(4000.0);
		acc4.transactions.remove(1);
		acc4.transactions.add(t4);
		acc4.withdraw(3000);
	
		assertEquals(acc4.getCurrentBalance(), 1010.70804185445786, DOUBLE_DELTA);
		assertEquals(acc4.interestEarned(), acc4.getCurrentBalance() - 1000, DOUBLE_DELTA);
	}

	@Test
	public void updateBalance_NoTransactionsFound() {
		Account acc = new Account(AccountType.CHECKING);
		acc.updateBalance();
		assertEquals(acc.getCurrentBalance(), 0, DOUBLE_DELTA);
	}

}
