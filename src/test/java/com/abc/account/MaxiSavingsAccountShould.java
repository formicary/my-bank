package com.abc.account;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class MaxiSavingsAccountShould {
    private static final double DOUBLE_DELTA = 1e-15;

    private Account account;

    @Before
    public void setUp() {
        account = new MaxiSavingsAccount();
    }

    @Test
    public void CalculateInterestCorrectly_GivenBalanceLessThan1001() {
        account.deposit(500);
        assertEquals(10, account.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void CalculateInterestCorrectly_GivenBalanceGreaterThan1000AndLessThan2001() {
        account.deposit(1500);
        assertEquals(45.0, account.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void CalculateInterestCorrectly_GivenBalanceGreaterThan2000() {
        account.deposit(5000);
        assertEquals(370.0, account.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void ReturnCorrectPrettyAccountName() {
        assertEquals("Maxi Savings Account", account.getPrettyAccountType());
    }
}