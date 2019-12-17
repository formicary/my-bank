package com.abc;

import org.junit.Assert;
import org.junit.Test;

public class CustomerTest {

    //TODO: need to add a test case for calculating the total interest earned

    @Test //Test customer statement generation
    //TODO: can be named better
    public void testApp() {

        Account checkingAccount = new Account(AccountType.CHECKING);
        Account savingsAccount = new Account(AccountType.SAVINGS);

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

        Assert.assertEquals("Statement for Henry\n" +
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
    public void testAccountAddedToCustomer() {
        Customer oscar = new Customer("Oscar").openAccount(new Account(AccountType.SAVINGS));
        Assert.assertEquals(1, oscar.getNumberOfAccounts());
    }
}
