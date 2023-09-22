package com.abc;

import org.junit.Test;

import com.abc.Account.Account;
import com.abc.Account.CheckingAccount;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;

public class AccountTest {
    private BigDecimal amountToDeposit;
    private BigDecimal amountToWithdraw;

    private Customer customer;
    private Account checkingAccount;

    @Before
    public void setup() {
        customer = new Customer("Jade");
        checkingAccount = new CheckingAccount();
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
        checkingAccount.depositFunds(amountToDeposit);
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
