package com.abc;

import org.junit.Ignore;
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

    @Test
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        oscar.openAccount(new Account(Account.MAXI_SAVINGS));
        assertEquals(3, oscar.getNumberOfAccounts());
    }


    @Test
    public void transferSuccess(){
        Customer oscar = new Customer("Oscar");
        Account saving = new Account(Account.SAVINGS);
        saving.deposit(1000);
        oscar.openAccount(saving);


        Account checking = new Account(Account.CHECKING);
        oscar.openAccount(checking);

        oscar.transfer(saving,checking,500);

        assertEquals(500, saving.sumTransactions(), DOUBLE_DELTA);
        assertEquals(500, checking.sumTransactions(), DOUBLE_DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void transferInsuccessAccount(){
        Customer oscar = new Customer("Oscar");
        Account saving = new Account(Account.SAVINGS);
        saving.deposit(1000);
        oscar.openAccount(saving);


        Account checking = new Account(Account.CHECKING);

        oscar.transfer(saving,checking,500);

        assertEquals(500, saving.sumTransactions(), DOUBLE_DELTA);
        assertEquals(500, checking.sumTransactions(), DOUBLE_DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void transferInsuccessAmount(){
        Customer oscar = new Customer("Oscar");
        Account saving = new Account(Account.SAVINGS);
        saving.deposit(1000);
        oscar.openAccount(saving);


        Account checking = new Account(Account.CHECKING);
        oscar.openAccount(checking);

        oscar.transfer(saving,checking,-500);

        assertEquals(500, saving.sumTransactions(), DOUBLE_DELTA);
        assertEquals(500, checking.sumTransactions(), DOUBLE_DELTA);
    }
}
