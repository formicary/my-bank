package com.abc.accenture.financial.services;

import com.abc.accenture.financial.items.account.Account;
import com.abc.accenture.financial.items.account.AccountGenerator;
import org.junit.Test;

import java.util.Map;

import static com.abc.accenture.financial.items.account.AccountType.CHECKING;
import static com.abc.accenture.financial.items.account.AccountType.SAVINGS;
import static org.junit.Assert.*;

public class AccountServiceTest {
    private static final double DOUBLE_DELTA = 1e-15;

    private final AccountService accountService = new AccountServiceImpl(new AccountGenerator());

    @Test
    public void testCreateAccount() {
        Account resultAccount = accountService.createAccount(CHECKING);

        assertEquals(CHECKING, resultAccount.getAccountType());
    }

    @Test
    public void testDeposit() {
        Account account = accountService.createAccount(CHECKING);
        double expectedAmount = 100.0;

        accountService.deposit(account.getTransactions(), expectedAmount);

        assertEquals(1, account.getTransactions().size());
        assertEquals(expectedAmount, account.getTransactions().getFirst().amount(), DOUBLE_DELTA);
    }

    @Test
    public void testWithdraw() {
        Account account = accountService.createAccount(CHECKING);
        double baseAmount = 500.0;
        double diffAmount = 200.0;
        double expectedAmount = baseAmount - diffAmount;

        accountService.deposit(account.getTransactions(), baseAmount);
        accountService.withdraw(account.getTransactions(), diffAmount);

        assertEquals(2, account.getTransactions().size());
        assertEquals(expectedAmount, accountService.sumTransactions(account.getTransactions()), DOUBLE_DELTA);
    }

    @Test
    public void interestEarned() {
        Account account = accountService.createAccount(CHECKING);
        double baseAmount = 500.0;
        double diffAmount = 200.0;
        double expectedInterestEarned = 0.3;

        accountService.deposit(account.getTransactions(), baseAmount);
        accountService.withdraw(account.getTransactions(), diffAmount);
        double interestEarnedResult = accountService.interestEarned(account);

        assertEquals(expectedInterestEarned, interestEarnedResult, DOUBLE_DELTA);
    }


    @Test
    public void isAlreadyAccountName() {
        String expectedTrueNameOne = "Jill";
        String expectedTrueNameTwo = "Chris";
        String expectedFalseName = "Albert";
        Map<String, Account> accounts = Map.of(expectedTrueNameOne, accountService.createAccount(CHECKING),
                expectedTrueNameTwo, accountService.createAccount(SAVINGS));

        assertTrue(accountService.isAlreadyAccountName(expectedTrueNameOne, accounts));
        assertTrue(accountService.isAlreadyAccountName(expectedTrueNameTwo, accounts));
        assertFalse(accountService.isAlreadyAccountName(expectedFalseName, accounts));
    }
}