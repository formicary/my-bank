package com.abc;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MaxiSavingsAccount extends Account {

    public MaxiSavingsAccount() {
        super();
    }

    @Override
    public void addInterest() {
        double amount = currentBalance();
        if (amount <= 0) {
            return;
        }

        Date now = Utils.nowDateAndTime();
        List<Transaction> transactionsList = this.getTransactions();
        Transaction lastWithdrawal = null;

        /* Find the last withdrawal that was made on the account by looping through the
         transactions list, starting from the latest transaction. When one is found,
         store a reference to it and then break from the loop */
        for (int i = transactionsList.size() - 1; i >= 0; i--) {
            Transaction currentTransaction = transactionsList.get(i);
            if (currentTransaction.getAmount() < 0.0) {
                lastWithdrawal = currentTransaction;
                break;
            }
        }

        if (lastWithdrawal == null) {
            // No withdrawals ever, so interest rate is 5%
            depositInterest(amount * (0.05 / 365));
        } else {
            // Find the difference between now and the last withdrawal in days
            long timeDifference = now.getTime() - lastWithdrawal.getTransactionDate().getTime();
            long days = TimeUnit.DAYS.convert(timeDifference, TimeUnit.MILLISECONDS);

            if (days < 10) {
                // Last withdrawal is within 10 days, so interest rate is 0.1%
                depositInterest(amount * (0.001 / 365));
            } else {
                // No withdrawal in last 10 days, so interest rate is 5%
                depositInterest(amount * (0.05 / 365));
            }
        }
    }
}
