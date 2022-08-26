package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MaxiSavingsAccountTest {

    private final String TEST_CUSTOMER_NAME = "Josh";
    private final double DELTA = 1e-15;

    @Test
    public void testCalcInterestEarnedWithAmountLessThan1000() {
        Account account = AccountFactory.create(new Customer(TEST_CUSTOMER_NAME), AccountType.MAXI_SAVINGS);
        account.deposit(900.0);
        assertEquals(18.0, account.calcInterestEarned(), DELTA);
    }

    @Test
    public void testCalcInterestEarnedWithAmountGreaterThan1000LessThan2000() {
        Account account = AccountFactory.create(new Customer(TEST_CUSTOMER_NAME), AccountType.MAXI_SAVINGS);
        account.deposit(1900.0);
        assertEquals(65.0, account.calcInterestEarned(), DELTA);
    }

    @Test
    public void testCalcInterestEarnedWithAmountGreaterThan2000() {
        Account account = AccountFactory.create(new Customer(TEST_CUSTOMER_NAME), AccountType.MAXI_SAVINGS);
        account.deposit(12500.0);
        assertEquals(1120.0, account.calcInterestEarned(), DELTA);
    }
}