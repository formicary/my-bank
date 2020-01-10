package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerShould {
    @Test
    public void GenerateStatement_GivenTheyHaveOpenAccounts() {

        Account checkingAccount = new Account(Account.CHECKING);
        Account savingsAccount = new Account(Account.SAVINGS);

        Customer customer = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

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
                "Total In All Accounts $3,900.00", customer.getStatement());
    }

    @Test
    public void BeAbleToOpenOneAccount() {
        Customer customer = new Customer("Oscar");

        customer.openAccount(new Account(Account.SAVINGS));

        assertEquals(1, customer.getNumberOfAccounts());
    }

    @Test
    public void BeAbleToOpenTwoAccounts() {
        Customer customer = new Customer("Oscar");

        customer.openAccount(new Account(Account.SAVINGS));
        customer.openAccount(new Account(Account.CHECKING));

        assertEquals(2, customer.getNumberOfAccounts());
    }

    @Test
    public void BeAbleToOpenThreeAccounts() {
        Customer customer = new Customer("Oscar");

        customer.openAccount(new Account(Account.SAVINGS));
        customer.openAccount(new Account(Account.CHECKING));
        customer.openAccount(new Account(Account.MAXI_SAVINGS));

        assertEquals(3, customer.getNumberOfAccounts());
    }
}
