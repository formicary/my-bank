package com.abc;

/**
 * Created by dragos on 11/05/2017.
 */
import org.junit.Test;

import java.awt.peer.SystemTrayPeer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AccountTest {
    @Test
    public void testAccountCreation() {
        Account checkingAccount = new Account(0);
        assertTrue(checkingAccount.getAccountType().equals(AccountType.CHECKING));
        Account savingsAccount = new Account(AccountType.SAVINGS);
        assertTrue(savingsAccount.getAccountType().equals(AccountType.SAVINGS));
    }

    @Test
    public void testTransactions() {
        Account dailyAccount = new Account(0);
        dailyAccount.deposit(300.0);
        dailyAccount.deposit(500.0);
        assertTrue(dailyAccount.getBalance() == 800.0);
        dailyAccount.withdraw(700.0);
        assertTrue(dailyAccount.getBalance() == 100.0);
    }

    @Test
    public void interestAccrual() {
        Account dailyAccount = new Account(0);
        dailyAccount.deposit(1000.0);
        assertTrue(dailyAccount.interestEarned() == 1.0);
//        1.0 / 365
        double DAILY_INTEREST = 0.0027397260273972603;
        assertTrue(dailyAccount.accrueInterestDaily() == DAILY_INTEREST);
    }
}
