package com.abc.interestCalculation.interestCalculationStrategies;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.Test;
import org.mockito.Mockito;

import com.abc.Account;
import com.abc.AccountType;
import com.abc.Transaction;
import com.abc.TransactionType;
import com.abc.interestCalculation.InterestCalculator;

public class MaxiSavingsAccountInterestCalculationTest {

	private static final double DOUBLE_DELTA = 1e-10;

	@Test
	public void calculateInterestUnder10days() {

		InterestCalculator interestCalculator = new InterestCalculator();

		Account account = Mockito.mock(Account.class);

		Mockito.when(account.getAccountType()).thenReturn(AccountType.MAXI_SAVINGS);
		Mockito.when(account.getCurrentBalance()).thenReturn(1000.0);
		Mockito.when(account.getDaysSinceLastTransaction()).thenReturn((long) 25);

		Transaction t = Mockito.mock(Transaction.class);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -7);
		java.util.Date myDate = cal.getTime();
		Mockito.when(t.getDate()).thenReturn(myDate);
		Mockito.when(t.getType()).thenReturn(TransactionType.WITHDRAWAL);
		
		Mockito.when(account.getLastTransactionByType(TransactionType.WITHDRAWAL)).thenReturn(t);
		
		assertEquals(0.0684612702909817, interestCalculator.calculateInterest(account), DOUBLE_DELTA);

	}

	@Test
	public void calculateInterestOver10days() {

		InterestCalculator interestCalculator = new InterestCalculator();

		Account account = Mockito.mock(Account.class);

		Mockito.when(account.getAccountType()).thenReturn(AccountType.MAXI_SAVINGS);
		Mockito.when(account.getCurrentBalance()).thenReturn(1000.0);
		Mockito.when(account.getDaysSinceLastTransaction()).thenReturn((long) 25);
		
		Transaction t = Mockito.mock(Transaction.class);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -30);
		java.util.Date myDate = cal.getTime();
		Mockito.when(t.getDate()).thenReturn(myDate);
		Mockito.when(t.getType()).thenReturn(TransactionType.WITHDRAWAL);
		
		Mockito.when(account.getLastTransactionByType(TransactionType.WITHDRAWAL)).thenReturn(t);

		assertEquals(3.347382078660303, interestCalculator.calculateInterest(account), DOUBLE_DELTA);

	}

	@Test
	public void calculateInterestOver10days_NoWithdrawalsFound() {

		InterestCalculator interestCalculator = new InterestCalculator();

		Account account = Mockito.mock(Account.class);

		Mockito.when(account.getAccountType()).thenReturn(AccountType.MAXI_SAVINGS);
		Mockito.when(account.getCurrentBalance()).thenReturn(1000.0);
		Mockito.when(account.getDaysSinceLastTransaction()).thenReturn((long) 25);
		
		Transaction t = new Transaction(0, TransactionType.EMPTY);
		Mockito.when(account.getLastTransactionByType(TransactionType.WITHDRAWAL)).thenReturn(t);

		assertEquals(3.347382078660303, interestCalculator.calculateInterest(account), DOUBLE_DELTA);

	}
}
