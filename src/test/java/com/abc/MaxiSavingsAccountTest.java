package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MaxiSavingsAccountTest {
    private static final double DOUBLE_DELTA = 1e-15;
    @Test
    public void should_returnAccountTypeAsMaxiSavings_when_maxiSavingsAccountIsCreated() {
        Account maxiSavingsAccount = new MaxiSavingsAccount();
        assertEquals(maxiSavingsAccount.getAccountType(),AccountTypeEnum.MAXI_SAVINGS);
    }
    @Test
    public void should_returnInterestAs170_when_3000IsDeposited() {
        Account maxiSavingsAccount = new MaxiSavingsAccount();
        maxiSavingsAccount.transact(3000.0);

        assertEquals(170.0, maxiSavingsAccount.interestEarned(), DOUBLE_DELTA);
    }
}
