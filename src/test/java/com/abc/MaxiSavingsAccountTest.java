package com.abc;

import org.junit.Test;
import sun.nio.cs.ext.MacThai;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class MaxiSavingsAccountTest {
    public static final LocalDate START = LocalDate.of(2020,1,1);
    public static final LocalDate END = LocalDate.of(2020,1,13);

    @Test
    public void interestEarnedNoWithdrawals() {
        Account account = new MaxiSavingsAccount();

        account.deposit(20000, START);
        assertEquals(32.9, account.getInterestEarned(END), 0.01);
    }

    @Test
    public void interestEarnedWithdrawal() {
        Account account = new MaxiSavingsAccount();

        account.deposit(20000, START);
        account.withdraw(10000, LocalDate.of(2020,1,2));

        assertEquals(4.38, account.getInterestEarned(END), 0.01);
    }

    @Test
    public void getAccountString() {
        Account account = new MaxiSavingsAccount();

        assertEquals("Maxi Savings Account\n", account.getAccountString());
    }
}