package com.abc;

import org.junit.Test;

import com.abc.Utilities.Enums.AccountType;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;

// Todo: add missing test cases to ensure full coverage. Consider global variable for e.g. deposit amount
public class AccountTest {
    private BigDecimal amountToDeposit;
    private BigDecimal amountToWithdraw;

    private Customer customer;
    private Account checkingAccount;

    @Before
    public void setup() {
        customer = new Customer("Jade");
        checkingAccount = new Account(AccountType.CHECKING);
        amountToDeposit = new BigDecimal(150.00);
        amountToWithdraw = new BigDecimal(10.00);
;    }

    @After
    public void tearDown() {
        customer = null;
        checkingAccount = null;
        amountToDeposit = null;
        amountToWithdraw = null;
    }

    @Test
    public void testDepositFunds() {
        customer.openAccount(checkingAccount);
        checkingAccount.depositFunds(new BigDecimal(150.00));
        BigDecimal expectedNewBalance = new BigDecimal(150.00);

        assertEquals(expectedNewBalance, checkingAccount.getBalance());
    }

    @Test(expected = IllegalArgumentException.class) // Todo: is good practice? Can this be asserted instead?
    public void testDepositFundsWithNegativeAmount() {
        customer.openAccount(checkingAccount);
        checkingAccount.depositFunds(amountToDeposit.negate());
    }

    @Test
    public void testWihdrawFunds() {
        customer.openAccount(checkingAccount);
        checkingAccount.depositFunds(amountToDeposit);
        checkingAccount.withdrawFunds(amountToWithdraw);
        BigDecimal expectedNewBalance = new BigDecimal(140.00);

        assertEquals(expectedNewBalance, checkingAccount.getBalance());
    }

    @Test(expected = IllegalStateException.class) // Todo: is good practice? Can this be asserted instead?
    public void testWithdrawalExceedsBalance() {
        customer.openAccount(checkingAccount);
        checkingAccount.depositFunds(amountToDeposit);
        checkingAccount.withdrawFunds(new BigDecimal(200.00));
    }
    
}
