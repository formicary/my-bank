package com.abc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SavingsAccountTest {

	private static SavingsAccount savingsAccount;

	@BeforeClass
	public static void init() {
		savingsAccount = new SavingsAccount(0);
	}

	@Before
	public void reset() {
		// Reset bank balance
		savingsAccount.setBalance(BigDecimal.ZERO);

		// Reset interest paid
		savingsAccount.setInterestPaid(BigDecimal.ZERO);

		// Reset number of days
		DateProvider.getInstance().setCurrentDate(LocalDate.now());
	}

	@Test
	public void accountName() {
		assertEquals("Savings Account", savingsAccount.getAccountName());
	}

	@Test
	public void accountNumber() {
		// First account created in the program will have an account number of 0
		assertEquals(0, savingsAccount.getAccountNumber());
	}

	@Test
	public void interestRate() {
		// The base interest rate for a Savings Account will always be 1%
		assertTrue(savingsAccount.getInterestRate() == 1);
	}

	@Test
	public void deposit() {
		BigDecimal depositAmount = new BigDecimal(25000);

		savingsAccount.deposit(depositAmount);

		// New balance should equal the deposit amount
		assertEquals(depositAmount, savingsAccount.getBalance());
	}

	@Test
	public void withdraw() {
		// Deposit money into the account first to ensure that money can be withdrawn
		BigDecimal depositAmount = new BigDecimal(7500);
		BigDecimal withdrawAmount = new BigDecimal(5000);

		savingsAccount.deposit(depositAmount);

		assertTrue(savingsAccount.withdraw(withdrawAmount));
		// New balance should be 7500 - 5000 = 2500
		assertEquals(depositAmount.subtract(withdrawAmount), savingsAccount.getBalance());
	}

	@Test
	public void overdraw() {
		// To be overdrawn, the balance has to be below 0
		BigDecimal overdrawAmount = BigDecimal.ONE;

		assertFalse(savingsAccount.withdraw(overdrawAmount));
	}

	@Test
	public void lowAmountInterestPaid() {
		BigDecimal depositAmount = new BigDecimal(500);
		// Compound interest rate for the current balance should equal $0.07
		BigDecimal interestPaid = new BigDecimal(0.07).setScale(2, RoundingMode.HALF_EVEN);

		savingsAccount.deposit(depositAmount);

		assertEquals(BigDecimal.ZERO, savingsAccount.getInterestPaid());

		// Add five days to the current date
		DateProvider.getInstance().addDays(5);

		savingsAccount.dailyTasks();

		assertEquals(interestPaid, savingsAccount.getInterestPaid());
	}

	@Test
	public void highAmountInterestPaid() {
		BigDecimal depositAmount = new BigDecimal(1500);
		// Compound interest rate for the current balance should equal $0.28
		BigDecimal interestPaid = new BigDecimal(0.28).setScale(2, RoundingMode.HALF_EVEN);

		savingsAccount.deposit(depositAmount);

		assertEquals(BigDecimal.ZERO, savingsAccount.getInterestPaid());

		// Add five days to the current date
		DateProvider.getInstance().addDays(5);

		savingsAccount.dailyTasks();

		assertEquals(interestPaid, savingsAccount.getInterestPaid());
		assertEquals(depositAmount.add(interestPaid), savingsAccount.getBalance());
	}

	@AfterClass
	public static void finalise() {
		savingsAccount = null;
	}

}
