package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    private static final double DOUBLE_DELTA = 1e-15;

    @Test //Test customer statement generation
    public void testApp() {

        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

        Customer henry = new Customer("Henry", 1).openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

        assertEquals("Statement for Henry\n"
                + "\n"
                + "Checking Account\n"
                + "  deposit $100.00\n"
                + "Total $100.00\n"
                + "\n"
                + "Savings Account\n"
                + "  deposit $4,000.00\n"
                + "  withdrawal $200.00\n"
                + "Total $3,800.00\n"
                + "\n"
                + "Total In All Accounts $3,900.00", henry.getStatement());
    }

    @Test
    public void testOneAccount() {
        Customer oscar = new Customer("Oscar", 1).openAccount(new Account(Account.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccount() {
        Customer oscar = new Customer("Oscar", 1)
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTransferAccount() {
        Customer oscar = new Customer("Oscar", 1)
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        oscar.getAccount(1).deposit(100);
        oscar.getAccount(0).deposit(100);
        oscar.transferBetweenAccounts(oscar.getAccount(1), oscar.getAccount(0), 100);
        assertEquals(200.0, oscar.getAccount(0).getBalance(), DOUBLE_DELTA);
    }
}
