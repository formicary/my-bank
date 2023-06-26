package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SavingsAccountTest {
    private static final double DOUBLE_DELTA = 1e-15;
    @Test
    public void should_returnAccountTypeAsSavings_when_savingsAccountIsCreated() {
        SavingsAccount savingsAccount = new SavingsAccount();
        assertEquals(savingsAccount.getAccountType(),AccountTypeEnum.SAVINGS);
    }
    @Test
    public void should_returnInterestAsTwo_when_1500IsDeposited() {
        Account savingsAccount = new SavingsAccount();
        savingsAccount.transact(1500.0);

        assertEquals(2.0, savingsAccount.interestEarned(), DOUBLE_DELTA);
    }
}
