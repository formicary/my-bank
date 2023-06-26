package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountTest {
    private static final double DOUBLE_DELTA = 1e-15;
    @Test
    public void should_returnBalanceAs10_when_10WasDepositedAnd20WasWithdrew (){
        Account account = new Account(AccountTypeEnum.CHECKING);
        account.transact(10);
        account.transact(-20);
        assertEquals(account.sumTransactions(),-10,DOUBLE_DELTA);
    }

    @Test
    public void should_returnAccountTypeAsSavings_when_savingsAccountWasCreated (){
        Account account = new Account(AccountTypeEnum.SAVINGS);
        assertEquals(account.getAccountType(),AccountTypeEnum.SAVINGS);
    }

}
