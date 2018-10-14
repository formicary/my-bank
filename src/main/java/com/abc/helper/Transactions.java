package com.abc.helper;

import com.abc.transaction.Transaction;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * A helper class for dealing with Lists of Transaction Objects
 */
public class Transactions {

    /**
     * Function for abstracting the filtering of transactions
     * @param transactions the List of Transactions to filter
     * @param filter the Predicate instance to use for filtering Transactions
     * @return Transactions satisfying the predicate
     */
    private static List<Transaction> filter(List<Transaction> transactions, Predicate<Transaction> filter) {
        return transactions.parallelStream()
                .filter(filter)
                .collect(Collectors.toList());
    }

    /**
     * Function for abstracting the sorting of transactions
     * @param transactions the List of Transactions to sort
     * @param comparator the Comparator for sorting two given Transactions
     * @return Transactions sorted
     */
    private static List<Transaction> sort(List<Transaction> transactions, Comparator<Transaction> comparator) {
        return transactions.parallelStream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    /**
     * Returns a subset of a List of Transactions, only containing withdrawals
     */
    public static List<Transaction> findWithdrawals(List<Transaction> transactions) {
        return filter(transactions, transaction -> transaction.amount < 0);
    }

    /**
     * Returns a subset of a List of Transactions, only containing deposits
     */
    public static List<Transaction> findDeposits(List<Transaction> transactions) {
        return filter(transactions, transaction -> transaction.amount > 0);
    }

    /**
     * Returns a subset of a List of Transactions, which occurred in a given date range
     * @param transactions the List of Transactions to search through
     * @param lower the Date to search for Transactions following
     * @param higher the Date that returned Dates cannot come after
     */
    public static List<Transaction> findRecent(List<Transaction> transactions,
                                               Date lower,
                                               Date higher) {
        return filter(transactions, transaction -> transaction.getDate().after(lower) && transaction.getDate().before(higher));
    }

    /**
     * Returns a subset of a List of Transactions, which occurred after a given date
     * @param transactions the List of Transactions to search through
     * @param cutoff the latest Date to accept (so, returns transactions before this date)
     */
    public static List<Transaction> findRecent(List<Transaction> transactions,
                                               Date cutoff) {
        return filter(transactions, transaction -> transaction.getDate().after(cutoff));
    }

    /**
     * Returns a subset of a List of Transactions, which occurred on a given date
     * @param match the Date to search for Transactions that occurred on the same day of
     */
    public static List<Transaction> dateSearch(List<Transaction> transactions,
                                               Date match) {
        return filter(transactions, transaction -> {
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal1.setTime(match);
            cal2.setTime(transaction.getDate());
            return cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
                    cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
        });
    }

    /**
     * Returns a subset of a List of Transactions, which occurred withing a {lower, upper} date range
     * @param lower the earliest date to accept
     * @param upper the latest date to accept
     */
    public static List<Transaction> dateSearch(List<Transaction> transactions,
                                               Date lower, Date upper) {
        return filter(transactions, transaction ->
                transaction.getDate().after(lower) && transaction.getDate().before(upper)
        );
    }

    /**
     * Sorts a List of Transactions based on the date which they took place, in chronological order
     */
    public static List<Transaction> sortByDate(List<Transaction> transactions) {
        return sort(transactions, new Comparator<Transaction>() {
            @Override
            public int compare(Transaction o1, Transaction o2) {
                if (o1.getDate().before(o2.getDate()))      return -1;
                if (o1.getDate().after(o2.getDate()))       return 1;
                return 0;
            }
        });
    }

}
