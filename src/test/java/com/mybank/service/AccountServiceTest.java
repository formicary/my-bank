package com.mybank.service;

import com.mybank.entity.Account;
import com.mybank.entity.AccountType;
import com.mybank.util.CurrencyConverter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AccountServiceTest {

    private static final double DOUBLE_DELTA = 1e-3;
    private AccountService accountService = new AccountService();

    @Test
    public void testInterestEarnedCheckings100Yearly() {

        Account account = new Account(AccountType.CHECKING, Account.YEARLY_COMPOUNDED);
        double amount = 100.0;
        accountService.deposit(account, amount);
        assertEquals(amount * 0.001, accountService.interestEarned(account), DOUBLE_DELTA);
    }

    @Test
    public void testInterestEarnedCheckings100Daily() {

        Account account = new Account(AccountType.CHECKING, Account.DAILY_COMPOUNDED);
        double amount = 50000000.0;
        accountService.deposit(account, amount);

        assertEquals(50024.94, accountService.interestEarned(account), DOUBLE_DELTA);
    }

    @Test
    public void testInterestEarnedSavingsLessThan1000Yearly() {
        Account account = new Account(AccountType.SAVINGS, Account.YEARLY_COMPOUNDED);
        double amount = 500.0;
        accountService.deposit(account, amount);

        assertEquals(amount * 0.001, accountService.interestEarned(account), DOUBLE_DELTA);
    }

    @Test
    public void testInterestEarnedSavingsLessThan1000Daily() {
        Account account = new Account(AccountType.SAVINGS, Account.DAILY_COMPOUNDED);
        double amount = 500.0;
        accountService.deposit(account, amount);

        assertEquals(amount * 0.001, accountService.interestEarned(account), DOUBLE_DELTA);
    }

    @Test
    public void testInterestEarnedSavingsMoreThan1000Yearly() {
        Account account = new Account(AccountType.SAVINGS, Account.YEARLY_COMPOUNDED);
        double amount = 2500.0;
        accountService.deposit(account, amount);

        double interestEarned1 = Math.min(1000, amount) * 0.001;
        double interestEarned2 = (amount-1000) * 0.002;

        assertEquals((interestEarned1+interestEarned2),  accountService.interestEarned(account), DOUBLE_DELTA);
    }

    @Test
    public void testInterestEarnedSavingsMoreThan1000Daily() {
        Account account = new Account(AccountType.SAVINGS, Account.DAILY_COMPOUNDED);
        double amount = 25000000.0;
        accountService.deposit(account, amount);

        double interestEarned1 = Math.min(1000, amount) * 0.001;
        double interestEarned2 = 50047.89;

        assertEquals((interestEarned1+interestEarned2), accountService.interestEarned(account), DOUBLE_DELTA);
    }

    @Test
    public void testInterestEarnedMaxiSavingsNoWithdrawalsYearly() {
        Account account = new Account(AccountType.MAXI_SAVINGS, Account.YEARLY_COMPOUNDED);
        double amount = 10000.0;
        accountService.deposit(account, amount);

        double interestEarned = amount * 0.05;

        assertEquals(interestEarned, accountService.interestEarned(account), DOUBLE_DELTA);
    }

    @Test
    public void testInterestEarnedMaxiSavingsNoWithdrawalsDaily() {
        Account account = new Account(AccountType.MAXI_SAVINGS, Account.DAILY_COMPOUNDED);
        double amount = 10000.0;
        accountService.deposit(account, amount);

        assertEquals(512.67, accountService.interestEarned(account), DOUBLE_DELTA);
    }

    @Test
    public void testInterestEarnedMaxiSavingsLastWithdrawalTodayYearly() {
        Account account = new Account(AccountType.MAXI_SAVINGS, Account.YEARLY_COMPOUNDED);
        double amount = 10000.0;
        accountService.deposit(account, amount);
        accountService.withdraw(account, 1000.0);

        double interestEarned = (amount-1000.0) * 0.001;

        assertEquals(interestEarned, accountService.interestEarned(account), DOUBLE_DELTA);
    }

    @Test
    public void testInterestEarnedMaxiSavingsLastWithdrawalTodayDaily() {
        Account account = new Account(AccountType.MAXI_SAVINGS, Account.DAILY_COMPOUNDED);
        double amount = 10000000.0;
        accountService.deposit(account, amount);
        accountService.withdraw(account, 5000000.0);

        assertEquals(5002.49, accountService.interestEarned(account), DOUBLE_DELTA);
    }

    @Test
    public void testInterestEarned0Amount() {
        Account account = new Account(AccountType.MAXI_SAVINGS);
        double amount = 10000.0;
        accountService.deposit(account, amount);
        accountService.withdraw(account, amount);

        assertEquals(0.0, accountService.interestEarned(account), DOUBLE_DELTA);
    }

    @Test
    public void testDepositAmount() {
        Account account = new Account(AccountType.MAXI_SAVINGS);
        double amount1 = 5000.0;
        accountService.deposit(account, amount1);
        double amount2 = 6000.0;
        accountService.deposit(account, amount2);

        assertEquals((amount1+amount2), accountService.sumTransactions(account), DOUBLE_DELTA);
    }

    @Test
    public void testWithdrawAmount() {
        Account account = new Account(AccountType.MAXI_SAVINGS);
        double amount = 10000.0;
        accountService.deposit(account, amount);
        accountService.withdraw(account, 1000.0);

        assertEquals(9000.0, accountService.sumTransactions(account), DOUBLE_DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithdrawNoFunds() {
        Account account = new Account(AccountType.MAXI_SAVINGS);
        double amount = 10000.0;
        accountService.withdraw(account, amount);
    }

    @Test
    public void testTransfer() {
        Account accountChecking = new Account(AccountType.CHECKING);
        Account accountMaxi = new Account(AccountType.MAXI_SAVINGS);
        double amount = 10000.0;
        accountService.deposit(accountChecking, amount);
        accountService.transfer(accountChecking, accountMaxi, 500.0);
        assertEquals(9500.0, accountService.sumTransactions(accountChecking), DOUBLE_DELTA);
        assertEquals(500.0, accountService.sumTransactions(accountMaxi), DOUBLE_DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTransferNoFunds() {
        Account accountChecking = new Account(AccountType.CHECKING);
        Account accountMaxi = new Account(AccountType.MAXI_SAVINGS);
        double amount = 10000.0;
        accountService.deposit(accountChecking, amount);
        accountService.transfer(accountChecking, accountMaxi, 15000.0);
    }

    @Test
    public void testStatementForAccount() {
        Account account = new Account(AccountType.MAXI_SAVINGS);
        double amount = 10000.0;
        accountService.deposit(account, amount);
        accountService.withdraw(account, 1000.0);

        String statement = accountService.statementForAccount(account);
        assertTrue(statement.contains(AccountType.MAXI_SAVINGS.getName()));
        assertTrue(statement.contains("deposit " + CurrencyConverter.toDollars(10000.0)));
        assertTrue(statement.contains("withdrawal " + CurrencyConverter.toDollars(1000.0)));
        assertTrue(statement.contains("Total " + CurrencyConverter.toDollars(9000.0)));
    }
}
