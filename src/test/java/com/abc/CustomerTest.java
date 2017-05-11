package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
    public void testTransfereBetweenAccounts(){
        Account checking;
        Account saving;
        Customer oscar = new Customer("Oscar").openAccount(checking = new Account(Account.CHECKING));
        oscar.openAccount(saving = new Account(Account.SAVINGS));
        oscar.transfereBetweenAccounts(checking, saving, 200.00);

        assertEquals(200.00, saving.sumTransactions(), DOUBLE_DELTA);
        assertEquals(-200.00, checking.sumTransactions(), DOUBLE_DELTA);
    }

    @Test
    public void testCheckIfAccountExists(){
        Account saving;
        Customer oscar = new Customer("Oscar").openAccount(saving = new Account(Account.SAVINGS));
        assertTrue(oscar.checkIfAccountExist(saving));
    }

    @Test
    public void testTotalInterestEarned(){
        Account saving;
        Customer oscar = new Customer("Oscar").openAccount(saving=new Account(Account.SAVINGS));
        saving.deposit(500.00);
        assertEquals(500.00*0.001/365, saving.interestEarned(), DOUBLE_DELTA);
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
