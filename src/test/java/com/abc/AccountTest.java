package com.abc;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class AccountTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void initialAmountTest(){
        Account account = new Account(AccountType.CHECKING);
        assertEquals(0.0, account.getCurrentAmount(), DOUBLE_DELTA);
    }

    @Test
    public void depositTest(){
        Account account = new Account(AccountType.CHECKING);

        account.deposit(1000.0);
        account.deposit(500.0);
        account.deposit(100.0);
        assertEquals(account.getCurrentAmount(), 1600.0, DOUBLE_DELTA);
    }

    @Test
    public void withdrawTest(){
        Account account = new Account(AccountType.CHECKING);

        account.deposit(1000.0);
        account.withdraw(100.0);
        account.withdraw(200.0);
        assertEquals(account.getCurrentAmount(), 700.0, DOUBLE_DELTA);
    }

    @Test
    public void withdrawExceptionTest(){
        Account account = new Account(AccountType.CHECKING);

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("amount must be greater than zero");

        account.withdraw(-9.0);

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("there are not sufficient fonds in this account");

        account.withdraw(100.0);
    }

    @Test
    public void sumTransactionsTest(){
        Account account = new Account(AccountType.CHECKING);

        account.deposit(1000.0);
        account.withdraw(100.0);
        account.withdraw(200.0);
        account.deposit(100.0);
        assertEquals(account.sumTransactions(), 800.0, DOUBLE_DELTA);
    }

    @Test
    public void checkingInterestTest() {
        Account account = new Account(AccountType.CHECKING);

        account.deposit(100.0);
        account.deposit(100.0);
        account.withdraw(50.0);

        assertEquals(0.15, account.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void savingsInterestTest() {
        Account account = new Account(AccountType.SAVINGS);

        account.deposit(100.0);
        account.deposit(100.0);
        account.withdraw(50.0);

        assertEquals(0.15, account.interestEarned(), DOUBLE_DELTA);

        account.deposit(1000.0);

        assertEquals(1.3, account.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void maxiSavingsInterestTest() {
        Account account = new Account(AccountType.MAXI_SAVINGS);

        account.deposit(100.0);
        account.deposit(100.0);
        account.withdraw(50.0);

        assertEquals(0.15, account.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void maxiSavingsNoWithdrawsInterestTest() {
        Account account = new Account(AccountType.MAXI_SAVINGS);

        account.deposit(100.0);
        account.deposit(50.0);

        assertEquals(7.5, account.interestEarned(), DOUBLE_DELTA);
    }
}
