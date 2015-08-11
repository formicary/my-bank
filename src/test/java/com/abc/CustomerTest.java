package com.abc;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

public class CustomerTest {

	@Test //Test customer statement generation. Also tests negative withdrawals and deposits (functioning properly).
	public void testApp() {
		Account checkingAccount = new CheckingAccount(100);
		Account savingsAccount = new SavingsAccount(101);

		Customer tess = new Customer("Tess Tap", 10).openAccount(checkingAccount).openAccount(savingsAccount);

		checkingAccount.deposit(100.00);
		savingsAccount.deposit(4000.0);
		savingsAccount.deposit(-100.0);
		savingsAccount.withdraw(200.0);
		savingsAccount.withdraw(-100.00);

		String assertion = "Statement for Tess Tap\n" +
				"\n" +
				"Checking Account\n" +
				"  deposit $100.00\n" +
				"Total $100.00\n" +
				"\n" +
				"Savings Account\n" +
				"  deposit $4,000.00\n" +
				"  withdrawal $200.00\n" +
				"Total $3,800.00\n" +
				"\n" +
				"Total In All Accounts $3,900.00";
		
		assertEquals("\n\n===== testApp() statement not as expected =====\n\n", assertion, tess.getStatement());
	}

	/*
	 * tests normal withdraw and deposit results, as well as attempting to withdraw more money than a customer has.
	 */
	@Test
	public void testBalance() {
		Account checkingAccount = new CheckingAccount(100);
		Account savingsAccount = new SavingsAccount(101);

		Customer belle = new Customer("Belle Ance", 10).openAccount(checkingAccount).openAccount(savingsAccount);

		checkingAccount.deposit(1000.93);
		checkingAccount.withdraw(2000.00);

		checkingAccount.withdraw(34.34);
		checkingAccount.withdraw(1.12);
		checkingAccount.withdraw(14.96);
		
		String assertion = "Statement for Belle Ance\n" +
				"\n" +
				"Checking Account\n" +
				"  deposit $1,000.93\n" +
				"  withdrawal $34.34\n" +
				"  withdrawal $1.12\n" +
				"  withdrawal $14.96\n" +
				"Total $950.51\n" +
				"\n" +
				"Savings Account\n" +
				"Total $0.00\n" +
				"\n" +
				"Total In All Accounts $950.51";
		
		assertEquals("\n\nAccounts differ in testBalance()\n", assertion, belle.getStatement());
	}
	
	/*
	 * Tests my own transfer function. Test fails if 10.0 in tess.transfer is uncommented (as expected).
	 */
	@Test
	public void testTransfer() {
		Customer tess = new Customer("Tess Trents-Far", 10);
		Account ca = new CheckingAccount(100);
		Account sa = new SavingsAccount(101);
		
		Double amount = 100.00;
		tess.openAccount(ca).openAccount(sa);
		ca.deposit(amount);
		
		tess.transfer(100, 101, amount/*+10.0*/);
		tess.deposit(200.00, 100);
				
		assertEquals("Transfer failed.\n", "100.0", sa.getBalance().toString());
	}
	
	/**
	 * This method replaces the testOne-, testTwo- and testThreeAccounts in the original code.
	 * It allows streamlined testing of any number of accounts by changing the numAccs variable.
	 */
	@Ignore
	public void testAccounts(){
		int numAccs = 3;
		Customer tess = new Customer("Tess Tackounce", 10);
		for (int i = 0; i < numAccs; i++) {
			tess.openAccount(new CheckingAccount(i));
		}
		
		assertEquals("\n\ntestOneAccount - number of accounts opened is incorrect\n\n",
				numAccs, tess.getNumberOfAccounts());
	}
}
