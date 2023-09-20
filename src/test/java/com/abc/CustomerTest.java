package com.abc;

import org.junit.Test;

import com.abc.Utilities.Enums.AccountType;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;

// Todo: add missing tests cases to ensure full coverage
public class CustomerTest {

    private Customer customer;
    private Account checkingAccount;
    private Account savingsAccount;
    private Account maxiSavingsAccount;

    @Before
    public void setup() {
        customer = new Customer("Jade");
        checkingAccount = new Account(AccountType.CHECKING);
        savingsAccount = new Account(AccountType.SAVINGS);
        maxiSavingsAccount = new Account(AccountType.MAXI_SAVINGS);
    }

    @After
    public void tearDown() {
        customer = null;
        checkingAccount = null;
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
    public void testCustomerStatementGeneration(){
        customer.openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.depositFunds(100.0);
        savingsAccount.depositFunds(4000.0);
        savingsAccount.withdraw(200.0);

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
                "Total In All Accounts $3,900.00", customer.getStatement());
    }
}
