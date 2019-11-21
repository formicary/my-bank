package com.abc;

import java.util.Date;
import java.util.List;

class MaxiSavingsAccount extends Account {

    MaxiSavingsAccount() {
        super(AccountType.MAXI_SAVINGS);
    }

    @Override
    double calcInterest() {
        Transaction withdrawal = lastWithdrawal();
        Date now = DateProvider.getInstance().now();
        long tenDaysMillis = 10 * 24 * 60 * 60 * 1000;

        Date withdrawalDate = (withdrawal == null) ? now : withdrawal.getTransactionDate();

        // Checking if 10 days has passed since the last withdrawal
        if (withdrawalDate.getTime() - now.getTime() < tenDaysMillis) return getBalance() * 0.05;
        else return getBalance() * 0.001;
    }

    @Override
    String genStatement() {
        return genStatement("Maxi-Savings Account");
    }

    /**
     * Finds the last withdrawal made from this account.
     * @return the most recent transaction which was a withdrawal
     */
    private Transaction lastWithdrawal() {
        List<Transaction> reversedTransactions = getTransactions();

        for (Transaction transaction : reversedTransactions) {
            if (transaction.getAmount() < 0) return transaction;
        }

        return null;
    }
}
