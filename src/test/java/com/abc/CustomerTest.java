package com.abc;

import org.junit.Ignore;
import org.junit.Test;
import java.util.Calendar;

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
    
    @Test //Test Moving Money between accounts
    public void testMove(){

        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(500.0);
        
        henry.moveMoney(checkingAccount, savingsAccount, 250);
        System.out.println("balance: "+henry.getBalance(savingsAccount));
        System.out.println(System.currentTimeMillis());
        
        assertEquals(250, henry.getBalance(savingsAccount), DOUBLE_DELTA);
    }
    
    @Test //Test Date Comparison
    public void testDate(){

        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(500.0);
        
        DateProvider oldDate = new DateProvider();
        
             
        System.out.println(oldDate.tenDaysPrior());
        
        System.out.println(checkingAccount.transactions.get(0).transactionDate);
        
        boolean before = oldDate.tenDaysPrior().before(checkingAccount.transactions.get(0).transactionDate);
        System.out.println(before);
        
        assertEquals(before, true);
    }

    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Ignore
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        assertEquals(3, oscar.getNumberOfAccounts());
    }
}
