package com.abc.accounts;

import com.abc.domain.Transaction;

import java.time.LocalDate;
import java.util.List;

/**
 * Maxi-saving Account Type.
 * Maxi-Savings accounts have an interest rate of 5% assuming no withdrawals in the past 10 days otherwise 0.1%.
 */
public class MaxiSavingsAccount implements AccountType {

    /**
     * Singleton instance.
     */
    public static AccountType INSTANCE = new MaxiSavingsAccount();

    private MaxiSavingsAccount() {
    }

    @Override
    public String getName() {
        return "Maxi Savings Account";
    }

    @Override
    public double interestEarned(double amount, List<Transaction> transactions) {
        if (!transactions.isEmpty()) {
            final LocalDate lastTransactionDate = getLastTransactionDate(transactions);
            final LocalDate today = LocalDate.now();
            if (!lastTransactionDate.isBefore(today.minusDays(10))) {
                return amount * 0.05;
            }
        }
        return amount * 0.001;
    }

    private LocalDate getLastTransactionDate(List<Transaction> transactions) {
        final Transaction lastTransaction = transactions.get(transactions.size() - 1);
        return lastTransaction.getDate();
    }

}
