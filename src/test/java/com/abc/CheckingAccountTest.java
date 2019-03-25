package com.abc;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CheckingAccountTest {

	private Checking_Account acc;

	@Before
	public void setUp() throws Exception {
		acc = new Checking_Account(TestConstants.CHECKING_ACCOUNT, TestConstants.FIRST_ACCOUNT_ID);
	}

	@Test
	public void testDeposit() {
		assertEquals(TestConstants.TRUE, acc.getDepositList().isEmpty());

		acc.deposit(TestConstants.DEPOSIT_AMOUNT);
		assertEquals(TestConstants.DEPOSIT_AMOUNT, acc.getDepositList().get(TestConstants.ZERO).getAmount());
		assertEquals(TestConstants.BALANCE_AFTER_DEPOSIT, acc.getBalance());
		
		acc.deposit(TestConstants.DEPOSIT_AMOUNT2);
		assertEquals(TestConstants.DEPOSIT_AMOUNT2, acc.getDepositList().get(TestConstants.ONE).getAmount());

		 //assertEquals(TestConstants.ACCOUNT_DEPOSIT_ILLEGAL_ARGUMENT_EXCEPTION, acc.deposit(TestConstants.NEGATIVE_AMOUNT));
	}

	public void testWithdrawal() {
		assertEquals(TestConstants.TRUE, acc.getWithdrawalList().isEmpty());

		assertEquals(TestConstants.INSUFFICIENT_FUNDS, acc.withdraw(TestConstants.WITHDRAWAL_AMOUNT));
		assertEquals(TestConstants.STARTING_BALANCE, acc.getBalance());
		
		acc.deposit(TestConstants.DEPOSIT_AMOUNT);
		assertEquals(TestConstants.SUCCESSFUL_WITHDRAWAL, acc.withdraw(TestConstants.WITHDRAWAL_AMOUNT));
		assertEquals(TestConstants.STARTING_BALANCE, acc.getBalance());
		
		assertEquals(TestConstants.ACCOUNT_WITHDRAW_ILLEGAL_ARGUMENT_EXCEPTION,
				acc.withdraw(TestConstants.NEGATIVE_AMOUNT));
	}

	public void testRecordDeposit() {
		assertEquals(TestConstants.TRUE, acc.getDepositList().isEmpty());

		acc.recordDeposit(TestConstants.DEPOSIT_AMOUNT);
		assertEquals(TestConstants.DEPOSIT_AMOUNT, acc.getDepositList().get(TestConstants.ZERO).getAmount());
	}

	public void testRecordWithdrawal() {
		assertEquals(TestConstants.TRUE, acc.getWithdrawalList().isEmpty());

		acc.recordWithdrawal(TestConstants.WITHDRAWAL_AMOUNT);
		assertEquals(TestConstants.WITHDRAWAL_AMOUNT, acc.getWithdrawalList().get(TestConstants.ZERO).getAmount());
	}
	
	public void testInterestEarned() {
		assertEquals(TestConstants.ZERO_BD, acc.interestEarned());
		
		acc.deposit(TestConstants.DEPOSIT_AMOUNT);
		assertEquals(TestConstants.CHECKING_ACCOUNT_INTEREST_EARNED, acc.interestEarned());
	}

	@After
	public void tearDown() throws Exception {
		acc = null;
	}
}
