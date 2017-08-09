package com.abc;

import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

public class CustomerTest {
	
	private static final double DOUBLE_DELTA = 1e-10;

    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new Account(AccountType.CHECKING);
        Account savingsAccount = new Account(AccountType.SAVINGS);

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  interest_earned $0.00\n" +
                "  deposit $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings Account\n" +
                "  interest_earned $0.00\n" +
                "  deposit $4,000.00\n" +
                "  interest_earned $0.00\n" +
                "  withdrawal $200.00\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Total In All Accounts $3,900.00", henry.getStatement());
    }
    
    @Test
    public void testApp_NoAccounts() {
    	Customer henry = new Customer("Henry");

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Total In All Accounts $0.00", henry.getStatement());
    }
    
    @Test
    public void testApp_AccountsButNoTransactions() {
    	Account checkingAccount = new Account(AccountType.CHECKING);
        Account savingsAccount = new Account(AccountType.SAVINGS);

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);
        
        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "Total $0.00\n" +
                "\n" +
                "Savings Account\n" +
                "Total $0.00\n" +
                "\n" +
                "Total In All Accounts $0.00", henry.getStatement());
    }

    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new Account(AccountType.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(AccountType.SAVINGS));
        oscar.openAccount(new Account(AccountType.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void testFourAcounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(AccountType.SAVINGS));
        oscar.openAccount(new Account(AccountType.CHECKING));
        oscar.openAccount(new Account(AccountType.CHECKING));
        oscar.openAccount(new Account(AccountType.MAXI_SAVINGS));
        assertEquals(4, oscar.getNumberOfAccounts());
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testTransfer_InvalidAccount() {
        Account checkingAccount = new Account(AccountType.CHECKING);
        Account savingsAccount = new Account(AccountType.SAVINGS);
        Account maxiSavingsAccount = new Account(AccountType.MAXI_SAVINGS); 

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);
        
        henry.transferBetweenAccounts(maxiSavingsAccount, checkingAccount, 400.0);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testTransfer_NotEnoughMoney() {
        Account checkingAccount = new Account(AccountType.CHECKING);
        Account savingsAccount = new Account(AccountType.SAVINGS);
        Account maxiSavingsAccount = new Account(AccountType.MAXI_SAVINGS); 

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(300.0);
        savingsAccount.withdraw(200.0);
        
        henry.transferBetweenAccounts(maxiSavingsAccount, checkingAccount, 400.0);
    }
    
    @Test
    public void testTransfer() {
        Account checkingAccount = new Account(AccountType.CHECKING);
        Account savingsAccount = new Account(AccountType.SAVINGS);

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);
        
        henry.transferBetweenAccounts(savingsAccount, checkingAccount, 400.0);
        assertEquals(500.0, checkingAccount.getCurrentBalance(), DOUBLE_DELTA);
        assertEquals(3400.0, savingsAccount.getCurrentBalance(), DOUBLE_DELTA);
    }
    
    
    @Test
    public void testTotalInterestEarned() {
        Account checkingAccount = Mockito.mock(Account.class);
        Mockito.when(checkingAccount.getAccountType()).thenReturn(AccountType.CHECKING);
        Mockito.when(checkingAccount.interestEarned()).thenReturn(15.00);
        
        Account savingsAccount = Mockito.mock(Account.class);
        Mockito.when(savingsAccount.getAccountType()).thenReturn(AccountType.SAVINGS);
        Mockito.when(savingsAccount.interestEarned()).thenReturn(38.00);

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);
        
        assertEquals(53.0, henry.totalInterestEarned(), DOUBLE_DELTA);
        
    }
    
    public void testTotalInterestEarned_NoAccounts() {
        Customer henry = new Customer("Henry");
        
        assertEquals(0.0, henry.totalInterestEarned(), DOUBLE_DELTA);
        
    }
}
