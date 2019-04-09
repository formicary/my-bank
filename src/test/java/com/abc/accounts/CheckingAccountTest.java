package com.abc.accounts;

import com.abc.domain.Transaction;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CheckingAccountTest {

    private static final double DOUBLE_DELTA = 1e-15;

    private final List<Transaction> transactions = new ArrayList<>();
    private final AccountType uut = CheckingAccount.INSTANCE;

    @Test
    public void testName() {
        assertEquals("Checking Account", uut.getName());
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
    public void testInterest_100_dollar() {
        // given
        transactions.add(new Transaction(100.0d, LocalDate.now().minusWeeks(3)));
        final double balance = 100.0d;
        // when
        final double interest = uut.interestEarned(balance, transactions);
        // then
        assertEquals(0.1d, interest, DOUBLE_DELTA);
    }

}