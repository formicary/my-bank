package com.abc.account;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class SavingsAccountShould {
    private static final double DOUBLE_DELTA = 1e-15;

    private Account account;

    @Before
    public void setUp() {
        account = new SavingsAccount();
    }

    @Test
    public void CalculateInterestCorrectly_GivenBalanceLessThan1000() {
        account.deposit(500);
        assertEquals(0.5, account.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void CalculateInterestCorrectly_GivenBalanceGreaterThan1000() {
        account.deposit(5000);
        assertEquals(9.0, account.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void ReturnCorrectPrettyAccountName() {
        assertEquals("Savings Account", account.getPrettyAccountType());
    }
}