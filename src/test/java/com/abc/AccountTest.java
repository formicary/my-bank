package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void customerSummary() {
        Account account = AccountFactory.getInstance().createAccount(Account.CHECKING);
        account.deposit(1000);
        account.withdraw(1100);
    }

}
