package com.abc;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class AccountTest {
	
    private static final double DOUBLE_DELTA = 1e-15;
    private static final TestOnlyCurrentTime testOnlyCurrentTime = TestOnlyCurrentTime.getInstance();
	
    @Before
    public void init() {
		Date date = Calendar.getInstance().getTime();
		testOnlyCurrentTime.setDate(date);
    }
    
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
	
	@Test
	public void withdraw_WithdrawMoreThanBalance_ShouldReturnNegativeBalance() {
		Account account = new Account(1);
		account.deposit(5);
		account.withdraw(10);
		
		assertEquals(-5.0, account.getBalance(),DOUBLE_DELTA);
	}

	@Test(expected = IllegalArgumentException.class) 
	public void withdraw_NegativeAmountGiven_ShouldThrowIllegalArgumentException() {
		Account account = new Account(1);
		account.deposit(5);
		account.withdraw(-2);
	}
	
	@Test
	public void updateTestOnlyCurrentTime_GivenUpdatedDate_DateProviderReturnUpdatedDate() {
		Calendar calendar = new GregorianCalendar(2014, 2, 11);
		testOnlyCurrentTime.setDate(calendar.getTime());
		
		Date originalDate = DateProvider.getInstance().now();
		
		calendar = new GregorianCalendar(2015, 2, 11);
		testOnlyCurrentTime.setDate(calendar.getTime());
		
		Date updatedDate = DateProvider.getInstance().now();
		assertNotEquals(originalDate,updatedDate);
	}
	
	@Test
	public void deposit_OneDepositOneYearAgoGiven_ShouldReturnBalanceWithInterest() {
		Account account = new Account(0);
		
		Calendar calendar = new GregorianCalendar(2018, 5, 30);
		testOnlyCurrentTime.setDate(calendar.getTime());
		
		account.deposit(5);
		
		calendar = new GregorianCalendar(2019, 5, 30);
		testOnlyCurrentTime.setDate(calendar.getTime());
		
		assertEquals(5.01, account.getBalance(),DOUBLE_DELTA);
	}


}
