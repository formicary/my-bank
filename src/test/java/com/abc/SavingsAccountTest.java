package com.abc;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class SavingsAccountTest {
    public static final LocalDate START = LocalDate.of(2020,1,1);
    public static final LocalDate END = LocalDate.of(2020,1,12);

    @Test
    public void interestEarned() {
        Account account = new SavingsAccount();
        account.deposit(20000, START);

        assertEquals(1.17, account.getInterestEarned(END), 0.01);
    }

    @Test
    public void interestEarned1000() {
        Account account = new SavingsAccount();
        account.deposit(1000, START);

        assertEquals(0.03, account.getInterestEarned(END), 0.01);
    }

    @Test
    public void getAccountString() {
        Account account = new SavingsAccount();
        assertEquals("Savings Account\n", account.getAccountString());
    }
}