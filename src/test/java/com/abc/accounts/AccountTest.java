package com.abc.accounts;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
public class AccountTest {
    private Account testAccount;
    private final double delta = 0.000001;

    @Before
    public void init() {
        testAccount = new Account() {};
    }

    @Test
    public void testDeposit() {
        testAccount.deposit(50);
        double expected = 50;
        double actual = testAccount.getBalance();
        assertEquals(expected, actual, delta);
    }
}
