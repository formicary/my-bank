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

    @Ignore
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.SAVINGS));
        oscar.openAccount(new Account(Account.CHECKING));
        assertEquals(3, oscar.getNumberOfAccounts());
    }

    @Test
    public void testIfTransferred() {
        Account accountFrom = new Account(Account.SAVINGS);
        Account accountTo = new Account(Account.CHECKING);
        accountFrom.deposit(100.0);
        accountTo.deposit(200.0);
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(accountFrom).openAccount(accountTo);
        oscar.transferTo(20.0,accountFrom,accountTo);
        assertEquals(80.0, accountFrom.sumTransactions(), DOUBLE_DELTA);
        assertEquals(220.0, accountTo.sumTransactions(), DOUBLE_DELTA);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testIfNotTransferred() {
        try {
            Account accountFrom = new Account(Account.SAVINGS);
            Account accountTo = new Account(Account.CHECKING);
            accountFrom.deposit(100);
            accountTo.deposit(200);
            Customer oscar = new Customer("Oscar");
            oscar.openAccount(accountFrom).openAccount(accountTo);
            oscar.transferTo(110, accountFrom, accountTo);
        } catch (IllegalArgumentException exception) {
            assertEquals("insufficient balance", exception.getMessage());
            throw exception;
        }
    }

    @Test (expected = IllegalArgumentException.class)
    public void testNegativeAmountTransfer() {
        try {
            Account accountFrom = new Account(Account.SAVINGS);
            Account accountTo = new Account(Account.CHECKING);
            accountFrom.deposit(100);
            accountTo.deposit(200);
            Customer oscar = new Customer("Oscar");
            oscar.openAccount(accountFrom).openAccount(accountTo);
            oscar.transferTo(-20, accountFrom, accountTo);
        }
        catch (IllegalArgumentException exception) {
            assertEquals("negative amount not allowed", exception.getMessage());
            throw exception;
        }

    }
}
