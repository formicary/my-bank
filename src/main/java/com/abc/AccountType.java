package com.abc;

import java.time.LocalDate;
import java.util.List;

public enum AccountType {
    CHECKING("Checking") {
        @Override
        public double calculateInterestEarned(double balance, List<Transaction> transactions) {
            return balance * 0.001;
        }
    }, SAVINGS("Savings") {
        @Override
        public double calculateInterestEarned(double balance, List<Transaction> transactions) {
            if (balance <= 1000) {
                return balance * 0.001;
            }
            return 1 + (balance - 1000) * 0.002;
        }
    }, MAXI_SAVINGS("Maxi Savings") {
        @Override
        public double calculateInterestEarned(double balance, List<Transaction> transactions) {
            Transaction lastWithdraw = null;
            LocalDate withdrawThreshold = DateProvider.now().minusDays(10);
            for (Transaction t: transactions) {
                if (t.amount < 0) {
                    lastWithdraw = t;
                    break;
                }
            }
            if (lastWithdraw == null || lastWithdraw.transactionDate.isBefore(withdrawThreshold)) {
                return balance * 0.05;
            }
            return balance * 0.001;
        }
    };

    public final String readableName;

    AccountType(final String readableName) {
        this.readableName = readableName;
    }

    public abstract double calculateInterestEarned(double balance, List<Transaction> transactions);
}
