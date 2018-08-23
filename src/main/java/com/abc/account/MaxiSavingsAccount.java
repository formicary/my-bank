package com.abc.account;

import com.abc.transaction.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * The class {@code MaxiSavingsAccount} extends {@code Account} that represents a maxi savings
 * account
 */
public class MaxiSavingsAccount extends Account {

    /** Initialise a {@code MaxiSavingsAccount} */
    public MaxiSavingsAccount() {
        super();
    }

    @Override
    protected BigDecimal getDailyInterestEarned(final LocalDateTime day) {
        BigDecimal amount = getTransactionsSum();
        if (hasWithdrawalOccurredInLastTenDaysFromDay(day)) {
            return amount.multiply(convertToDailyRate(BigDecimal.valueOf(0.001)));
        }
        return amount.multiply(convertToDailyRate(BigDecimal.valueOf(0.05)));
    }

    private boolean hasWithdrawalOccurredInLastTenDaysFromDay(final LocalDateTime day) {
        return getTransactions()
                .stream()
                .filter(Transaction::isWithdrawal)
                .filter(
                        transaction ->
                                DAYS.between(transaction.getDate(), day) >= 0
                                        && DAYS.between(transaction.getDate(), day) <= 10)
                .findAny()
                .isPresent();
    }

    @Override
    public String getName() {
        return "Maxi Savings Account";
    }
}