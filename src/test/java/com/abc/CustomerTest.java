package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
                "  Deposit: $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings Account\n" +
                "  Deposit: $4,000.00\n" +
                "  Withdrawal: $200.00\n" +
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
    public void successfulAccountTransfer() {
        Customer bob = new Customer("Bob");
        Account checking = new Account(Account.CHECKING);
        bob.openAccount(checking);
        Account savings = new Account(Account.SAVINGS);
        bob.openAccount(savings);

        checking.deposit(100);
        assertTrue(bob.accountTransfer(checking, savings, 50));

        assertEquals(50, checking.getBalance(), DOUBLE_DELTA);
        assertEquals(50, savings.getBalance(), DOUBLE_DELTA);
    }

    @Test
    public void lowFundsAccountTransfer() {
        Customer bob = new Customer("Bob");
        Account checking = new Account(Account.CHECKING);
        bob.openAccount(checking);
        Account savings = new Account(Account.SAVINGS);
        bob.openAccount(savings);

        checking.deposit(50);
        assertFalse(bob.accountTransfer(checking, savings, 100));

        assertEquals(50, checking.getBalance(), DOUBLE_DELTA);
        assertEquals(0, savings.getBalance(), DOUBLE_DELTA);
    }
}
