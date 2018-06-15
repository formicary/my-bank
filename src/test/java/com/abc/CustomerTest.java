package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void testAccountStatementGeneration(){

        Account checkingAccount = new Account(Account.accountType.CHECKING);
        Account savingsAccount = new Account(Account.accountType.SAVINGS);
        Account maxiSavingsAccount = new Account(Account.accountType.MAXI_SAVINGS);
        
        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount).openAccount(maxiSavingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);
        maxiSavingsAccount.deposit(100.0);

        assertEquals("Statement for Henry\n" +
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
                "Maxi Savings Account\n" +
                "  deposit $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Total In All Accounts $4,000.00", henry.getStatement());
    }

    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.accountType.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.accountType.SAVINGS));
        oscar.openAccount(new Account(Account.accountType.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.accountType.SAVINGS));
        oscar.openAccount(new Account(Account.accountType.CHECKING));
        oscar.openAccount(new Account(Account.accountType.MAXI_SAVINGS));
        assertEquals(3, oscar.getNumberOfAccounts());
    }
    
    @Test
    public void testNoAccountTotalInterest() {
        Customer bill = new Customer("Bill");
        assertEquals(0.0, bill.totalInterestEarned(), DOUBLE_DELTA);
    }
    
    @Test
    public void testOneTotalInterest() {
    	Bank bank = new Bank();
        Account checkingAccount = new Account(Account.accountType.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(365.0);

        assertEquals(0.001, bill.totalInterestEarned(), DOUBLE_DELTA);
    }
    
    @Test
    public void testTwoTotalInterest() {
    	Bank bank = new Bank();
        Account savingsAccount = new Account(Account.accountType.SAVINGS);
        Account checkingAccount = new Account(Account.accountType.CHECKING);
        Customer bill = new Customer("Bill").openAccount(savingsAccount);
        bill.openAccount(checkingAccount);
        bank.addCustomer(bill);
        	
        savingsAccount.deposit(1500.0);
        checkingAccount.deposit(100.0);

        assertEquals(0.0057534246575344247, bill.totalInterestEarned(), DOUBLE_DELTA);
    }
    
	@Test
	public void testTransferFromAccount() {
		Bank bank = new Bank();
        Account savingsAccount = new Account(Account.accountType.SAVINGS);
        Account checkingAccount = new Account(Account.accountType.CHECKING);
        Customer bill = new Customer("Bill").openAccount(savingsAccount);
        bill.openAccount(checkingAccount);
        bank.addCustomer(bill);
        	
        savingsAccount.deposit(1500.0);
        checkingAccount.deposit(100.0);
        
        bill.transferfunds(savingsAccount, checkingAccount, 500.0);

        assertEquals(1000.0, savingsAccount.sumTransactions(), DOUBLE_DELTA);
	}
	
	@Test
	public void testTransferToAccount() {
		Bank bank = new Bank();
        Account savingsAccount = new Account(Account.accountType.SAVINGS);
        Account checkingAccount = new Account(Account.accountType.CHECKING);
        Customer bill = new Customer("Bill").openAccount(savingsAccount);
        bill.openAccount(checkingAccount);
        bank.addCustomer(bill);
        	
        savingsAccount.deposit(1500.0);
        checkingAccount.deposit(100.0);
        
        bill.transferfunds(savingsAccount, checkingAccount, 500.0);

        assertEquals(600.0, checkingAccount.sumTransactions(), DOUBLE_DELTA);
	}
}
