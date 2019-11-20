package com.abc;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    private Customer elliot;
    private Account checkingAccount;
    private Account savingsAccount;
    private Account maxiSavingsAccount;

    /**
     * Easier to create a single, and therefore consistent, customer which is reinitialised prior to each
     * test, than to create new customers for each test.
     */
    @Before
    public void defaultCustomer() {
        elliot = new Customer("Elliot Alderson");
    }

    /**
     * Same premise as creating a default customer.
     * Accounts of each type are initialised prior to tests, ensuring consistency across test cases.
     */
    @Before
    public void defaultAccounts() {
        checkingAccount = new Account(AccountType.CHECKING);
        savingsAccount = new Account(AccountType.SAVINGS);
        maxiSavingsAccount = new Account(AccountType.MAXI_SAVINGS);
    }

    /**
     * Tests against the appropriate generation of customer statements.
     */
    @Test
    public void testApp() {
        elliot.openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

        assertEquals("Statement for Elliot Alderson\n" +
            "\n" +
            "Checking Account\n" +
            "\tdeposit $100.00\n" +
            "Total $100.00\n" +
            "\n" +
            "Savings Account\n" +
            "\tdeposit $4,000.00\n" +
            "\twithdrawal $200.00\n" +
            "Total $3,800.00\n" +
            "\n" +
            "Cross-account total: $3,900.00", elliot.genStatement());
    }

    /**
     * Test ensuring that accounts are opened successfully under a customer.
     */
    @Test
    public void testOneAccount() {
        elliot.openAccount(checkingAccount);
        assertEquals(1, elliot.getNumberOfAccounts());
    }

    /**
     * Testing that customers can open two accounts successfully.
     */
    @Test
    public void testTwoAccount() {
        elliot.openAccount(new Account(AccountType.SAVINGS))
                .openAccount(new Account(AccountType.CHECKING));
        assertEquals(2, elliot.getNumberOfAccounts());
    }

    /**
     * Test ensuring that customers can open three accounts successfully.
     */
    @Test
    public void testThreeAccounts() {
        // Chaining is easier than repeatedly referencing the elliot object
        elliot.openAccount(new Account(AccountType.SAVINGS))
                .openAccount(new Account(AccountType.MAXI_SAVINGS))
                .openAccount(new Account(AccountType.CHECKING));
        assertEquals(3, elliot.getNumberOfAccounts());
    }

    /**
     * Testing a customer's ability to transfer money between accounts.
     */
    @Test
    public void testTransfer() {
        // I've added a getter for the accounts of a customer
        // This would typically be used to retrieve certain accounts
        // However, since I already have the accounts, I'll make transactions prior to
        // their opening with Elliot Alderson
        checkingAccount.deposit(2015);
        maxiSavingsAccount.deposit(71);
        checkingAccount.withdraw(1116);

        elliot.openAccount(checkingAccount)
                .openAccount(maxiSavingsAccount);

        // Checking account inequality would benefit from overriding equals
        elliot.transfer(59, checkingAccount, maxiSavingsAccount);

        assertEquals(checkingAccount.getBalance(), 399, 0);
    }

    /**
     * Test to guarantee customer's cannot transfer negative funds between accounts.
     * Negative transfers, theoretically, would just be an inverse transfer. Still, we probably don't want that.
     *
     * Testing for the presence of a thrown exception.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testNegativeTransfer() {
        checkingAccount.deposit(2019);
        maxiSavingsAccount.deposit(22);

        elliot.openAccount(checkingAccount)
                .openAccount(maxiSavingsAccount);

        elliot.transfer(-1886, checkingAccount, maxiSavingsAccount);
    }

    /**
     * Test to guarantee customer's cannot transfer more funds than available from one account to another.
     * Violation of the test could cause a recession in production. :(
     */
    @Test (expected = IllegalArgumentException.class)
    public void testExcessFundsTransfer() {
        checkingAccount.deposit(0.50);
        maxiSavingsAccount.deposit(0.25);

        elliot.openAccount(checkingAccount)
                .openAccount(maxiSavingsAccount);

        elliot.transfer(Double.MAX_VALUE, checkingAccount, maxiSavingsAccount);
    }

    /**
     * Test to ensure that transfers can only occur if the customer has, at least, 2 accounts.
     * Incidentally also tests the inability to transfer between a single account.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testSingleAccountTranfer() {
        checkingAccount.deposit(50000);

        elliot.openAccount(checkingAccount);

        elliot.transfer(Double.MAX_VALUE, checkingAccount, checkingAccount);
    }
}
