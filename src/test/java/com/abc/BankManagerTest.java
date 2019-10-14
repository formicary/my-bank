package com.abc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class BankManagerTest {

	private static Bank abcBank;
	private static BankManager managerRobert;

	@BeforeClass
	public static void init() {
		Teller tellerAlison = new Teller("Alison");
		Customer customerMatt = new Customer("Matt");
		Customer customerNick = new Customer("Nick");

		managerRobert = new BankManager("Robert");
		abcBank = new Bank("ABC Bank", managerRobert, tellerAlison);

		// Make accounts for both customers
		// Account number: 0
		tellerAlison.openAccount(customerMatt, 0);
		// Account number: 1
		tellerAlison.openAccount(customerNick, 1);
		// Account number: 2
		tellerAlison.openAccount(customerMatt, 2);

		// Deposit money into each of the accounts
		tellerAlison.deposit(new BigDecimal(7500), 0);
		tellerAlison.deposit(new BigDecimal(500), 1);
		tellerAlison.deposit(new BigDecimal(5000), 2);
	}

	@Test
	public void notAnEmployee() {
		BankManager managerLuna = new BankManager("Luna");

		// Attempt to get a customer summary from the bank
		assertEquals("Luna does not belong to this bank.", managerLuna.getCustomerSummary());
	}

	@Test
	public void getSummary() {
		// Get a customer summary for the number of accounts
		assertEquals("CUSTOMER SUMMARY (ABC BANK):\n\n• Matt: 2 Accounts\n• Nick: 1 Account",
				managerRobert.getCustomerSummary());

		// Check that the bank holds the correct number of accounts
		assertTrue(abcBank.getAccounts().size() == 3);
	}

	@Test
	public void getTotalInterestPaidDay0() {
		// Get the total interest paid on all accounts and a final total when interest
		// has not been compounded once
		String expectedSummary = "TOTAL INTEREST PAID: (ABC BANK):\n" + "\n"
				+ "• Account Number: 0, Account Type: Checking Account\n" + "- Total Interest Paid: $0.00\n"
				+ "• Account Number: 2, Account Type: Maxi-Savings Account\n" + "- Total Interest Paid: $0.00\n"
				+ "• Account Number: 1, Account Type: Savings Account\n" + "- Total Interest Paid: $0.00\n" + "\n"
				+ "• Total Interest Paid On All Accounts: $0.00";

		assertEquals(expectedSummary, managerRobert.getTotalInterestPaid());

		// Check that no interest has been paid
		for (Account account : abcBank.getAccounts())
			assertTrue(account.getInterestPaid() == BigDecimal.ZERO);
	}

	@Test
	public void getTotalInterestPaid() {
		// Get the total interest paid on all account and a final total when interest
		// has been compounded 5 times
		String expectedSummary = "TOTAL INTEREST PAID: (ABC BANK):\n" + "\n"
				+ "• Account Number: 0, Account Type: Checking Account\n" + "- Total Interest Paid: $1.03\n"
				+ "• Account Number: 2, Account Type: Maxi-Savings Account\n" + "- Total Interest Paid: $3.43\n"
				+ "• Account Number: 1, Account Type: Savings Account\n" + "- Total Interest Paid: $0.07\n" + "\n"
				+ "• Total Interest Paid On All Accounts: $4.53";

		// Add five days to the current date
		DateProvider.getInstance().addDays(abcBank, 5);

		assertEquals(expectedSummary, managerRobert.getTotalInterestPaid());
	}

	@AfterClass
	public static void finalise() {
		abcBank = null;
		managerRobert = null;
	}

}
