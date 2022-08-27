package com.abc.account;

import com.abc.customer.Customer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CheckingAccountTest {

    private final String TEST_CUSTOMER_NAME = "Josh";
    private final double DELTA = 1e-15;

    @Test
    public void testCalcInterestEarned() {
        Account account = AccountFactory.create(new Customer(TEST_CUSTOMER_NAME), AccountType.CHECKING);
        account.deposit(1900.0);
        assertEquals(1.9, account.calcInterestEarned(), DELTA);
    }
}