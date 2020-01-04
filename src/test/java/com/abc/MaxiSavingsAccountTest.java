package com.abc;

import org.junit.Test;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import java.util.Date;

public class MaxiSavingsAccountTest {
	
	private static final double DOUBLE_DELTA = 1e-10;

	@Test
	// Check if the interest for a MAXI SAVINGS account is correctly accrued and
	// compounded daily.
	public void maxiSavingsAccountTest() {
		Bank bank = new Bank();
		Customer bill = new Customer("Bill", "Barber", 2);
		bill.openAccount("MAXI SAVINGS");
		bank.addCustomer(bill);
		bill.getAccounts().get(0).deposit(1000.0);
		// After 1 day.
		bill.getAccounts().get(0).depositTest(1000.0, (new DateHelper()).changeDay(1));
		// After 12 days.
		bill.getAccounts().get(0).withdrawTest(1000.0, (new DateHelper()).changeDay(12));
		// After 13 days.
		bill.getAccounts().get(0).depositTest(1000.0, (new DateHelper()).changeDay(13));
		// 0.1% * 1000 * 1 day + (0.1% * 2000 * 10 days + 5% * 2000 * 1 days)
		// + (0.1% * 1000) * 1 day + (0.1% * 2000) * 1 day = 1 + 120 + 1 + 2 = 124
		assertEquals(124.0, bill.getAccounts().get(0).getTotalInterestEarned(), DOUBLE_DELTA);
	}

}
