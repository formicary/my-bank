package com.abc;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class CheckingAccountTest {
    public static final LocalDate START = LocalDate.of(2020,1,1);
    public static final LocalDate END = LocalDate.of(2020,1,12);

    @Test
    public void interestEarned() {
        Account account = new CheckingAccount();

        account.deposit(20000, START);
        assertEquals(0.60, account.getInterestEarned(END), 0.01);
    }

    @Test
    public void getAccountString() {
        Account account = new CheckingAccount();
        assertEquals("Checking Account\n", account.getAccountString());
    }
}