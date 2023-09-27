package com.abc;

import org.junit.Test;

import com.abc.account.Account;
import com.abc.account.CheckingAccount;

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

        customer.openAccount(checkingAccount);
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
        checkingAccount.depositFunds(amountToDeposit);
        BigDecimal expectedNewBalance = new BigDecimal(150.00);

        assertEquals(expectedNewBalance, checkingAccount.getBalance());
    }

    @Test
    public void testDepositFundsGeneratesTransaction() {
        checkingAccount.depositFunds(amountToDeposit);
        assertEquals(1, checkingAccount.getTransactions().size());
    }

    @Test(expected = IllegalArgumentException.class) // Todo: is good practice? Can this be asserted instead?
    public void testDepositFundsWithNegativeAmount() {
        checkingAccount.depositFunds(amountToDeposit.negate());
    }

    @Test
    public void testWihdrawFunds() {
        checkingAccount.depositFunds(amountToDeposit);
        checkingAccount.withdrawFunds(amountToWithdraw);
        BigDecimal expectedNewBalance = new BigDecimal(140.00);

        assertEquals(expectedNewBalance, checkingAccount.getBalance());
    }

    @Test
    public void testWihdrawFundsGeneratesTransaction() {
        checkingAccount.depositFunds(amountToDeposit);
        checkingAccount.withdrawFunds(amountToWithdraw);

        assertEquals(2, checkingAccount.getTransactions().size());
    }

    @Test(expected = IllegalStateException.class)
    public void testWithdrawalExceedsBalance() {
        checkingAccount.depositFunds(amountToDeposit);
        checkingAccount.withdrawFunds(new BigDecimal(200.00));
    }
    
}
