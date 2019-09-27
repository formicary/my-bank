package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CustomerTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void getName() {
        Bank bank = new Bank();
        Customer cust = new Customer("Cust", bank);
        assertEquals("Cust", cust.getName());
    }

    @Test
    public void customerStatement() {
        Bank bank = new Bank();
        Customer henry = new Customer("Henry", bank).openAccount(Account.CHECKING).openAccount(Account.SAVINGS);

        henry.deposit(100.00, 0);
        henry.deposit(4000.50,1);
        henry.withdraw(200.00,1);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4,000.50\n" +
                "  withdrawal $200.00\n" +
                "Total $3,800.50\n" +
                "\n" +
                "Total In All Accounts $3,900.50", henry.getStatement());
    }

    @Test
    public void testOneAccount() {
        Bank bank = new Bank();
        Customer oscar = new Customer("Oscar", bank).openAccount(Account.SAVINGS);
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccounts() {
        Bank bank = new Bank();
        Customer oscar = new Customer("Oscar", bank).openAccount(Account.SAVINGS);
        oscar.openAccount(Account.CHECKING);
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void testZeroAccounts() {
        Bank bank = new Bank();
        Customer oscar = new Customer("Oscar", bank);
        assertEquals(0, oscar.getNumberOfAccounts());
    }

    @Test
    public void depositWithoutAccount() {
        Bank bank = new Bank();
        Customer sophie = new Customer("Sophie", bank);

        try {
            sophie.deposit(1000, 0);
            fail("Expected illegal argument exception to be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("invalid account index", e.getMessage());
        }
    }

    @Test
    public void deposit() {
        Bank bank = new Bank();
        Customer david = new Customer("David", bank);
        david.openAccount(Account.SAVINGS);

        david.deposit(1000, 0);

        assertEquals("Statement for David\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $1,000.00\n" +
                "Total $1,000.00\n" +
                "\n" +
                "Total In All Accounts $1,000.00", david.getStatement());
    }

    @Test
    public void withdraw() {
        Bank bank = new Bank();
        Customer elizabeth = new Customer("Elizabeth", bank);
        elizabeth.openAccount(Account.SAVINGS);

        elizabeth.withdraw(1000, 0);

        assertEquals("Statement for Elizabeth\n" +
                "\n" +
                "Savings Account\n" +
                "  withdrawal $1,000.00\n" +
                "Total -$1,000.00\n" +
                "\n" +
                "Total In All Accounts $-1,000.00", elizabeth.getStatement());
    }

    @Test
    public void transfer() {
        Bank bank = new Bank();
        Customer tom = new Customer("Tom", bank);
        tom.openAccount(Account.SAVINGS);
        tom.openAccount(Account.CHECKING);

        tom.deposit(1000, 0);
        tom.transfer(1, 0, 1000);

        assertEquals("Statement for Tom\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $1,000.00\n" +
                "  withdrawal $1,000.00\n" +
                "Total $0.00\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $1,000.00\n" +
                "Total $1,000.00\n" +
                "\n" +
                "Total In All Accounts $1,000.00", tom.getStatement());
    }

    @Test
    public void totalInterestEarned() {
        Bank bank = new Bank();
        Customer george = new Customer("George", bank);

        george.openAccount(Account.CHECKING);
        george.openAccount(Account.CHECKING);
        george.openAccount(Account.CHECKING);

        george.deposit(1000, 0);
        george.deposit(1000, 1);
        george.deposit(1000, 2);

        assertEquals(3.0, george.totalInterestEarned(), DOUBLE_DELTA);
    }
}
