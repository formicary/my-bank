package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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

    @Test
    public void testAccruedInterest(){

        Account savingsAccount = new Account(Account.SAVINGS);

        savingsAccount.deposit(4000.0);
        savingsAccount.deposit(4000.0);

        assertEquals(0, savingsAccount.getAccruedInterest(),DOUBLE_DELTA);

    }

    @Test
    public void testCompoundInterest(){

        Account savingsAccount = new Account(Account.SAVINGS);

        savingsAccount.deposit(4000.0);
        savingsAccount.deposit(4000.0);

        assertEquals(0, savingsAccount.getCompoundInterest(),DOUBLE_DELTA);

    }

    @Test
    public void testTransferBetweenAccounts(){

        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(1000.0);
        savingsAccount.deposit(4000.0);

        henry.transferBetweenAccounts(checkingAccount,savingsAccount,100);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $1,000.00\n" +
                "  withdrawal $100.00\n" +
                "Total $900.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4,000.00\n" +
                "  deposit $100.00\n" +
                "Total $4,100.00\n" +
                "\n" +
                "Total In All Accounts $5,000.00", henry.getStatement());
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
