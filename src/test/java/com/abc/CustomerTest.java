package com.abc;

import org.junit.Test;

import com.abc.Utilities.Enums.AccountType;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;

// Todo: add missing tests cases to ensure full coverage
public class CustomerTest {
    private static final double DOUBLE_DELTA = 1e-2; // is this good practice?

    private Customer customer;
    private Customer customer2;
    private Account checkingAccount;
    private Account checkingAccount2;
    private Account savingsAccount;
    private Account maxiSavingsAccount;

    @Before
    public void setup() {
        customer = new Customer("Jade");
        customer2 = new Customer("Jack");
        checkingAccount = new Account(AccountType.CHECKING);
        checkingAccount2 = new Account(AccountType.CHECKING);
        savingsAccount = new Account(AccountType.SAVINGS);
        maxiSavingsAccount = new Account(AccountType.MAXI_SAVINGS);
    }

    @After
    public void tearDown() {
        customer = null;
        customer2 = null;
        checkingAccount = null;
        checkingAccount2 = null;
        savingsAccount = null;
        maxiSavingsAccount = null;
    }

    @Test
    public void testOneAccount(){
        customer.openAccount(checkingAccount);
        assertEquals(1, customer.getNumberOfAccounts());
    }

    @Test
    public void testMultipleAcounts() {
        customer.openAccount(checkingAccount);
        customer.openAccount(savingsAccount);
        customer.openAccount(maxiSavingsAccount);
        assertEquals(3, customer.getNumberOfAccounts());
    }

    @Test
    public void testTransferFundsDeductsAmountFromInitialAccount() {
        customer.openAccount(checkingAccount);
        customer.openAccount(savingsAccount);

        checkingAccount.depositFunds(150.00);
        customer.transferFunds(checkingAccount, savingsAccount, 50);

        assertEquals(null, 100, checkingAccount.getBalance(), DOUBLE_DELTA);
    }

    @Test
    public void testFundsTransferredToDestinationAccount() {
        customer.openAccount(checkingAccount);
        customer.openAccount(savingsAccount);

        checkingAccount.depositFunds(150.00);
        customer.transferFunds(checkingAccount, savingsAccount, 50);

        assertEquals(null, 50, savingsAccount.getBalance(), DOUBLE_DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTransferFundsToNonCustomerAccount() {
        customer.openAccount(checkingAccount);
        customer2.openAccount(maxiSavingsAccount);

        checkingAccount.depositFunds(150);
        customer.transferFunds(checkingAccount, maxiSavingsAccount, 50);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTransferFundsFromNonCustomerAccount() {
        customer.openAccount(checkingAccount);
        customer.openAccount(savingsAccount);
        customer2.openAccount(checkingAccount2);

        checkingAccount2.depositFunds(150);
        customer.transferFunds(checkingAccount2, savingsAccount, 50);
    }

    @Test
    public void testCustomerStatementGeneration(){
        customer.openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.depositFunds(100.0);
        savingsAccount.depositFunds(4000.0);
        savingsAccount.withdrawFunds(200.0);

        assertEquals("Statement for Jade\n" +
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
                "Total In All Accounts $3,900.00", customer.generateConsolidatedAcountsStatement());
    }

    @Test
    public void testCustomerStatementGenerationWhenNoTransactions() {
        customer.openAccount(checkingAccount).openAccount(savingsAccount);

            assertEquals("Statement for Jade\n" +
                "\n" +
                "Checking Account\n" +
                "Total $0.00\n" +
                "\n" +
                "Savings Account\n" +
                "Total $0.00\n" +
                "\n" +
                "Total In All Accounts $0.00", customer.generateConsolidatedAcountsStatement());
    }
}
