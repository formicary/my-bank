package com.abc;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

public class MaxiSavingsAccountTest {

	static MaxiSavingsAccount testAccount;
	static final double delta = 1e-15;
		
	@Before
	public void setUpBeforeEach() {
		testAccount = new MaxiSavingsAccount();
	}
	
	@Test
	public void negativeDepositTest() {
		try {
			testAccount.deposit(-1);
			fail("Cannot have a negative deposit");
		}
		catch(IllegalArgumentException e) {
			assertEquals("Amount must be greater than zero", e.getMessage());
		}
	}
	
	@Test
	public void zeroDepositTest() {
		try {
			testAccount.deposit(0);
			fail("Cannot have a zero deposit");
		}
		catch(IllegalArgumentException e) {
			assertEquals("Amount must be greater than zero", e.getMessage());
		}
	}
	
	@Test
	public void negativeWithdrawalTest() {
		try {
			testAccount.withdraw(-1);
			fail("Cannot have a negative withdrawal");
		}
		catch(IllegalArgumentException e) {
			assertEquals("Amount must be greater than zero", e.getMessage());
		}
	}
	
	@Test
	public void zeroWithdrawalTest() {
		try {
			testAccount.withdraw(0);
			fail("Cannot have a zero withdraw");
		}
		catch(IllegalArgumentException e) {
			assertEquals("Amount must be greater than zero", e.getMessage());
		}
	}
	
	@Test
	public void noWithdrawalInterestTest1() {
		testAccount.deposit(1000);
		assertEquals(50, testAccount.getInterest(), delta);
	}
	
	@Test
	public void recentWithdrawalInterestTest1() {
		testAccount.deposit(1100);
		testAccount.withdraw(100);
		assertEquals(1, testAccount.getInterest(), delta);
	}
	
	@Test
	public void nineDayWithdrawalInterestTest1() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -9);
		Date withdrawDate = cal.getTime();
		testAccount.deposit(1100, withdrawDate);
		testAccount.withdraw(100, withdrawDate);
		assertEquals(1, testAccount.getInterest(), delta);
	}
	
	@Test
	public void tenDayWithdrawalInterestTest1() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -10);
		Date withdrawDate = cal.getTime();
		testAccount.deposit(1100, withdrawDate);
		testAccount.withdraw(100, withdrawDate);
		assertEquals(50, testAccount.getInterest(), delta);
	}
	
	@Test
	public void statementTest() {
		testAccount.deposit(1000);
		testAccount.withdraw(100);
		testAccount.deposit(100);
		String expStr = "Maxi-Savings Account\n" +
              "\n  Deposit $1000.00" +
              "\n  Withdrawal $100.00" +
              "\n  Deposit $100.00" +
              "\nTotal: $1000.00";
		assertEquals(expStr, testAccount.getStatement());
	}

}
