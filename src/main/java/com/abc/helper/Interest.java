package com.abc.helper;

import com.abc.customer.AccountType;
import com.abc.transaction.Transaction;

import java.util.Date;
import java.util.List;

/**
 * Helper class for calculating the interest yield for a given list of transactions and account type
 */
public class Interest {

    /**
     * Calculates the final amount of money for a given List of Transactions, and account type
     * @param transactions the List of Transactions (not necessarily in chronological order)
     * @param accountType the AccountType enum, used to determine the amount of interest to apply
     * @return the sum of daily, accrued interest generated from the transactions
     */
    public static double calculateYield(List<Transaction> transactions, AccountType accountType) {

        double amount = 0;  // store the accrued amount

        // handling empty Lists
        if (transactions.isEmpty()) return amount;

        // sort the Transactions into chronological order
        List<Transaction> chronologicalTransactions = Transactions.sortByDate(transactions);

        // get the starting Date for the Transactions
        Date startDate = chronologicalTransactions.get(0).getDate();

        // get the series of dates starting from the first Transaction to now
        List<Date> days = DateProvider.buildChronology(startDate, DateProvider.now());

        // iterate through each chronological day Date
        for (Date day : days) {

            // get the Transactions which occurred on this day
            List<Transaction> dayTransactions = Transactions.dateSearch(chronologicalTransactions, day);

            // add the Transaction amounts to the total amount
            amount += dayTransactions.stream().map(Transaction::getAmount).
                    reduce((aDouble, aDouble2) -> aDouble + aDouble2).get();

            // calculate the daily interest on the current total amount
            switch(accountType){

                case SAVINGS:
                    if (amount <= 1000)     amount *= 0.001;
                    else                    amount *= 1 + (amount-1000) * 0.002;

                case MAXI_SAVINGS:
                    amount += amount *
                            (Transactions.findRecent(transactions,
                                    DateProvider.offset(day, -10),
                                    day
                            ).isEmpty() ? 0.05 : 0.01 );

                default:

                    amount += amount * 0.001;
            }
        }

        return amount;
    }
}
