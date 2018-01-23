package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {
    
    private static final double DOUBLE_DELTA = 1e-15;
    
    @Test //Test customer statement generation
    public void testApp(){
        Customer henry = new Customer("Henry");
        
        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);
        
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

    //test getnumberOfAccounts() with one account open
    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.CHECKING));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    //test getnumberOfAccounts() with two accounts open
    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.CHECKING));
        oscar.openAccount(new Account(Account.SAVINGS));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    //test getnumberOfAccounts() with three accounts open
    @Test
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        oscar.openAccount(new Account(Account.SAVINGS));
        assertEquals(3, oscar.getNumberOfAccounts());
    }
    
    //the betweenAccountTransaction() method by checking that the money has left the initial account
    @Test
    public void testAccountTransfer() {
        Customer oscar = new Customer("Oscar");
        Account aAccount = new Account(Account.SAVINGS);
        Account bAccount = new Account(Account.CHECKING);
        
        oscar.openAccount(aAccount).openAccount(bAccount);
        oscar.betweenAccountTransaction(aAccount, bAccount, 100);
        assertEquals(-100.0, aAccount.sumTransactions() , DOUBLE_DELTA);
    }
    
    //check the totalInterestEarned() method by adding up all the interest earned over two accounts
    @Test
    public void testInterestEarned() {
        Customer abby = new Customer("Abby");
        Account savings = new Account(Account.SAVINGS);
        Account checking = new Account(Account.CHECKING);
        abby.openAccount(savings).openAccount(checking);
        
        savings.deposit(2000);
        checking.deposit(1000);
        
        assertEquals(4, abby.totalInterestEarned(),DOUBLE_DELTA);
    }
}
