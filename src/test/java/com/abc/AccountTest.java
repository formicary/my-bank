package com.abc;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class AccountTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void testDepositInCheckingAccountWithValidAmount(){
        Account account = AccountFactory.createAccount(AccountType.CHECKING);
        account.deposit(1000.0);
        assertEquals(1000.0, account.getBalance(), DOUBLE_DELTA);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testDepositInCheckingAccountWithInvalidAmount(){
        Account account = AccountFactory.createAccount(AccountType.CHECKING);
        account.deposit(-1.0);

    }

    @Test
    public void testWithdrawalInCheckingAccountWithValidAmount(){
        Account account = AccountFactory.createAccount(AccountType.CHECKING);
        account.deposit(1000.0);
        account.withdraw(200.0);
        assertEquals(800.0, account.getBalance(), DOUBLE_DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithdrawalInCheckingAccountWithInvalidAmount(){
        Account account = AccountFactory.createAccount(AccountType.CHECKING);
        account.deposit(1000.0);
        account.withdraw(2000.0);
    }

    @Test
    public void testBalance() {
        Account savingsAccount =AccountFactory.createAccount(AccountType.SAVINGS);
        savingsAccount.deposit(1000.0);
        savingsAccount.deposit(2000.0);
        savingsAccount.withdraw(500.0);
        assertEquals(2500.0, savingsAccount.getBalance(), DOUBLE_DELTA);
    }


    @Test
    public void testInterestEarnedOnCheckingAccount(){
        Account account = AccountFactory.createAccount(AccountType.CHECKING);
        account.deposit(1000.0);
        assertEquals(1.0, account.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void testInterestEarnedOnSavingsAccountForAmountLessThanThousand(){
        Account account = AccountFactory.createAccount(AccountType.SAVINGS);
        account.deposit(1000.0);
        assertEquals(1.0, account.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void testInterestEarnedOnSavingsAccountForAmountGreaterThanThousand(){
        Account account = AccountFactory.createAccount(AccountType.SAVINGS);
        account.deposit(2000.0);
        assertEquals(3.0, account.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void testInterestEarnedOnMaxiSavingsAccountWhenWithdrawalIsMadeWithin10Days() {
        Account maxiSavingsAccount = AccountFactory.createAccount(AccountType.MAXI_SAVINGS);
        maxiSavingsAccount.deposit(3000.0);
        maxiSavingsAccount.withdraw(1000.0);
        assertEquals(2.0, maxiSavingsAccount.interestEarned(), DOUBLE_DELTA);
    }


    @Test
    public void testInterestEarnedOnMaxiSaveAccountWithNoWithdrawals() {
        Account maxiSavingsAccount = AccountFactory.createAccount(AccountType.MAXI_SAVINGS);
        maxiSavingsAccount.deposit(2000.0);
        assertEquals(100.0, maxiSavingsAccount.interestEarned(), DOUBLE_DELTA);
    }









}
