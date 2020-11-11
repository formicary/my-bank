package com.abc;

import com.abc.core.Account;
import com.abc.core.AccountType;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {

    private static final double DELTA = 1e-15;

    @Test
    public void customerCanDepositToAccount() {
        Account account = new Account(AccountType.CHECKING);
        account.deposit(20.5);

        assertEquals(1, account.getTransactions().size());
        assertEquals(20.5, account.getTransactions().get(0).getAmount(), DELTA);   // TODO: is it Ok to compare this way?
        assertNotNull(account.getTransactions().get(0).getTransactionDate());
    }

    @Test
    public void customerCanWithdrawFromAccount() {
        Account account = new Account(AccountType.CHECKING);
        account.deposit(100);
        account.withdraw(20.5);

        assertEquals(2, account.getTransactions().size());
        assertEquals(-20.5, account.getTransactions().get(1).getAmount(), DELTA);
        assertNotNull(account.getTransactions().get(1).getTransactionDate());
        assertEquals(79.5, account.sumOfTransactions(), DELTA);
        // TODO: add test - customer can withdraw, only if he has enough money
    }

    @Test
    public void checkingAccountInterest() {
        Account account = new Account(AccountType.CHECKING);
        account.deposit(100.0);

        assertEquals(100 * 0.001, account.interestEarned(), DELTA);
    }

    @Test
    public void savingsAccountInterestUnder1000Dollars() {
        Account account = new Account(AccountType.SAVINGS);
        account.deposit(500.0);

        assertEquals(500 * 0.001, account.interestEarned(), DELTA);
    }

    @Test
    public void savingsAccountInterestAbove1000Dollars() {
        Account account = new Account(AccountType.SAVINGS);
        account.deposit(1200.0);
        double expectedInterest = 0.001 * 1000 + 0.002 * 200;

        assertEquals(expectedInterest, account.interestEarned(), DELTA);
    }

    @Test
    public void savingsAccountInterestAt1000Dollars() {
        Account account = new Account(AccountType.SAVINGS);
        account.deposit(1000.0);

        assertEquals(1000 * 0.001, account.interestEarned(), DELTA);
    }

    @Test
    public void maxiSavingsAccountInterestUpTo1000Dollars() {
        Account account = new Account(AccountType.MAXI_SAVINGS);
        account.deposit(800.0);

        assertEquals(800 * 0.02, account.interestEarned(), DELTA);
    }

    @Test
    public void maxiSavingsAccountInterestBetween1000And2000Dollars() {
        Account account = new Account(AccountType.MAXI_SAVINGS);
        account.deposit(1500.0);
        double expectedInterest = 0.02 * 1000 + 0.05 * 500;

        assertEquals(expectedInterest, account.interestEarned(), DELTA);
    }

    @Test
    public void maxiSavingsAccountInterestAbove2000Dollars() {
        Account account = new Account(AccountType.MAXI_SAVINGS);
        account.deposit(2500.0);
        double expectedInterest = 0.02 * 1000 + 0.05 * 1000 + 0.1 * 500;

        assertEquals(expectedInterest, account.interestEarned(), DELTA);
    }
}
