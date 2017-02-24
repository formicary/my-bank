package com.mybank.calculators;

import static org.junit.Assert.*;

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

public class SavingsAccountCalculatorTest {
	
	private static final double DOUBLE_DELTA = 1e-15;
	private List<Transaction> transactions;
	private SavingsAccountCalculator testCalculator;

	@Mock
	Transaction transaction1;
	@Mock
	Transaction transaction2;
	@Mock
	Transaction transaction3;
	@Mock
	Transaction transaction4;
	
	@Before
	public void setUp(){
		
		testCalculator = new SavingsAccountCalculator();
		
		transaction1 = Mockito.mock(Transaction.class);
		transaction2 = Mockito.mock(Transaction.class);
		transaction3 = Mockito.mock(Transaction.class);
		transaction4 = Mockito.mock(Transaction.class);
		
		Mockito.when(transaction4.isMadeToday()).thenReturn(true);
		Mockito.when(transaction3.isMadeToday()).thenReturn(false);
		Mockito.when(transaction2.isMadeToday()).thenReturn(false);
		Mockito.when(transaction1.isMadeToday()).thenReturn(false);
		
		Mockito.when(transaction1.getDaysToNextTransaction(transaction2.getTransactionDateTime())).thenReturn((long) 7);
		Mockito.when(transaction2.getDaysToNextTransaction(transaction3.getTransactionDateTime())).thenReturn((long) 8);
		Mockito.when(transaction3.getDaysToNextTransaction(transaction4.getTransactionDateTime())).thenReturn((long) 5);
		Mockito.when(transaction4.getDaysToNextTransaction(new GregorianCalendar())).thenReturn((long) 0);
		
		
		transactions = new ArrayList<Transaction>();
		transactions.add(transaction1);
		transactions.add(transaction2);
		transactions.add(transaction3);
		transactions.add(transaction4);
	}
	
		
	
	@Test
	public void calculateInterest_ShouldReturnInterestForSavingsAccount_GivenBalanceIsLessThanAThousand() throws UndefinedAccountTypeException, IllegalArgumentException, NotEnoughBalanceException {

		Mockito.when(transaction1.getTransactionAmount()).thenReturn((double) 300);
		Mockito.when(transaction2.getTransactionAmount()).thenReturn((double) -30);
		Mockito.when(transaction3.getTransactionAmount()).thenReturn((double) 400);
		Mockito.when(transaction4.getTransactionAmount()).thenReturn((double) 20);
		
		assertEquals(0.020839365764293752, testCalculator.calculateInterest(transactions), DOUBLE_DELTA);
		
	}
	
	@Test
	public void calculateInterest_ShouldReturnInterestForSavingsAccount_GivenBalanceIsGreaterThanAThousand() throws UndefinedAccountTypeException, IllegalArgumentException, NotEnoughBalanceException {

		Mockito.when(transaction1.getTransactionAmount()).thenReturn((double) 700);
		Mockito.when(transaction2.getTransactionAmount()).thenReturn((double) 400);
		Mockito.when(transaction3.getTransactionAmount()).thenReturn((double) -150);
		Mockito.when(transaction4.getTransactionAmount()).thenReturn((double) 200);
		
		assertEquals(0.052712871072097496, testCalculator.calculateInterest(transactions), DOUBLE_DELTA);
		
	}

}
