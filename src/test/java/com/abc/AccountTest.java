package com.abc;

import com.abc.Accounts.Account;
import com.abc.Accounts.AccountCreator;
import com.abc.Accounts.AccountType;
import org.junit.Test;

import java.time.Instant;

import static org.junit.Assert.assertEquals;

public class AccountTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void testDepositCheckingAccountValidAmount() {
        Account account = AccountCreator.createAccount(AccountType.CHECKING);
        account.deposit(1000.0);
        assertEquals(1000.0, account.getBalance(), DOUBLE_DELTA);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testDepositCheckingAccountInvalidAmount() {
        Account account = AccountCreator.createAccount(AccountType.CHECKING);
        account.deposit(-1.0);

    }

    @Test
    public void testWithdrawalCheckingAccountValidAmount() {
        Account account = AccountCreator.createAccount(AccountType.CHECKING);
        account.deposit(1000.0);
        account.withdraw(200.0);
        assertEquals(800.0, account.getBalance(), DOUBLE_DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithdrawalCheckingAccountInvalidAmount() {
        Account account = AccountCreator.createAccount(AccountType.CHECKING);
        account.deposit(1000.0);
        account.withdraw(2000.0);
    }

    @Test
    public void testBalance() {
        Account savingsAccount = AccountCreator.createAccount(AccountType.SAVINGS);
        savingsAccount.deposit(1000.0);
        savingsAccount.deposit(2000.0);
        savingsAccount.withdraw(500.0);
        assertEquals(2500.0, savingsAccount.getBalance(), DOUBLE_DELTA);
    }


    @Test
    public void testInterestEarnedCheckingAccount() {
        Account account = AccountCreator.createAccount(AccountType.CHECKING);
        account.deposit(1000.0);
        assertEquals(1.0, account.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void testInterestEarnedSavingsAccountAmountSubThousand() {
        Account account = AccountCreator.createAccount(AccountType.SAVINGS);
        account.deposit(1000.0);
        assertEquals(1.0, account.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void testInterestEarnedSavingsAccountAmountOverThousand() {
        Account account = AccountCreator.createAccount(AccountType.SAVINGS);
        account.deposit(2000.0);
        assertEquals(3.0, account.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void testInterestEarnedMaxiSavingsAccountWithdrawalWithinTenDays() {
        Account maxiSavingsAccount = AccountCreator.createAccount(AccountType.MAXI_SAVINGS);
        maxiSavingsAccount.deposit(3000.0);
        maxiSavingsAccount.withdraw(1000.0);
        assertEquals(2.0, maxiSavingsAccount.interestEarned(), DOUBLE_DELTA);
    }


    @Test
    public void testInterestEarnedMaxiSavingsAccountNoWithdrawals() {
        Account maxiSavingsAccount = AccountCreator.createAccount(AccountType.MAXI_SAVINGS);
        maxiSavingsAccount.deposit(2000.0);
        assertEquals(100.0, maxiSavingsAccount.interestEarned(), DOUBLE_DELTA);
    }

    @Test
    public void testInterestEarnedMaxiSavingsAccountNoWithdrawalsWithinTenDays() {
        // Override the date set on the transaction object to test interest (Maxi Savings Account)
        Instant date = Instant.parse("2019-01-01T00:00:01.00Z");
        Account maxiSavingsAccount = AccountCreator.createAccount(AccountType.MAXI_SAVINGS);
        maxiSavingsAccount.deposit(2000.0);
        maxiSavingsAccount.getTransactions().get(0).setTransactionDate(date);
        maxiSavingsAccount.withdraw(1000.0);
        maxiSavingsAccount.getTransactions().get(1).setTransactionDate(date);
        assertEquals(50.0, maxiSavingsAccount.interestEarned(), DOUBLE_DELTA);
    }

}
