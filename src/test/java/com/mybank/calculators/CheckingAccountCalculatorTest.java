package com.mybank.calculators;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.mybank.entities.Transaction;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class CheckingAccountCalculatorTest {

	private static final double DOUBLE_DELTA = 1e-15;
	private List<Transaction> transactions;
	private CheckingAccountCalculator testCalculator;

	@Mock
	Transaction transaction1;
	@Mock
	Transaction transaction2;
	@Mock
	Transaction transaction3;
	
	@Before
	public void setUp(){
		
		testCalculator = new CheckingAccountCalculator();
		
		transaction1 = Mockito.mock(Transaction.class);
		transaction2 = Mockito.mock(Transaction.class);
		transaction3 = Mockito.mock(Transaction.class);
		
		Mockito.when(transaction3.isMadeToday()).thenReturn(true);
		Mockito.when(transaction2.isMadeToday()).thenReturn(false);
		Mockito.when(transaction1.isMadeToday()).thenReturn(false);
		
		Mockito.when(transaction1.getDaysToNextTransaction(transaction2.getTransactionDateTime())).thenReturn((long) 2);
		Mockito.when(transaction2.getDaysToNextTransaction(transaction3.getTransactionDateTime())).thenReturn((long) 5);
		Mockito.when(transaction3.getDaysToNextTransaction(new GregorianCalendar())).thenReturn((long) 0);
		
		Mockito.when(transaction1.getTransactionAmount()).thenReturn((double) 300);
		Mockito.when(transaction2.getTransactionAmount()).thenReturn((double) -30);
		Mockito.when(transaction3.getTransactionAmount()).thenReturn((double) 400);
		
		transactions = new ArrayList<Transaction>();
		transactions.add(transaction1);
		transactions.add(transaction2);
		transactions.add(transaction3);
	}
	
		
	
	@Test
	public void calculateInterest_ShouldReturnInterestForCheckingAccount(){

		assertEquals(0.005339848603274187, testCalculator.calculateInterest(transactions), DOUBLE_DELTA);
		
	}

}
