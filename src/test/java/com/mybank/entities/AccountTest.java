package com.mybank.entities;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mybank.exceptions.NotEnoughBalanceException;
import com.mybank.exceptions.UndefinedAccountTypeException;
import com.mybank.factories.InterestCalculatorFactory;
import com.mybank.calculators.CheckingAccountCalculator;
import com.mybank.calculators.InterestCalculator;
import com.mybank.entities.Account;

@RunWith(PowerMockRunner.class)
@PrepareForTest(InterestCalculatorFactory.class)
public class AccountTest {

	private static final double DOUBLE_DELTA = 1e-15;

	private Account testCheckingAccount;
	private Account testSavingsAccount;
	private Account testMaxiSavingsAccount;
	private Account testExceptionAccount;

	@Mock
	CheckingAccountCalculator mockCheckingAccountCalculator;
	@Mock
	InterestCalculator mockInterestCalculator;
	@Mock
	Transaction mockTransaction1;
	@Mock
	Transaction mockTransaction2;

	@Before
	public void setUp() throws UndefinedAccountTypeException {

		testCheckingAccount = new Account(Account.CHECKING);
		testSavingsAccount = new Account(Account.SAVINGS);
		testMaxiSavingsAccount = new Account(Account.MAXI_SAVINGS);
		testExceptionAccount = new Account(3);

		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions.add(mockTransaction1);
		transactions.add(mockTransaction2);

		mockCheckingAccountCalculator = Mockito.mock(CheckingAccountCalculator.class);
		mockInterestCalculator = Mockito.mock(InterestCalculator.class);

		PowerMockito.mockStatic(InterestCalculatorFactory.class);
		Mockito.when(InterestCalculatorFactory.getAccountCalculator(testCheckingAccount))
				.thenReturn(mockInterestCalculator);
		Mockito.when(InterestCalculatorFactory.getAccountCalculator(testSavingsAccount))
				.thenReturn(mockInterestCalculator);
		Mockito.when(InterestCalculatorFactory.getAccountCalculator(testMaxiSavingsAccount))
				.thenReturn(mockInterestCalculator);
		Mockito.when(mockInterestCalculator.calculateInterest(testCheckingAccount.getTransactions())).thenReturn(0.08);
		Mockito.when(mockInterestCalculator.calculateInterest(testSavingsAccount.getTransactions())).thenReturn(0.08);
		Mockito.when(mockInterestCalculator.calculateInterest(testMaxiSavingsAccount.getTransactions()))
				.thenReturn(0.08);

	}

	@Test
	public void deposit_ShouldAddTransaction_GivenPossible() {

		testCheckingAccount.deposit(100);
		assertEquals(1, testCheckingAccount.getTransactions().size());
		assertEquals(100, testCheckingAccount.getTransactions().get(0).getTransactionAmount(), DOUBLE_DELTA);
	}

	@Test(expected = IllegalArgumentException.class)
	public void deposit_ShouldThrowIllegalArgumentException_GivenNegativeValueIsPassed() {

		testCheckingAccount.deposit(-100);

	}

	@Test
	public void withdraw_ShouldAddTransaction_GivenPossibleTransaction()
			throws IllegalArgumentException, NotEnoughBalanceException {

		testCheckingAccount.deposit(200);
		testCheckingAccount.withdraw(100);

		assertEquals(2, testCheckingAccount.getTransactions().size());
		assertEquals(200, testCheckingAccount.getTransactions().get(0).getTransactionAmount(), DOUBLE_DELTA);
		assertEquals(-100, testCheckingAccount.getTransactions().get(1).getTransactionAmount(), DOUBLE_DELTA);

	}

	@Test(expected = IllegalArgumentException.class)
	public void withdraw_ShouldThrowIllegalArgumentException_GivenNegativeValueIsPassed()
			throws IllegalArgumentException, NotEnoughBalanceException {

		testCheckingAccount.withdraw(-100);

	}

	@Test(expected = NotEnoughBalanceException.class)
	public void withdraw_ShouldThrowNotEnoughBalanceException_GivenBalanceIsInsufficient()
			throws IllegalArgumentException, NotEnoughBalanceException {

		testCheckingAccount.withdraw(300);

	}

	@Test
	public void getBalance_ShouldReturnCurrentBalance() throws IllegalArgumentException, NotEnoughBalanceException {

		testCheckingAccount.deposit(100);
		testCheckingAccount.withdraw(20);
		assertEquals(80, testCheckingAccount.getBalance(), DOUBLE_DELTA);
	}

	@Test
	public void getInterestEarnedCoumpundedDaily_ShouldReturnInterestForCheckingAccount_GivenPossible()
			throws UndefinedAccountTypeException, IllegalArgumentException, NotEnoughBalanceException {

		assertEquals(0.08, testCheckingAccount.getInterestEarnedCompundedDaily(), DOUBLE_DELTA);

	}

	@Test
	public void getInterestEarnedCoumpundedDaily_ShouldReturnInterestForSavingsAccount_GivenBalanceIsLessThanAThousand()
			throws UndefinedAccountTypeException, IllegalArgumentException, NotEnoughBalanceException {

		assertEquals(0.08, testSavingsAccount.getInterestEarnedCompundedDaily(), DOUBLE_DELTA);

	}

	@Test
	public void getInterestEarnedCoumpundedDaily_ShouldReturnInterestForSavingsAccount_GivenIsGreaterThanAThousand()
			throws UndefinedAccountTypeException, IllegalArgumentException, NotEnoughBalanceException {

		testSavingsAccount.deposit(1100);
		assertEquals(0.08, testSavingsAccount.getInterestEarnedCompundedDaily(), DOUBLE_DELTA);

	}

	@Test
	public void getInterestEarnedCoumpoundedDaily_ShouldReturnInterestForMaxiSavingsAccount_GivenBalanceIsLessOrEqualToAThousand()
			throws UndefinedAccountTypeException, IllegalArgumentException, NotEnoughBalanceException {

		testMaxiSavingsAccount.deposit(100);
		assertEquals(0.08, testMaxiSavingsAccount.getInterestEarnedCompundedDaily(), DOUBLE_DELTA);

	}

	@Test
	public void getInterestEarnedCoumpoundedDaily_ShouldReturnInterestForMaxiSavingsAccount_GivenBalanceIsLessOrEqualToTwoThousand()
			throws UndefinedAccountTypeException, IllegalArgumentException, NotEnoughBalanceException {

		assertEquals(0.08, testMaxiSavingsAccount.getInterestEarnedCompundedDaily(), DOUBLE_DELTA);
	}

	@Test
	public void getInterestEarnedCoumpoundedDaily_ShouldReturnInterestForMaxiSavingsAccount_GivenBalanceIsGreaterThanTwoThousand()
			throws UndefinedAccountTypeException, IllegalArgumentException, NotEnoughBalanceException {

		assertEquals(0.08, testMaxiSavingsAccount.getInterestEarnedCompundedDaily(), DOUBLE_DELTA);

	}

	@Test(expected = UndefinedAccountTypeException.class)
	public void getInterestEarnedCoumpundedDaily_ShouldThrowUndefinedAccountTypeException_GivenUndefinedAccountType()
			throws UndefinedAccountTypeException {

		testExceptionAccount.getInterestEarnedCompundedDaily();

	}

}
