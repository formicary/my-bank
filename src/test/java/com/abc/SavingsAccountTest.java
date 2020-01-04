package com.abc;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class SavingsAccountTest {
	
	private static final double DOUBLE_DELTA = 1e-15;

	@Test
	// Check if the interest for a SAVINGS account is correctly accrued and
	// compounded daily.
	public void savingsAccountTest() {
		Bank bank = new Bank();
		Customer bill = new Customer("Bill", "Barber", 2);
		bill.openAccount("SAVINGS");
		bank.addCustomer(bill);
		bill.getAccounts().get(0).deposit(500.0);
		// After 1 day.
		bill.getAccounts().get(0).depositTest(1000.0, (new DateHelper()).changeDay(1));
		// After 10 days.
		bill.getAccounts().get(0).depositTest(1000.0, (new DateHelper()).changeDay(10));
		// 0.1% * 500 * 1 day + ( 0.1% * 1000 + 0.2% * 500 ) * 9 days + ( 0.1% * 1000 +
		// 0.2% * 1500 ) * 1 day = 0.5 + 18 + 4 = 22.5
		assertEquals(22.5, bill.getAccounts().get(0).getTotalInterestEarned(), DOUBLE_DELTA);
	}

}
