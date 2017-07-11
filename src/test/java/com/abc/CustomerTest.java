package com.abc;

import static com.abc.Account.AccountType.*;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test
    public void testOpenAccount1() {
        Customer oscar = new Customer("Oscar");

        oscar.openAccount("AccSavings", new Account(SAVINGS));
        oscar.openAccount("AccMaxi", new Account(MAXI_SAVINGS));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOpenAccount2() {
        Customer oscar = new Customer("Oscar");

        oscar.openAccount("AccSavings", new Account(SAVINGS));
        oscar.openAccount("AccSavings", new Account(CHECKING));
    }

    @Test
    public void testGetNumberOfAccounts() {
        Customer oscar = new Customer("Oscar");

        oscar.openAccount("Acc1", new Account(SAVINGS));

        int expected = 1;
        int result = oscar.getNumberOfAccounts();
        assertEquals(expected, result);

        oscar.openAccount("Acc2", new Account(CHECKING));

        expected = 2;
        result = oscar.getNumberOfAccounts();
        assertEquals(expected, result);
    }

    @Test
    public void testGetStatement() {
        Customer oscar = new Customer("Oscar");

        Account checkingAccount = new Account(CHECKING);
        Account savingsAccount = new Account(SAVINGS);

        oscar.openAccount("AccChecking", checkingAccount);
        oscar.openAccount("AccSavings", savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

        String expected = "Statement for Oscar\n"
                + "\n"
                + "Savings Account\n"
                + "  deposit $4,000.00\n"
                + "  withdrawal $200.00\n"
                + "Total $3,800.00\n"
                + "\n"
                + "Checking Account\n"
                + "  deposit $100.00\n"
                + "Total $100.00\n"
                + "\n"
                + "Total In All Accounts $3,900.00";
        String result = oscar.getStatement();

        assertEquals(expected, result);
    }
}
