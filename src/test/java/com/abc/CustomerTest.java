package com.abc;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CustomerTest {
    private static final double DOUBLE_DELTA = 1e-15;

    private Customer henry;
    private Account savingsAccount;
    private Account checkingAccount;

    @Before
    public void createCustomer() {
        henry = new Customer("Henry");


        savingsAccount = new Account(AccountType.SAVINGS);
        checkingAccount = new Account(AccountType.CHECKING);

        savingsAccount.deposit(1000);
        checkingAccount.deposit(1000);

        henry.openAccount(savingsAccount);
        henry.openAccount(checkingAccount);
    }

    @Test
    public void nameIsStoredCorrectly() {
        Assert.assertEquals("Henry", henry.getName());
    }

    @Test
    public void addAccountAddsAccountToAccounts() {
        Customer customer = new Customer("customer");
        Assert.assertEquals(0, customer.getNumberOfAccounts());
        customer.openAccount(savingsAccount);
        Assert.assertEquals(1, customer.getNumberOfAccounts());
    }

    @Test
    public void totalInterestEarnedIsCorrect() {
        Assert.assertEquals(2, henry.totalInterestEarned(), DOUBLE_DELTA);
    }

    @Test //Test customer statement generation
    public void testCustomerStatementIsCorrect() {
        Assert.assertEquals("Statement for Henry\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $1,000.00\n" +
                "Total $1,000.00\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $1,000.00\n" +
                "Total $1,000.00\n" +
                "\n" +
                "Total In All Accounts $2,000.00", henry.getStatement());
    }
}
