package com.abc.Accounts;

import com.abc.Transaction;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class MaxiSavingsAccount extends Account {

    public double interestEarned() {
        double amount = getBalance();

        return queryInterestIncrease() ? 0.05 * amount : 0.001 * amount;
    }

    public String getAccountType() {
        return "Maxi Savings Account\n";
    }

    private boolean queryInterestIncrease() {
        List<Transaction> transactions = getTransactions();

        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction transaction = transactions.get(i);

            if (transaction.getAmount() < 0.0) {
                // the current transaction is a withdrawal
                Instant currentTime = Instant.now();
                Instant transactionTime = transaction.getTransactionDate();
                return Math.abs(ChronoUnit.DAYS.between(currentTime, transactionTime)) > 10;

            }

        }
        // no withdrawals took place
        return true;
    }

}
