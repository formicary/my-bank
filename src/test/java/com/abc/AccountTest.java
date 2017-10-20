package com.abc;

import com.abc.accounts.Account;
import com.abc.accounts.MaxiSavingsAccount;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class AccountTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void maxiInterestRecentWithdrawal() {
        Account maxiAccount = new MaxiSavingsAccount();

        Date depositDate = DateProvider.getInstance().earlierDate(30);
        Transaction deposit = new Transaction(1000, depositDate);

        Date withdrawalDate = DateProvider.getInstance().earlierDate(3);
        Transaction withdrawal = new Transaction(-100, withdrawalDate);

        maxiAccount.addTransaction(deposit);
        maxiAccount.addTransaction(withdrawal);

        double expectedInterest = 0.001 * (1000 - 100);

        assertEquals(expectedInterest, maxiAccount.interestEarned(), DOUBLE_DELTA);

    }

    @Test
    public void maxiInterestWithoutRecentWithdrawal() {
        Account maxiAccount = new MaxiSavingsAccount();

        Date depositDate = DateProvider.getInstance().earlierDate(30);
        Transaction deposit = new Transaction(1000, depositDate);

        Date withdrawalDate = DateProvider.getInstance().earlierDate(15);
        Transaction withdrawal = new Transaction(-100, withdrawalDate);

        maxiAccount.addTransaction(deposit);
        maxiAccount.addTransaction(withdrawal);

        double expectedInterest = 0.05 * (1000 - 100);

        assertEquals(expectedInterest, maxiAccount.interestEarned(), DOUBLE_DELTA);

    }

}
