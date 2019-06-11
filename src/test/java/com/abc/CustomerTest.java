package com.abc;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test
    public void statementTest() {

        Account checkingAccount = new Account(Account.Type.CHECKING);
        Account savingsAccount = new Account(Account.Type.SAVINGS);

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
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar");

        oscar.openAccount(new Account(Account.Type.SAVINGS));
        oscar.openAccount(new Account(Account.Type.CHECKING));
        oscar.openAccount(new Account(Account.Type.MAXI_SAVINGS));
        assertEquals(3, oscar.getNumberOfAccounts());
    }

    @Test
    public void withdrawalTransaction() {
        Customer oscar = new Customer("Oscar");
        Account checkingAccount = new Account(Account.Type.CHECKING);
        oscar.openAccount(checkingAccount);

        boolean exceptionCaught = false;
        try {
            checkingAccount.deposit(200);
            checkingAccount.withdraw(400);
        } catch (IllegalArgumentException ex) {
            Assert.assertEquals("Not enough money on your account.", ex.getMessage());
            exceptionCaught = true;
        }
        Assert.assertTrue(exceptionCaught);
    }
}
