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
        assertEquals(200, checking.getBalance(), DOUBLE_DELTA);
    }

    // Demonstrates customer can be overdrawn
    @Test
    public void testWithdraw() {
        checking.withdraw(500);
        assertEquals(-500, checking.getBalance(), DOUBLE_DELTA);
    }

    @Test
    public void interestEarned(){
        checking.deposit(500);
        assertEquals(0.5, checking.interestEarned(), DOUBLE_DELTA);

        Account saving = new Account(AccountTypes.SAVINGS);
        saving.deposit(500);
        assertEquals(0.5, saving.interestEarned(), DOUBLE_DELTA);
        saving.deposit(1000);
        assertEquals(2, saving.interestEarned(), DOUBLE_DELTA);

        Account maxi = new Account(AccountTypes.MAXI_SAVINGS);
        maxi.deposit(500);
        assertEquals(10, maxi.interestEarned(), DOUBLE_DELTA);
        maxi.deposit(1000);
        assertEquals(45, maxi.interestEarned(), DOUBLE_DELTA);
        maxi.deposit(1000);
        assertEquals(120, maxi.interestEarned(), DOUBLE_DELTA);
    }
}
