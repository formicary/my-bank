package com.abc.domain;

import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

public class AccountTest {

    private static final double DOUBLE_DELTA = 1e-15;

    private final MockAccountType mockAccountType = new MockAccountType();

    private final Account uut = new Account(mockAccountType);

    @Test
    public void accountType() {
        assertSame(mockAccountType, uut.getAccountType());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getTransactionsReturnsUnmodifiableList() {
        // given
        uut.deposit(100.0d);
        // when
        final List<Transaction> transactions = uut.getTransactions();
        // then
        transactions.add(new Transaction(10.0d, LocalDate.now()));
    }

    @Test
    public void deposit() {
        // given
        uut.deposit(100.0d);
        // when
        final List<Transaction> transactions = uut.getTransactions();
        // then
        assertNotNull(transactions);
        assertEquals(transactions.size(), 1);

        final Transaction transaction = transactions.get(0);
        assertEquals(100.0d, transaction.getAmount(), DOUBLE_DELTA);
        // TODO This date comparison may fail around midnight. A mockable date provider bean could solve it.
        assertEquals(LocalDate.now(), transaction.getDate());
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullDeposit() {
        uut.deposit(0.0d);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeDeposit() {
        uut.deposit(-10.0d);
    }

    @Test
    public void withdraw() {
        // given
        uut.withdraw(41.5d);
        // when
        final List<Transaction> transactions = uut.getTransactions();
        // then
        assertNotNull(transactions);
        assertEquals(transactions.size(), 1);

        final Transaction transaction = transactions.get(0);
        assertEquals(-41.5d, transaction.getAmount(), DOUBLE_DELTA);
        // TODO This date comparison may fail around midnight. A mockable date provider bean could solve it.
        assertEquals(LocalDate.now(), transaction.getDate());
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullWithdraw() {
        uut.withdraw(0.0d);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeWithdraw() {
        uut.withdraw(-10.0d);
    }

    @Test
    public void interestEarned() {
        // given
        uut.deposit(100.0d);

        final double expectedInterest = 2.41d;

        mockAccountType.interest = expectedInterest;
        mockAccountType.balanceValidator = balance -> {
            assertEquals(100.0d, balance, DOUBLE_DELTA);
        };
        mockAccountType.transactionsValidator = transactions -> {
            assertNotNull(transactions);
            assertEquals(1, transactions.size());
            assertEquals(100.0d, transactions.get(0).getAmount(), DOUBLE_DELTA);
        };

        // when
        final double interest = uut.interestEarned();

        // then
        assertEquals(expectedInterest, interest, DOUBLE_DELTA);
    }

    @Test
    public void balance() {
        // given
        uut.deposit(20.0d);
        uut.deposit(30.0d);
        uut.withdraw(8.0d);
        // when
        final double balance = uut.getBalance();
        // then
        assertEquals(42.0d, balance, DOUBLE_DELTA);
    }

}