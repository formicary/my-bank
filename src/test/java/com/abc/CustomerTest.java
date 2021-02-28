package com.abc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CustomerTest {
	private static final double DOUBLE_DELTA = 1e-15;

	@Test // Test customer statement generation
	public void testAppCheckingType(){

        Account checkingAccount = new CheckingAccount();

        Customer henry = new Customer("Henry").openAccount(checkingAccount);

        checkingAccount.deposit(100.0);

        assertEquals(" Checking Account", henry.typeForAccount(checkingAccount));
    }
	
	@Test
	public void testToDollar() {
		Customer joe = new Customer("Joe");
		assertEquals("$100.00", joe.toDollars(100.0));
	}
	
	@Test // Test customer statement generation
	public void testAppSaving(){
		
		Account savingsAccount = new SavingsAccount();
		
		Customer henry = new Customer("Henry").openAccount(savingsAccount);
		
		savingsAccount.deposit(500.0);
		assertEquals(
				"  deposit $500.00\n" +
				"Total $500.00\n"
				, henry.statementForAccount(savingsAccount));
	}
	
	@Test // Test customer statement generation
	public void testAppMessage() {
		Account checkingAccount = new CheckingAccount();
		Account savingsAccount = new SavingsAccount();
		Account maxiSavingsAccount = new MaxiSavingsAccount();
		
		Customer henry = new Customer("Henry");
				henry.openAccount(checkingAccount)
				.openAccount(savingsAccount)
				.openAccount(maxiSavingsAccount);
		
		checkingAccount.deposit(100.0);
		savingsAccount.deposit(4000.0);
		savingsAccount.withdraw(200.0);
		maxiSavingsAccount.deposit(3000.0);
		
		String statement = "Statement for Henry\n"
				+ " Checking Account\n"
				+ "  deposit $100.00\n"
				+ "Total $100.00\n"
				+ "\n" 
				+ " Savings Account\n"
				+ "  deposit $4,000.00\n"
				+ "  withdrawal $200.00\n"
				+ "Total $3,800.00\n"
				+ "\n" 
				+ " Maxi Savings Account\n"
				+ "  deposit $3,000.00\n"
				+ "Total $3,000.00\n"
				+ "\n"
				+ " Total In All Accounts $6,900.00";
		
		assertEquals(statement, henry.getStatement());
		
	}

	@Test
	public void testOneAccount() {
		Customer oscar = new Customer("Oscar").openAccount(new SavingsAccount());
		assertEquals(1, oscar.getNumberOfAccounts());
	}

	@Test
	public void testTwoAccounts() {
		Customer oscar = new Customer("Oscar").openAccount(new SavingsAccount());
		oscar.openAccount(new CheckingAccount());
		assertEquals(2, oscar.getNumberOfAccounts());
	}

	@Test
	public void testThreeAccounts() {
		Customer oscar = new Customer("Oscar").openAccount(new SavingsAccount());
		oscar.openAccount(new CheckingAccount());
		oscar.openAccount(new MaxiSavingsAccount());
		assertEquals(3, oscar.getNumberOfAccounts());
	}

	@Test
	public void testTransferAmount() {
		Account checkingAccount = new CheckingAccount();
		Account savingsAccount = new SavingsAccount();

		Customer joe = new Customer("Joe").openAccount(checkingAccount).openAccount(savingsAccount);

		checkingAccount.deposit(1000.0);
		savingsAccount.deposit(4000.0);

		assertEquals("Transfer completed", joe.transferBetweenAccounts(checkingAccount, savingsAccount, 500));
		assertEquals(500.0, checkingAccount.getBalance(), DOUBLE_DELTA);
		assertEquals(4500.0, savingsAccount.getBalance(), DOUBLE_DELTA);
	}

	@Test
	public void testTransferFailed() {
		Account checkingAccount = new CheckingAccount();
		Account savingsAccount = new SavingsAccount();

		Customer joe = new Customer("Joe").openAccount(checkingAccount).openAccount(savingsAccount);

		checkingAccount.deposit(1000.0);
		savingsAccount.deposit(4000.0);

		assertEquals("amount must be greater than zero and accounts must be exists.",
				joe.transferBetweenAccounts(checkingAccount, savingsAccount, 0));
		assertEquals(1000.0, checkingAccount.getBalance(), DOUBLE_DELTA);
		assertEquals(4000.0, savingsAccount.getBalance(), DOUBLE_DELTA);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testdwithdrawFailed() {
		Account checkingAccount = new CheckingAccount();
		Customer henry = new Customer("Henry");
				henry.openAccount(checkingAccount);
		checkingAccount.deposit(100.0);
		checkingAccount.withdraw(200);
	}
	
	@Test
	public void testCheckAmount() {
		Account checkingAccount = new CheckingAccount();
		Customer henry = new Customer("Henry");
				henry.openAccount(checkingAccount);
		checkingAccount.deposit(100.0);
		assertTrue(checkingAccount.checkAmount(0.0));
		assertTrue(checkingAccount.checkAmount(200.0));
	}
	
	@Test
	public void testTransactionType() {
		Account checkingAccount = new CheckingAccount();
		Customer henry = new Customer("Henry").openAccount(checkingAccount);
		checkingAccount.deposit(100.0);
		assertEquals(checkingAccount.getAccountType(), henry.getAccounts().get(0).transactions.get(0).getAccountType());
	}
	
	@Test
	public void testInterestEarnedSavings() {
		Account savingsAccount = new SavingsAccount();
		Customer henry = new Customer("Henry").openAccount(savingsAccount);
		savingsAccount.deposit(100.0);
		assertEquals(0.1, savingsAccount.interestEarned(), DOUBLE_DELTA);
	}
	
	@Test
	public void testInterestEarnedMaxi() {
		Account savingsAccount = new MaxiSavingsAccount();
		Customer henry = new Customer("Henry").openAccount(savingsAccount);
		savingsAccount.deposit(1000.0);
		savingsAccount.withdraw(900);
		assertEquals(0.1, savingsAccount.interestEarned(), DOUBLE_DELTA);
	}
}
