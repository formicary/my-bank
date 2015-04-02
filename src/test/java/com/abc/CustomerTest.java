package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    /* A customer can deposit / withdraw funds from an account */
    @Test //Test customer statement generation
    public void verify_customer_statement_generation() {
        AccountType checking = AccountType.CHECKING;
        AccountType savings = AccountType.SAVINGS;
        Account checkingAccount = new Account(checking);
        Account savingsAccount = new Account(savings);

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
    public void verify_creation_of_one_account() {
        AccountType savings = AccountType.SAVINGS;
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(new Account(savings));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void verify_creation_of_two_accounts() {
        AccountType savings = AccountType.SAVINGS;
        AccountType checking = AccountType.CHECKING;
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(new Account(savings));
        oscar.openAccount(new Account(checking));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Test
    public void verify_creation_of_three_accounts() {
        AccountType savings = AccountType.SAVINGS;
        AccountType checking = AccountType.CHECKING;
        AccountType maxiSavings = AccountType.MAXI_SAVINGS;
        Customer oscar = new Customer("Oscar");
        oscar.openAccount(new Account(savings));
        oscar.openAccount(new Account(checking));
        oscar.openAccount(new Account(maxiSavings));

        assertEquals(3, oscar.getNumberOfAccounts());
    }

}
