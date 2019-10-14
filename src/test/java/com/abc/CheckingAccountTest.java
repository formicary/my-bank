package com.abc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CheckingAccountTest {

	private static CheckingAccount checkingAccount;

	@BeforeClass
	public static void init() {
		checkingAccount = new CheckingAccount(0);
	}

	@Before
	public void reset() {
		// Reset bank balance
		checkingAccount.setBalance(BigDecimal.ZERO);

		// Reset interest paid
		checkingAccount.setInterestPaid(BigDecimal.ZERO);
	}

	@Test
	public void accountName() {
		assertEquals("Checking Account", checkingAccount.getAccountName());
	}

	@Test
	public void accountNumber() {
		// First account created in the program will have an account number of 0
		assertEquals(checkingAccount.getAccountNumber(), 0);
	}

	@Test
	public void interestRate() {
		// Interest rate for a Checking Account will always be 1%
		assertTrue(checkingAccount.getInterestRate() == 1);
	}

	@Test
	public void deposit() {
		BigDecimal depositAmount = new BigDecimal(25000);

		checkingAccount.deposit(depositAmount);

		// New balance should equal the deposit amount
		assertEquals(depositAmount, checkingAccount.getBalance());
	}

	@Test
	public void withdraw() {
		// Deposit money into the account first to ensure that money can be withdrawn
		BigDecimal depositAmount = new BigDecimal(7500);
		BigDecimal withdrawAmount = new BigDecimal(5000);

		checkingAccount.deposit(depositAmount);

		assertTrue(checkingAccount.withdraw(withdrawAmount));
		// New balance should be 7500 - 5000 = 2500
		assertEquals(depositAmount.subtract(withdrawAmount), checkingAccount.getBalance());
	}

	@Test
	public void overdraw() {
		// To be overdrawn, the balance has to be below 0
		BigDecimal overdrawAmount = BigDecimal.ONE;

		assertFalse(checkingAccount.withdraw(overdrawAmount));
	}

	@Test
	public void interestPaid() {
		BigDecimal depositAmount = new BigDecimal(7500);
		BigDecimal interestPaid = new BigDecimal(1.03).setScale(2, RoundingMode.HALF_EVEN);

		checkingAccount.deposit(depositAmount);

		assertEquals(BigDecimal.ZERO, checkingAccount.getInterestPaid());

		// Add five days to the current date
		DateProvider.getInstance().addDays(5);

		checkingAccount.dailyTasks();

		// The interest rate paid after five days should equal $1.03
		assertEquals(interestPaid, checkingAccount.getInterestPaid());
		// The total balance should now be 1.03 + 7500 = 7501.03
		assertEquals(depositAmount.add(interestPaid), checkingAccount.getBalance());
	}

	@AfterClass
	public static void finalise() {
		checkingAccount = null;
	}

}
