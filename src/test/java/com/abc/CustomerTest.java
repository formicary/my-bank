package com.abc;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CustomerTest {
	private static final double DOUBLE_DELTA = 1e-15;

    @Test //Test customer statement generation
    public void testApp(){

        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

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
    
  //Test Moving Money between accounts
    @Test 
    public void testMove(){

        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(500.0);
        
        henry.moveMoney(checkingAccount, savingsAccount, 250);
                
        assertEquals(250, savingsAccount.sumTransactions(), DOUBLE_DELTA);
    }
    
    //Test Date Comparison, old date should be before checking account transaction date
    @Test 
    public void testDate(){

        Account checkingAccount = new Account(Account.CHECKING);

        checkingAccount.deposit(500.0);
        
        DateProvider oldDate = new DateProvider();
        
        boolean before = oldDate.tenDaysPrior().before(checkingAccount.transactions.get(0).transactionDate);
        
        assertEquals(before, true);
    }

    //Testings one new account
    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    // Testing two accounts
    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    // Testing three accounts
    @Test
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS)).openAccount(new Account(Account.CHECKING)).openAccount(new Account(Account.MAXI_SAVINGS));
        assertEquals(3, oscar.getNumberOfAccounts());
    }
}
