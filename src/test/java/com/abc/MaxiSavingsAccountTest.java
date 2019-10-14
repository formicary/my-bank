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

public class MaxiSavingsAccountTest {

	private static MaxiSavingsAccount maxiSavingsAccount;

	@BeforeClass
	public static void init() {
		maxiSavingsAccount = new MaxiSavingsAccount(0);
	}

	@Before
	public void reset() {
		// Reset bank balance
		maxiSavingsAccount.setBalance(BigDecimal.ZERO);

		// Reset interest paid
		maxiSavingsAccount.setInterestPaid(BigDecimal.ZERO);

		// Reset number of days
		DateProvider.getInstance().setCurrentDate(LocalDate.now());

		// Set number of days without withdrawal to 5. This is to indicate that no
		// withdrawal has been made prior to each test
		maxiSavingsAccount.setDaysWithoutWithdrawal(10);

		// Reset interest rate
		maxiSavingsAccount.setInterestRate(5);
	}

	@Test
	public void accountName() {
		assertEquals("Maxi-Savings Account", maxiSavingsAccount.getAccountName());
	}

	@Test
	public void accountNumber() {
		// First account created in the program will have an account number of 0
		assertEquals(0, maxiSavingsAccount.getAccountNumber());
	}

	@Test
	public void interestRateNoWithdrawal() {
		// Interest rate for a Maxi-Savings Account will be 5% until a withdrawal takes
		// place
		assertTrue(maxiSavingsAccount.getInterestRate() == 5);
	}

	@Test
	public void interestRateWithdrawal() {
		BigDecimal depositAmount = new BigDecimal(7500);
		BigDecimal withdrawAmount = new BigDecimal(5000);

		assertTrue(maxiSavingsAccount.getInterestRate() == 5);

		maxiSavingsAccount.deposit(depositAmount);
		maxiSavingsAccount.withdraw(withdrawAmount);

		// Withdrawal has occurred, therefore the interest rate is no 1%
		assertTrue(maxiSavingsAccount.getInterestRate() == 1);
	}

	@Test
	public void interestRateWithdrawalTenDays() {
		BigDecimal depositAmount = new BigDecimal(7500);
		BigDecimal withdrawAmount = new BigDecimal(5000);

		assertTrue(maxiSavingsAccount.getInterestRate() == 5);

		maxiSavingsAccount.deposit(depositAmount);
		maxiSavingsAccount.withdraw(withdrawAmount);

		assertTrue(maxiSavingsAccount.getInterestRate() == 1);

		// Add 10 days to the current date
		DateProvider.getInstance().addDays(10);

		maxiSavingsAccount.dailyTasks();

		// After 10 days without a withdrawal, the interest rate resets to 5%
		assertTrue(maxiSavingsAccount.getInterestRate() == 5);
	}

	@Test
	public void deposit() {
		BigDecimal depositAmount = new BigDecimal(25000);

		maxiSavingsAccount.deposit(depositAmount);

		// New balance should equal the deposit amount
		assertEquals(depositAmount, maxiSavingsAccount.getBalance());
	}

	@Test
	public void withdraw() {
		// Deposit money into the account first to ensure that money can be withdrawn
		BigDecimal depositAmount = new BigDecimal(7500);
		BigDecimal withdrawAmount = new BigDecimal(5000);

		maxiSavingsAccount.deposit(depositAmount);

		assertTrue(maxiSavingsAccount.withdraw(withdrawAmount));
		// New balance should be 7500 - 5000 = 2500
		assertEquals(depositAmount.subtract(withdrawAmount), maxiSavingsAccount.getBalance());
	}

	@Test
	public void overdraw() {
		// To be overdrawn, the balance has to be below 0
		BigDecimal overdrawAmount = BigDecimal.ONE;

		assertFalse(maxiSavingsAccount.withdraw(overdrawAmount));
	}

	@Test
	public void interestPaidNoWithdrawal() {
		BigDecimal depositAmount = new BigDecimal(7500);
		BigDecimal interestPaid = new BigDecimal(5.14).setScale(2, RoundingMode.HALF_EVEN);

		maxiSavingsAccount.deposit(depositAmount);

		assertEquals(BigDecimal.ZERO, maxiSavingsAccount.getInterestPaid());

		// Add five days to the current date
		DateProvider.getInstance().addDays(5);

		maxiSavingsAccount.dailyTasks();

		// The interest rate paid after five days should equal $5.14
		assertEquals(interestPaid, maxiSavingsAccount.getInterestPaid());
		// The total balance should now be 5.14 + 7500 = 7505.14
		assertEquals(depositAmount.add(interestPaid), maxiSavingsAccount.getBalance());
	}

	@Test
	public void interestPaidWithdrawal() {
		BigDecimal depositAmount = new BigDecimal(8000);
		BigDecimal withdrawAmount = new BigDecimal(500);
		BigDecimal interestPaid = new BigDecimal(1.03).setScale(2, RoundingMode.HALF_EVEN);

		maxiSavingsAccount.deposit(depositAmount);
		maxiSavingsAccount.withdraw(withdrawAmount);
		// Save the previous balance before interest is compounded
		BigDecimal previousBalance = maxiSavingsAccount.getBalance();

		assertEquals(BigDecimal.ZERO, maxiSavingsAccount.getInterestPaid());

		// Add five days to the current date
		DateProvider.getInstance().addDays(5);

		maxiSavingsAccount.dailyTasks();

		// The interest rate paid after five days should equal $1.03
		assertEquals(interestPaid, maxiSavingsAccount.getInterestPaid());
		// The total balance should now be 1.03 + 7500 = 7501.03
		assertEquals(previousBalance.add(interestPaid), maxiSavingsAccount.getBalance());
	}

	@AfterClass
	public static void finalise() {
		maxiSavingsAccount = null;
	}

}
