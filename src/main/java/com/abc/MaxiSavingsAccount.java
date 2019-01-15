package com.abc;

import java.util.List;

public class MaxiSavingsAccount extends Account {
    private static final long TEN_DAYS = 10 * 24 * 60 * 60 * 1000;

    public double interestEarned() {
        double amount = getBalance();

        return shouldIncreaseInterestRate()? 0.05 * amount : 0.001 * amount;
    }

    public String getAccountType() {
        return "Maxi Savings Account\n";
    }

    private boolean shouldIncreaseInterestRate(){
        List<Transaction> transactions = getTransactions();

        for (int i = transactions.size() - 1; i >= 0; i--){
            Transaction transaction = transactions.get(i);

            if (transaction.getAmount() < 0.0){
                // the current transaction is a withdrawal
                long currentTime = DateProvider.now().getTime();
                long transactionTime = transaction.getTransactionDate().getTime();

                if (currentTime - transactionTime <= TEN_DAYS){
                    return false;
                } else {
                    return true;
                }

            }

        }
        // no withdrawals took place
        return true;
    }

}
