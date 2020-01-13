package com.abc;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class AccountTest {
    private static final double DOUBLE_DELTA = 1e-15;

    @Test
    public void getInterestEarnedNoTransactions() {
        Account account = new CheckingAccount();

        assertEquals(0, account.getInterestEarned(LocalDate.now()), DOUBLE_DELTA);
    }
}