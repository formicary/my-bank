package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

public class CustomerTest {

	@Test //Test Customer Constructor
	public void customer_MakeNewCustomer_NewCustomerInstance() {
		Customer amy = new Customer("Amy");
		assertTrue(amy instanceof Customer);
	}
	
	@Test //Test getName method
	public void getName_CustomerNameProvided_ReturnName() {
		Customer amy = new Customer("Amy");
		assertEquals("Amy", amy.getName());
	}
	
	@Test //Test openAccount method with one account
	public void openAccount_OneAccountOpened_CustomerInstanceReturned() {
		Account checkingAccount = new Account(Account.Type.CHECKING);
		
        Customer henry = new Customer("Henry").openAccount(checkingAccount);
        assertTrue(henry instanceof Customer);
	}
	
	@Test //Test openAccount method with two accounts
	public void openAccount_TwoAccountsOpened_CustomerInstanceReturned() {
		Account checkingAccount = new Account(Account.Type.CHECKING);
        Account savingsAccount = new Account(Account.Type.SAVINGS);

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);
        assertTrue(henry instanceof Customer);
	}
	
	@Test //Test getNumberOfAccounts method with 0 accounts
	public void getNumberOfAccounts_NoAccounts_ReturnZero() {
		Customer henry = new Customer("Henry");
		assertEquals(0,henry.getNumberOfAccounts());
	}
	
	@Test //Test getNumberOfAccounts method with 1 account
	public void getNumberOfAccounts_OneAccounts_ReturnOne() {
		Account checkingAccount = new Account(Account.Type.CHECKING);
		Customer henry = new Customer("Henry").openAccount(checkingAccount);
		assertEquals(1,henry.getNumberOfAccounts());
	}
	
	@Test //Test accountTransfer method
	public void accountTransfer_TransferBetweenAccounts_AmountTransferred() {
		Account checkingAccount = new Account(Account.Type.CHECKING);
        Account savingsAccount = new Account(Account.Type.SAVINGS);

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(300.0);
        savingsAccount.deposit(400.0);
        
        henry.accountTransfer(200.0, checkingAccount, savingsAccount);
        assertEquals(100.0, checkingAccount.sumTransactions(), 0.00);
        assertEquals(600.0, savingsAccount.sumTransactions(), 0.00);
	}
	
	@Test //Test totalInterestEarned method for one account
	public void totalInterestEarned_OneAccount_ReturnAccountInterest() {
		Account checkingAccount = new Account(Account.Type.CHECKING);
        Customer henry = new Customer("Henry").openAccount(checkingAccount);
        
        LocalDate date = LocalDate.of(2017,06,01);
		Transaction deposit = new Transaction(200.0, date.atStartOfDay());
		checkingAccount.addTransaction(deposit);
		
		double expectedInterest = checkingAccount.totalInterest();
        assertEquals(expectedInterest, henry.totalInterestEarned(), 0.0000001);
	}
	
	@Test //Test totalInterestEarned method for one account
	public void totalInterestEarned_TwoAccount_ReturnAccountInterest() {
		Account checkingAccount = new Account(Account.Type.CHECKING);
		Account maxiSavingsAccount = new Account(Account.Type.MAXI_SAVINGS);
        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(maxiSavingsAccount);
        
        LocalDate date = LocalDate.of(2017,06,01);
		Transaction deposit = new Transaction(200.0, date.atStartOfDay());
		checkingAccount.addTransaction(deposit);
		maxiSavingsAccount.addTransaction(deposit);
		
		double expectedInterest = checkingAccount.totalInterest() + maxiSavingsAccount.totalInterest();
        assertEquals(expectedInterest, henry.totalInterestEarned(), 0.0000001);
	}
	
	@Test //Test customer statement generation for two accounts
    public void statementForAccount_OneAccount_ReturnStatementWithAccount(){
    	Account savingsAccount = new Account(Account.Type.SAVINGS);
        Customer henry = new Customer("Henry").openAccount(savingsAccount);
        
        LocalDate date = LocalDate.of(2018,06,01);
		Transaction deposit = new Transaction(200.0, date.atStartOfDay());
		savingsAccount.addTransaction(deposit);
		savingsAccount.deposit(300.0);
        assertEquals("Statement for Henry\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $200.00\n" +
                "  deposit $300.00\n" +
                "  interest $0.00\n" +
                "Total $500.00\n" +
                "\n" +
                "Total In All Accounts $500.00", henry.getStatement());
    }
	
    @Test //Test customer statement generation for two accounts
    public void statementForAccount_TwoAccounts_ReturnStatementWithAccounts(){

    	Account checkingAccount = new Account(Account.Type.CHECKING);
		Account maxiSavingsAccount = new Account(Account.Type.MAXI_SAVINGS);
        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(maxiSavingsAccount);
        
        LocalDate date = LocalDate.of(2018,06,01);
		Transaction deposit = new Transaction(200.0, date.atStartOfDay());
		checkingAccount.addTransaction(deposit);
		maxiSavingsAccount.addTransaction(deposit);
		checkingAccount.deposit(300.0);
        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $200.00\n" +
                "  deposit $300.00\n" +
                "  interest $0.00\n" +
                "Total $500.00\n" +
                "\n" +
                "Maxi Savings Account\n" +
                "  deposit $200.00\n" +
                "  interest $0.07\n" +
                "Total $200.07\n" +
                "\n" +
                "Total In All Accounts $700.07", henry.getStatement());
    }
}
