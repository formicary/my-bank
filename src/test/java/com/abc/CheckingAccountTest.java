package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CheckingAccountTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void should_returnAccountTypeAsChecking_when_checkingAccountIsCreated() {
        Account checkingAccount = new CheckingAccount();
        assertEquals(checkingAccount.getAccountType(),AccountTypeEnum.CHECKING);
    }

    @Test
    public void should_returnInterestEarnedAsOne_when_aHundredIsDeposited() {
        Account checkingAccount = new CheckingAccount();
        checkingAccount.transact(100.0);

        assertEquals(0.1, checkingAccount.interestEarned(), DOUBLE_DELTA);
    }
}
