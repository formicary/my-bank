package com.abc.account;

import com.abc.TransactionType;

import java.time.LocalDateTime;

public class MaxiSavingsAccount extends Account {
    private static final int WITHDRAWALS_DAYS = 10;

    @Override
    public String getType() {
        return "Maxi Savings";
    }

    /**
     * Change Maxi-Savings accounts to have an interest rate of 5% assuming no withdrawals in the past 10 days otherwise 0.1%
     */
    @Override
    public double getRateByDate(LocalDateTime date) {
        double rate;
        if (checkLastWithdrawalTransaction(date)) {
            rate = 5;
        } else {
            rate = 0.1;
        }
        return rate;
    }

    /**
     * Checks whether there was any withdrawal transaction withing last days
     */
    private boolean checkLastWithdrawalTransaction(LocalDateTime date) {
        return getTransactions().stream().noneMatch(transaction -> transaction.getTransactionType().equals(TransactionType.WITHDRAWAL)
            && transaction.getTransactionDate().isAfter(date.minusDays(WITHDRAWALS_DAYS)));
    }

}
