package com.abc;

import com.abc.accounts.Account;
import com.abc.accounts.CheckingAccount;
import com.abc.accounts.MaxiSavingsAccount;
import com.abc.accounts.SavingsAccount;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class AccountTest {
    private static final double DOUBLE_DELTA = 0.01;

    // Test expected values calculated manually using spreadsheet

    @Test
    public void maxSavingsInterest() {
        Account maxiAccount = new MaxiSavingsAccount();

        Date depositDate = DateProvider.getInstance().earlierDate(30);
        Transaction deposit = new Transaction(1000, depositDate);

        Date withdrawalDate = DateProvider.getInstance().earlierDate(3);
        Transaction withdrawal = new Transaction(-100, withdrawalDate);

        maxiAccount.addTransaction(deposit);
        maxiAccount.addTransaction(withdrawal);

        assertEquals(3.8501, maxiAccount.interestEarned(), DOUBLE_DELTA);

    }

    @Test
    public void checkingAccountInterest() {
        Account checkingAccount = new CheckingAccount();

        Date depositDate = DateProvider.getInstance().earlierDate(30);
        Transaction deposit = new Transaction(1000, depositDate);

        Date withdrawalDate = DateProvider.getInstance().earlierDate(15);
        Transaction withdrawal = new Transaction(-100, withdrawalDate);

        checkingAccount.addTransaction(deposit);
        checkingAccount.addTransaction(withdrawal);

        assertEquals(0.0808, checkingAccount.interestEarned(), 0.01);

    }

    @Test
    public void savingAccountInterest() {
        Account savingsAccount = new SavingsAccount();

        Date depositDate = DateProvider.getInstance().earlierDate(30);
        Transaction deposit = new Transaction(10000, depositDate);

        Date withdrawalDate = DateProvider.getInstance().earlierDate(15);
        Transaction withdrawal = new Transaction(-9500, withdrawalDate);

        savingsAccount.addTransaction(deposit);
        savingsAccount.addTransaction(withdrawal);

        assertEquals(0.853, savingsAccount.interestEarned(), 0.01);

    }

}
