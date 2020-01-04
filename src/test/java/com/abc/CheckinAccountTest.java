package com.abc;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CheckinAccountTest {
	
	private static final double DOUBLE_DELTA = 1e-15;

	@Test
	// Check if the interest for a CHECKING account is correctly accrued and
	// compounded daily.
	public void checkingAccountTest() {
		Bank bank = new Bank();
		Customer bill = new Customer("Bill", "Barber", 2);
		bill.openAccount("CHECKING");
		bank.addCustomer(bill);
		bill.getAccounts().get(0).deposit(100.0);
		bill.getAccounts().get(0).deposit(900.0);
		bill.getAccounts().get(0).withdraw(500.0);
		// After 1 day.
		bill.getAccounts().get(0).depositTest(100.0, (new DateHelper()).changeDay(1));
		// After 10 days.
		bill.getAccounts().get(0).depositTest(100.0, (new DateHelper()).changeDay(10));
		// 0.1% * 500 * 1 day + 0.1% * 600 * 9 days + 0.1% * 700 * 1 day = 0.5 + 0.7 +
		// 5.4 = 6.6
		assertEquals(6.6, bill.getAccounts().get(0).getTotalInterestEarned(), DOUBLE_DELTA);
	}

}
