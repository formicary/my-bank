package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {
    
     private static final double DOUBLE_DELTA = 1e-15;
     
    @Test //Test customer statement generation
    public void testApp(){
        
        Customer henry = new Customer("Henry");
        
        Account checkingAccount = new CheckingAccount(henry, Account.CHECKING);
        Account savingsAccount = new SavingsAccount(henry, Account.SAVINGS);

        henry.openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

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
                "Total In All Accounts $3,900.00", henry.getStatement());
    }

    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(new SavingsAccount(oscar, Account.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(new SavingsAccount(oscar, Account.SAVINGS));
        oscar.openAccount(new CheckingAccount(oscar, Account.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Ignore
    public void testThreeAcounts() {
       Customer oscar = new Customer("Oscar");
        oscar.openAccount(new SavingsAccount(oscar, Account.SAVINGS));
        oscar.openAccount(new CheckingAccount(oscar, Account.CHECKING));
        assertEquals(3, oscar.getNumberOfAccounts());
    }
    
    @Test
    public void testTransferBetweenAccounts() {
        Customer oscar = new Customer("Oscar");
        Account checkingAccount = new CheckingAccount(oscar, Account.CHECKING);
        Account savingsAccount = new SavingsAccount(oscar, Account.SAVINGS);
        checkingAccount.deposit(200);
        
        oscar.transferBetweenAccounts(checkingAccount, savingsAccount, 50);
        assertEquals(150, checkingAccount.sumTransactions(), DOUBLE_DELTA);
        assertEquals(50, savingsAccount.sumTransactions(), DOUBLE_DELTA); 
    }
    
    @Test(expected=IllegalArgumentException.class) 
    public void testTransferInsufficientFunds() {
        Customer oscar = new Customer("Oscar");
        Account checkingAccount = new CheckingAccount(oscar, Account.CHECKING);
        Account savingsAccount = new SavingsAccount(oscar, Account.SAVINGS);
        checkingAccount.deposit(20);

        oscar.transferBetweenAccounts(checkingAccount, savingsAccount, 50);
        assertEquals(20, checkingAccount.sumTransactions(), DOUBLE_DELTA);
        assertEquals(0, savingsAccount.sumTransactions(), DOUBLE_DELTA);
    }
    
    @Test(expected=IllegalArgumentException.class) 
    public void testTransferDiffCustomers() {
        Customer oscar = new Customer("Oscar");
        Customer chris = new Customer("Chris");
        Account checkingAccount = new CheckingAccount(oscar, Account.CHECKING);
        Account savingsAccount = new SavingsAccount(chris, Account.SAVINGS);
        checkingAccount.deposit(200);

        oscar.transferBetweenAccounts(checkingAccount, savingsAccount, 50);
        assertEquals(200, checkingAccount.sumTransactions(), DOUBLE_DELTA);
        assertEquals(0, savingsAccount.sumTransactions(), DOUBLE_DELTA);
    }
    
    @Test(expected=IllegalArgumentException.class) 
    public void testTransferNegativeAmount() {
        Customer oscar = new Customer("Oscar");
         Account checkingAccount = new CheckingAccount(oscar, Account.CHECKING);
         Account savingsAccount = new SavingsAccount(oscar, Account.SAVINGS);
         checkingAccount.deposit(200);
         
         oscar.transferBetweenAccounts(checkingAccount, savingsAccount, -20);
         assertEquals(200, checkingAccount.sumTransactions(), DOUBLE_DELTA);
         assertEquals(0, savingsAccount.sumTransactions(), DOUBLE_DELTA);  
    }  
}
