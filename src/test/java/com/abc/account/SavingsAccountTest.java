package com.abc.account;

import com.abc.customer.Customer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SavingsAccountTest {

    private final String TEST_CUSTOMER_NAME = "Josh";
    private final double DELTA = 1e-15;

    @Test
    public void testCalcInterestEarnedWithAmountLessThan1000() {
        Account account = AccountFactory.create(new Customer(TEST_CUSTOMER_NAME), AccountType.SAVINGS);
        account.deposit(900.0);
        assertEquals(0.9, account.calcInterestEarned(), DELTA);
    }

    @Test
    public void testCalcInterestEarnedWithAmountGreaterThan1000() {
        Account account = AccountFactory.create(new Customer(TEST_CUSTOMER_NAME), AccountType.SAVINGS);
        account.deposit(1900.0);
        assertEquals(2.8, account.calcInterestEarned(), DELTA);
    }
}