package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {
    
    private static final double DOUBLE_DELTA = 1e-15;

    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new Account(Account.accountType.CHECKING);
        Account savingsAccount = new Account(Account.accountType.SAVINGS);

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

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
    
    //checking for the exceeding amount withdrawal. 
    @Ignore
     public void testApp2(){

        Account checkingAccount = new Account(Account.accountType.CHECKING);
        Account savingsAccount = new Account(Account.accountType.SAVINGS);

        Customer fabian = new Customer("Fabian").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(199.0);
        savingsAccount.withdraw(200.0);

        assertEquals("Statement for Fabian\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $199.00\n" +
                "  withdrawal $200.00\n" +
                "Total $-1.00\n" +
                "\n" +
                "Total In All Accounts $99.00", fabian.getStatement());
    }
     //checking for all 0's.  
     @Ignore
     public void testApp3(){

        Account checkingAccount = new Account(Account.accountType.CHECKING);
        Account savingsAccount = new Account(Account.accountType.SAVINGS);

        Customer tony = new Customer("Tony").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(0.0);
        savingsAccount.deposit(0.0);
        savingsAccount.withdraw(0.0);

        assertEquals("Statement for Tony\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $0.00\n" +
                "Total $0.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $0.00\n" +
                "  withdrawal $0.00\n" +
                "Total $0.00\n" +
                "\n" +
                "Total In All Accounts $0.00", tony.getStatement());
    }

    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.accountType.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccounts(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.accountType.SAVINGS));
        oscar.openAccount(new Account(Account.accountType.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }
    
    //added the maxi savings account to test 3 accounts
    @Test
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.accountType.SAVINGS));
        oscar.openAccount(new Account(Account.accountType.CHECKING));
        oscar.openAccount(new Account(Account.accountType.MAXI_SAVINGS));
        assertEquals(3, oscar.getNumberOfAccounts());
        
    }
    
    //added testing for transferring between accounts
    @Test
    public void testTransfer() {
        Bank bank = new Bank();
        
        Account checkingAccount = new Account(Account.accountType.CHECKING);
        Account savingsAccount = new Account(Account.accountType.SAVINGS);

        Customer fabian = new Customer("Fabian").openAccount(checkingAccount).openAccount(savingsAccount);
       
        bank.addCustomer(fabian);
        
        checkingAccount.deposit(500);
        savingsAccount.deposit(100);

        fabian.transfer(200, checkingAccount, savingsAccount);

        assertEquals(300, savingsAccount.sumTransactions(), DOUBLE_DELTA);
    }
}
