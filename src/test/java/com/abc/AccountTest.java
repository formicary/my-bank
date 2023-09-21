package com.abc;

import org.junit.Test;

import com.abc.Utilities.Enums.AccountType;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;

// Todo: add missing test cases to ensure full coverage. Consider global variable for e.g. deposit amount
public class AccountTest {
    private static final double DOUBLE_DELTA = 1e-2; // is this good practice?

    private Customer customer;
    private Account checkingAccount;

    @Before
    public void setup() {
        customer = new Customer("Jade");
        checkingAccount = new Account(AccountType.CHECKING);
    }

    @After
    public void tearDown() {
        customer = null;
        checkingAccount = null;
    }

    @Test
    public void testDepositFunds() {
        customer.openAccount(checkingAccount);
        checkingAccount.depositFunds(100.00);

        assertEquals(null, 100.00, checkingAccount.getBalance(), DOUBLE_DELTA);
    }

    @Test(expected = IllegalArgumentException.class) // Todo: is good practice? Can this be asserted instead?
    public void testDepositFundsWithNegativeAmount() {
        customer.openAccount(checkingAccount);
        checkingAccount.depositFunds(-100.00);
    }

    @Test
    public void testWihdrawFunds() {
        customer.openAccount(checkingAccount);
        checkingAccount.depositFunds(100.00);
        checkingAccount.withdrawFunds(10.00);

        assertEquals(null, 90.00, checkingAccount.getBalance(), DOUBLE_DELTA);
    }

    @Test(expected = IllegalStateException.class) // Todo: is good practice? Can this be asserted instead?
    public void testWithdrawalExceedsBalance() {
        customer.openAccount(checkingAccount);
        checkingAccount.depositFunds(10.00);
        checkingAccount.withdrawFunds(100.00);
    }
    
}
