package com.abc;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountTest {

    private static final double DOUBLE_DELTA = 1e-15;

    private static Customer henry;
    private static Account checking;

    @BeforeClass
    public static void setup() {
        henry = new Customer("Henry");
        henry.openAccount(new Account(AccountTypes.CHECKING));
        checking = henry.getAccounts().get(0);
    }

    @Test
    public void testDeposit() {
        checking.deposit(200);
        assertEquals(200, checking.sumTransactions(), DOUBLE_DELTA);
    }

    // Demonstrates customer can be overdrawn
    @Test
    public void testWithdraw() {
        checking.withdraw(500);
        assertEquals(-500, checking.sumTransactions(), DOUBLE_DELTA);
    }
}
