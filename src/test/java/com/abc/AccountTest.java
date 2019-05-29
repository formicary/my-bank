package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class AccountTest {
	
    private static final double DOUBLE_DELTA = 1e-15;
    private static final TestOnlyCurrentTime testOnlyCurrentTime = TestOnlyCurrentTime.getInstance();
	
	@Test
	public void deposit_PositiveAmountGiven_ShouldReturnIncreasedBalance() {
		Account account = new Account(1);
		account.deposit(5);
		
		assertEquals(5.0, account.getBalance(),DOUBLE_DELTA);
	}

	@Test(expected = IllegalArgumentException.class) 
	public void deposit_NegativeAmountGiven_ShouldThrowIllegalArgumentException() {
		Account account = new Account(1);
		account.deposit(-5);
	}
	
	@Test
	public void withdraw_PositiveAmountGiven_ShouldReturnReducedBalance() {
		Account account = new Account(1);
		account.deposit(5);
		account.withdraw(3);
		
		assertEquals(2.0, account.getBalance(),DOUBLE_DELTA);
	}

	@Test(expected = IllegalArgumentException.class) 
	public void withdraw_NegativeAmountGiven_ShouldThrowIllegalArgumentException() {
		Account account = new Account(1);
		account.deposit(5);
		account.withdraw(-2);
	}
	
	@Ignore
	public void updateTestOnlyCurrentTime_GivenUpdatedDate_DateProviderReturnUpdatedDate() {
		Calendar myCalendar = new GregorianCalendar(2014, 2, 11);
		Date date = myCalendar.getTime();
		testOnlyCurrentTime.setDate(date);
		
		Date originalDate = DateProvider.getInstance().now();
		
		Calendar myCalendar2 = new GregorianCalendar(2015, 2, 11);
		Date date2 = myCalendar2.getTime();
		testOnlyCurrentTime.setDate(date2);
		
		Date updatedDate = DateProvider.getInstance().now();
		assertNotEquals(originalDate,updatedDate);
	}


}
