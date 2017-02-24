package com.mybank.calculators;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.mybank.entities.Transaction;
import com.mybank.exceptions.NotEnoughBalanceException;
import com.mybank.exceptions.UndefinedAccountTypeException;

public class MaxiSavingsAccountCalculatorTest {
	
	private static final double DOUBLE_DELTA = 1e-15;
	private List<Transaction> transactions;
	private MaxiSavingsAccountCalculator testCalculator;

	@Mock
	Transaction transaction1;
	
	@Mock
	Transaction transaction2;
	
	@Mock
	Transaction transaction3;
	
	@Mock
	Transaction transaction4;
	
	@Mock
	Transaction transaction5;
	
	@Before
	public void setUp(){
		
		testCalculator = new MaxiSavingsAccountCalculator();
		
		transaction1 = Mockito.mock(Transaction.class);
		transaction2 = Mockito.mock(Transaction.class);
		transaction3 = Mockito.mock(Transaction.class);
		transaction4 = Mockito.mock(Transaction.class);
		transaction5 = Mockito.mock(Transaction.class);
		
		Mockito.when(transaction3.isMadeToday()).thenReturn(true);
		Mockito.when(transaction2.isMadeToday()).thenReturn(false);
		Mockito.when(transaction1.isMadeToday()).thenReturn(false);
		
		Mockito.when(transaction1.getDaysToNextTransaction(transaction2.getTransactionDateTime())).thenReturn((long) 2);
		Mockito.when(transaction2.getDaysToNextTransaction(transaction3.getTransactionDateTime())).thenReturn((long) 5);
		Mockito.when(transaction3.getDaysToNextTransaction(new GregorianCalendar())).thenReturn((long) 0);
		
		transactions = new ArrayList<Transaction>();
		transactions.add(transaction1);
		transactions.add(transaction2);
		transactions.add(transaction3);
	}	
	
	@Test
	public void calculateInterest_ShouldReturnInterestForMaxiSavingsAccount_GivenBalanceIsLessThanAThousand() {
		
		Mockito.when(transaction1.getTransactionAmount()).thenReturn((double) 300);
		Mockito.when(transaction2.getTransactionAmount()).thenReturn((double) -30);
		Mockito.when(transaction3.getTransactionAmount()).thenReturn((double) 400);
		
		assertEquals(0.10581539151357167, testCalculator.calculateInterest(transactions), DOUBLE_DELTA);
	}
	
	@Test
	public void calculateInterest_ShouldReturnInterestForSavingsAccount_GivenBalanceIsGreaterThanAThousandAndLessThanTwoThousand() throws UndefinedAccountTypeException, IllegalArgumentException, NotEnoughBalanceException {

		Mockito.when(transaction1.getTransactionAmount()).thenReturn((double) 700);
		Mockito.when(transaction2.getTransactionAmount()).thenReturn((double) -100);
		Mockito.when(transaction3.getTransactionAmount()).thenReturn((double) 500);
		
		assertEquals(0.23876341028441508, testCalculator.calculateInterest(transactions), DOUBLE_DELTA);
		
	}
	
	@Test
	public void calculateInterest_ShouldReturnInterestForSavingsAccount_GivenBalanceIsGreaterThanTwoThousand() throws UndefinedAccountTypeException, IllegalArgumentException, NotEnoughBalanceException {

		Mockito.when(transaction1.getTransactionAmount()).thenReturn((double) 1000);
		Mockito.when(transaction2.getTransactionAmount()).thenReturn((double) 3000);
		Mockito.when(transaction3.getTransactionAmount()).thenReturn((double) -500);
		
		assertEquals(3.6614858677207276, testCalculator.calculateInterest(transactions), DOUBLE_DELTA);
	}
}
