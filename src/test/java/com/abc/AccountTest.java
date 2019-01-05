package com.abc;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

public class AccountTest {

	CheckingAccount checkingAccount = new CheckingAccount();
	private static final double DOUBLE_DELTA = 1e-15;
	DateProvider dp = new DateProvider();
	Date startDate = dp.tenDaysBeforeCurrentDate();
	Date endDate = dp.now();
	
	@Test(expected = IllegalArgumentException.class)
	public void negativeDepositTest() {
		
		checkingAccount.deposit(-1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void negativeWithdrawalTest() {
		
		checkingAccount.withdraw(-1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void withdrawalNotFeasible() {
		
		checkingAccount.deposit(50);
		checkingAccount.withdraw(500);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void compoundInterestError1() {
		checkingAccount.compoundInterest(endDate, startDate);
	}
	
	@Test
	public void compoundInterestSameDate() {
		checkingAccount.deposit(500);
		assertEquals(500,checkingAccount.compoundInterest(startDate, startDate), DOUBLE_DELTA);
	}
	
	@Test
	public void compoundInterest() {
		checkingAccount.deposit(500);
		
		assertEquals(52.31,checkingAccount.compoundInterest(startDate, endDate), DOUBLE_DELTA);
	}
	
	@Test 
	public void interestRounder() {
		
		assertEquals(50.25, checkingAccount.interestRounder(50.252), DOUBLE_DELTA);
		assertEquals(50.26, checkingAccount.interestRounder(50.256), DOUBLE_DELTA);
	}
}
