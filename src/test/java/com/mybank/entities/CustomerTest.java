package com.mybank.entities;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.mybank.exceptions.NonExistentAccountException;
import com.mybank.exceptions.NotEnoughBalanceException;
import com.mybank.exceptions.UndefinedAccountTypeException;
import com.mybank.entities.Account;
import com.mybank.entities.Bank;
import com.mybank.entities.Customer;
import com.mybank.entities.Transaction;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;

public class CustomerTest {

	private static final double DOUBLE_DELTA = 1e-15;
	private Customer testCustomer = new Customer("John");

	@Mock
	Bank mockBank;
	@Mock
	Account mockSavingsAccount;
	@Mock
	Account mockCheckingAccount;
	@Mock
	Account mockMaxiSavingsAccount;
	@Mock
	Transaction mockDepositTransaction;
	@Mock
	Transaction mockWithdrawTransaction;

	@Before
	public void setUp() {

		mockBank = Mockito.mock(Bank.class);
		mockDepositTransaction = Mockito.mock(Transaction.class);
		mockWithdrawTransaction = Mockito.mock(Transaction.class);
		mockSavingsAccount = Mockito.mock(Account.class);
		mockCheckingAccount = Mockito.mock(Account.class);
		mockMaxiSavingsAccount = Mockito.mock(Account.class);
		

		testCustomer.openAccount(mockSavingsAccount);
		testCustomer.openAccount(mockCheckingAccount);
		testCustomer.openAccount(mockMaxiSavingsAccount);

		ArrayList<Transaction> transactions = new ArrayList<Transaction>();
		transactions.add(mockDepositTransaction);
		transactions.add(mockWithdrawTransaction);
		
		Mockito.when(mockDepositTransaction.getTransactionAmount()).thenReturn((double) 100);
		Mockito.when(mockWithdrawTransaction.getTransactionAmount()).thenReturn((double) -20);
		Mockito.when(mockSavingsAccount.getTransactions()).thenReturn(transactions);
		Mockito.when(mockCheckingAccount.getTransactions()).thenReturn(transactions);
		Mockito.when(mockMaxiSavingsAccount.getTransactions()).thenReturn(transactions);
	}

	@Test
	public void openAccount_ShouldAddNewAccount() {

		assertEquals(3, testCustomer.getNumberOfAccounts());
		assertEquals(mockSavingsAccount, testCustomer.getAccounts().get(0));
		assertEquals(mockCheckingAccount, testCustomer.getAccounts().get(1));
		assertEquals(mockMaxiSavingsAccount, testCustomer.getAccounts().get(2));

	}

	@Test
	public void totalInterestEarned_ShouldReturnAmountOfTotalInterest_GivenPossible()
			throws UndefinedAccountTypeException { // given

		Mockito.when(mockCheckingAccount.getInterestEarnedCompundedDaily()).thenReturn(0.10);
		Mockito.when(mockSavingsAccount.getInterestEarnedCompundedDaily()).thenReturn(0.10);
		Mockito.when(mockMaxiSavingsAccount.getInterestEarnedCompundedDaily()).thenReturn(0.10);
		assertEquals(0.30, testCustomer.totalInterestEarned(), DOUBLE_DELTA);
	}

	@Test(expected = UndefinedAccountTypeException.class)
	public void totalInterestEarned_ShouldThrowUndefinedAccountTypeExeption_GivenUndefinedAccountType()
			throws UndefinedAccountTypeException {

		Mockito.when(mockSavingsAccount.getInterestEarnedCompundedDaily())
				.thenThrow(new UndefinedAccountTypeException());
		testCustomer.totalInterestEarned();

	}

	@Test
	public void getStatement_ShouldReturnStatement() {

		Mockito.when(mockSavingsAccount.getAccountType()).thenReturn(Account.SAVINGS);
		Mockito.when(mockCheckingAccount.getAccountType()).thenReturn(Account.CHECKING);
		Mockito.when(mockMaxiSavingsAccount.getAccountType()).thenReturn(Account.MAXI_SAVINGS);
		Mockito.when(mockSavingsAccount.getBalance()).thenReturn(80.00);
		Mockito.when(mockCheckingAccount.getBalance()).thenReturn(80.00);
		Mockito.when(mockMaxiSavingsAccount.getBalance()).thenReturn(80.00);

		assertEquals("Statement for John\n\n" + "Savings Account\n" + " deposit $100.00\n" + " withdrawal $20.00\n"
				+ "Total $80.00\n\n" + "Checking Account\n" + " deposit $100.00\n" + " withdrawal $20.00\n"
				+ "Total $80.00\n\n" + "Maxi Savings Account\n" + " deposit $100.00\n" + " withdrawal $20.00\n"
				+ "Total $80.00\n\n" + "Total In All Accounts $240.00", testCustomer.getStatement());

	}

	@Test
	public void getStatement_ShouldReturnErrorStatement_GivenUndefinedAccountType() {

		Account exceptionAccount = Mockito.mock(Account.class);
		Mockito.when(exceptionAccount.getAccountType()).thenReturn(4);
		testCustomer.openAccount(exceptionAccount);

		assertEquals("Please contact the bank for the details of yout accounts.", testCustomer.getStatement());

	}

	@Test
	public void transferFromTo_ShouldTransferAmountFromOneAccountToAnother_GivenPossible()
			throws NonExistentAccountException, IllegalArgumentException, NotEnoughBalanceException {

		Account fromAccount = new Account(Account.CHECKING);
		Account toAccount = new Account(Account.SAVINGS);

		testCustomer.openAccount(fromAccount);
		testCustomer.openAccount(toAccount);

		fromAccount.deposit(100);

		testCustomer.transferFromTo(fromAccount, toAccount, 20);
		int sizeTransactionsFromAccount = fromAccount.getTransactions().size();
		int sizeTransactionsToAccount = toAccount.getTransactions().size();
		assertEquals(fromAccount.getTransactions().get(sizeTransactionsFromAccount - 1).getTransactionAmount(), -20,
				DOUBLE_DELTA);
		assertEquals(toAccount.getTransactions().get(sizeTransactionsToAccount - 1).getTransactionAmount(), 20,
				DOUBLE_DELTA);

	}

	@Test(expected = NonExistentAccountException.class)
	public void transferFromTo_ShouldThrowNonExistentAccountExcetion_GivenAccountsDontExist()
			throws NonExistentAccountException, IllegalArgumentException, NotEnoughBalanceException {

		Account mockAccount = Mockito.mock(Account.class);
		testCustomer.transferFromTo(mockAccount, mockCheckingAccount, 20);

	}

	@Test(expected = NotEnoughBalanceException.class)
	public void transferFromTo_ShouldThrowNotEnoughBalanceExcetion_GivenInsufficientBalance()
			throws NonExistentAccountException, IllegalArgumentException, NotEnoughBalanceException {

		Account fromAccount = new Account(Account.CHECKING);
		testCustomer.openAccount(fromAccount);

		testCustomer.transferFromTo(fromAccount, mockCheckingAccount, 20);

	}

	@Test(expected = IllegalArgumentException.class)
	public void transferFromTo_ShouldThrowIllegalArgumentException_GivenNegativeValuePassed()
			throws NonExistentAccountException, IllegalArgumentException, NotEnoughBalanceException {

		Account fromAccount = new Account(Account.CHECKING);
		Account toAccount = new Account(Account.SAVINGS);

		testCustomer.openAccount(fromAccount);
		testCustomer.openAccount(toAccount);

		fromAccount.deposit(100);
		testCustomer.transferFromTo(fromAccount, toAccount, -20);

	}
}
