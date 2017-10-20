package com.abc.accounts;

import com.abc.DateProvider;

import java.util.Collections;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CheckingAccount extends Account {

    private static double FLAT_INTEREST_RATE = 0.001;
    private static double DAILY_INTEREST_RATE = FLAT_INTEREST_RATE / 365.0;

    public CheckingAccount() {
        super();
    }

    public double interestEarned() {
        Collections.sort(getTransactions());

        Date firstTransactionDate = getTransactions().get(0).transactionDate;
        long daysSinceFirstTransaction = TimeUnit.DAYS.convert(
                DateProvider.getInstance().now().getTime()
                        - firstTransactionDate.getTime(),
                TimeUnit.MILLISECONDS);

        double balance = 0.0;
        double totalInterest = 0.0;

        int transactionCount = 0;
        long nextTransactionDay = 0;

        int i = 0;
        // Loop over all days between the first transaction and now
        while (i <= daysSinceFirstTransaction) {
            // If 'i' is a day with a transaction, add the transaction amount to the balance
            if (i == nextTransactionDay) {
                balance += getTransactions().get(transactionCount).amount;
                transactionCount++;
                // Set the day counter for the next coming transaction.
                if (transactionCount <= getTransactions().size() - 1) {
                    nextTransactionDay = TimeUnit.DAYS.convert(
                            (getTransactions().get(transactionCount).transactionDate.getTime() -
                                    firstTransactionDate.getTime()),
                            TimeUnit.MILLISECONDS);
                }
            }
            double dayInterest = balance * DAILY_INTEREST_RATE;
            totalInterest += dayInterest;
            balance += dayInterest;
            i++;
        }

        return totalInterest;
    }

    protected String getPrettyAccountType() {
        return "Checking Account\n";
    }

}
