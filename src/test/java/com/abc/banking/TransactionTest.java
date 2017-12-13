package com.abc.banking;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;

import com.abc.banking.Transaction;
import com.abc.utils.TestingUtils;

public class TransactionTest {
    
	@Test
    public void transactionShallBeCorrectlyInitializedByTheConstructor() {
		
		Transaction transaction = new Transaction(new BigDecimal("100.00"), LocalDate.of(2016, 11, 12), true);
		
		TestingUtils.assertEqualsBigDecimal(new BigDecimal("100.00"), transaction.getAmount());
		
		Assert.assertEquals(LocalDate.of(2016, 11, 12), transaction.getTransactionDate());
		
		Assert.assertEquals(true, transaction.isAccruedInterest());
    }
	
	@Test
    public void usingOneArgumentConstructorShallInitializeDefaultValues() {
		
		Transaction transaction = new Transaction(new BigDecimal("10000.00"));
		
		Assert.assertEquals(LocalDate.now(), transaction.getTransactionDate());
		
		Assert.assertEquals(false, transaction.isAccruedInterest());
    }
    
	@Test(expected = IllegalArgumentException.class)
	public void transactionShallNotAcceptNullAmount() {
		new Transaction(null, LocalDate.now(), true);
	}

	@Test(expected = IllegalArgumentException.class)
	public void transactionShallNotAcceptNullDate() {
		new Transaction(new BigDecimal("500.00"), null, true);
	}

	@Test
	public void twoInstancesOfTransactionsConstructedUsingTheSameConstructorArgumentsShallNotBeEqual() {
		Transaction t1 = new Transaction(new BigDecimal("500.00"), LocalDate.of(2016, 11, 12), true);
		Transaction t2 = new Transaction(new BigDecimal("500.00"), LocalDate.of(2016, 11, 12), true);
		
		Assert.assertNotEquals(t1, t2);
	}
	
	@Test
	public void twoInstancesOfTransactionsConstructedUsingDifferentConstructorArgumentsShallNotBeEqual() {
		Transaction t1 = new Transaction(new BigDecimal("500.00"), LocalDate.of(2016, 11, 12), true);
		Transaction t2 = new Transaction(new BigDecimal("50.00"), LocalDate.of(2015, 1, 9), false);
		
		Assert.assertNotEquals(t1, t2);
	}

	@Test
	public void equalsShallConformToTheContract() {
		Transaction transaction = new Transaction(new BigDecimal("100.00"), LocalDate.of(2016, 11, 12), true);
		
		// reflexive: for any non-null reference value x, x.equals(x) should return true.
		Assert.assertTrue(transaction.equals(transaction));
		
		// For any non-null reference value x, x.equals(null) should return false.
		Assert.assertFalse(transaction.equals(null));
		
		// The following rules are not tested, as each transaction shall be unique
		// It is symmetric: for any non-null reference values x and y, x.equals(y) should return true if and only if y.equals(x) returns true.
		// It is transitive: for any non-null reference values x, y, and z, if x.equals(y) returns true and y.equals(z) returns true, then x.equals(z) should return true.
		// It is consistent: for any non-null reference values x and y, multiple invocations of x.equals(y) consistently return true or consistently return false, provided no information used in equals comparisons on the objects is modified.
	}
	
	@Test
	public void transactionCreatedLaterWithSameFieldsExceptAmountShallBeGreaterWhenCompared() {
		Transaction t1 = new Transaction(new BigDecimal("500.00"), LocalDate.of(2016, 11, 12), true);
		Transaction t2 = new Transaction(new BigDecimal("500.00"), LocalDate.of(2016, 11, 12), true);
		
		Assert.assertTrue(t1.compareTo(t2) < 0);
		Assert.assertTrue(t2.compareTo(t1) > 0);
	}
	
	@Test
	public void transactionsShallCompareBasedOnTransactionDateFirst() {
		
		Transaction transactionSameDate1 = new Transaction(new BigDecimal("90000.00"), LocalDate.of(2016, 11, 12), true);
		Transaction transactionSameDate2 = new Transaction(new BigDecimal("40.00"), LocalDate.of(2016, 11, 12), false);
		Transaction transactionBeforeDate = new Transaction(new BigDecimal("500.00"), LocalDate.of(2014, 11, 12), false);
		Transaction transactionAfterDate = new Transaction(new BigDecimal("500.00"), LocalDate.of(2016, 11, 13), false);
		
		Assert.assertNotEquals(0, transactionSameDate1.compareTo(transactionSameDate2));
		
		Assert.assertTrue(transactionBeforeDate.compareTo(transactionSameDate1) < 0);

		Assert.assertTrue(transactionAfterDate.compareTo(transactionSameDate1) > 0);
	}
	
	@Test
	public void transactionsShallCompareAccruedInterestAsGreaterIfSameTransactionDate() {
		
		Transaction transactionAccruedInterestFlag1True = new Transaction(new BigDecimal("500.00"), LocalDate.of(2016, 11, 12), true);
		Transaction transactionAccruedInterestFlag2True = new Transaction(new BigDecimal("14.00"), LocalDate.of(2016, 11, 12), true);
		Transaction transactionAccruedInterestFlag1False = new Transaction(new BigDecimal("5010.00"), LocalDate.of(2016, 11, 12), false);
		Transaction transactionAccruedInterestFlag2False = new Transaction(new BigDecimal("1224.00"), LocalDate.of(2016, 11, 12), false);

		Assert.assertNotEquals(0, transactionAccruedInterestFlag1True.compareTo(transactionAccruedInterestFlag2True));

		Assert.assertNotEquals(0, transactionAccruedInterestFlag1False.compareTo(transactionAccruedInterestFlag2False));
		
		Assert.assertTrue(transactionAccruedInterestFlag1True.compareTo(transactionAccruedInterestFlag1False) > 0);

		Assert.assertTrue(transactionAccruedInterestFlag1False.compareTo(transactionAccruedInterestFlag1True) < 0);
	}
	
	@Test
	public void sameInstancesOfTransactionsShallCompareAsSame() {
		Transaction t = new Transaction(new BigDecimal("500.00"), LocalDate.of(2016, 11, 12), true);
		
		Assert.assertEquals(0, t.compareTo(t));
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void compareNullObjectShallFail() {
		Transaction t = new Transaction(new BigDecimal("500.00"), LocalDate.of(2016, 11, 12), true);
		
		t.compareTo(null);
	}
	
	@Test
	public void equalsShallCorrectlyReturnFalseIfNotCompatibleClasses() {
		Transaction t = new Transaction(new BigDecimal("500.00"), LocalDate.of(2016, 11, 12), true);
		Object o = new String();
		
		Assert.assertFalse(t.equals(o));
	}
	
	@Test
	public void transactionsShallNotEqualIfNotTheSameObjects() {
		Transaction t1 = new Transaction(new BigDecimal("500.00"), LocalDate.now(), true);
		Transaction t2 = new Transaction(new BigDecimal("500.00"), LocalDate.now(), false);
		
		Assert.assertNotEquals(t1, t2);
	}
	
	@Test
	public void transactionsShallEqualIfTheSameObjects() {
		Transaction t = new Transaction(new BigDecimal("500.00"));
		
		Assert.assertEquals(t, t);
	}

}
