package com.abc.accounts;

import com.abc.domain.Transaction;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SavingsAccountTest {

    private static final double DOUBLE_DELTA = 1e-15;

    private final List<Transaction> transactions = new ArrayList<>();
    private final AccountType uut = SavingsAccount.INSTANCE;

    @Test
    public void testName() {
        assertEquals("Savings Account", uut.getName());
    }

    @Test
    public void testInterest_emptyAccount() {
        // given
        final double balance = 0.0d;
        // when
        final double interest = uut.interestEarned(balance, transactions);
        // then
        assertEquals(0.0d, interest, DOUBLE_DELTA);

    }

    @Test
    public void testInterest_500Dollars() {
        // given
        transactions.add(new Transaction(500.0d, LocalDate.now().minusWeeks(2)));
        final double balance = 500.0d;
        // when
        final double interest = uut.interestEarned(balance, transactions);
        // then
        assertEquals(0.5d, interest, DOUBLE_DELTA);
    }

    @Test
    public void testInterest_1000Dollars() {
        // given
        transactions.add(new Transaction(1000.0d, LocalDate.now().minusWeeks(2)));
        final double balance = 1000.0d;
        // when
        final double interest = uut.interestEarned(balance, transactions);
        // then
        assertEquals(1d, interest, DOUBLE_DELTA);
    }

    @Test
    public void testInterest_1001Dollars() {
        // given
        transactions.add(new Transaction(1001.0d, LocalDate.now().minusWeeks(2)));
        final double balance = 1001.0d;
        // when
        final double interest = uut.interestEarned(balance, transactions);
        // then
        assertEquals(1.002d, interest, DOUBLE_DELTA);
    }

    @Test
    public void testInterest_2000Dollars() {
        // given
        transactions.add(new Transaction(2000.0d, LocalDate.now().minusWeeks(2)));
        final double balance = 2000.0d;
        // when
        final double interest = uut.interestEarned(balance, transactions);
        // then
        assertEquals(3.0d, interest, DOUBLE_DELTA);
    }

}
