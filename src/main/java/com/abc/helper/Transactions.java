package com.abc.helper;

import com.abc.transaction.Transaction;

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
        return transactions.stream()
                .filter(filter)
                .collect(Collectors.toList());
    }

    /**
     * Returns a subset of a List of Transactions, only containing withdrawals
     * @param transactions the List of Transactions to filter down
     * @return Transactions with negative amount fields
     */
    public static List<Transaction> findWithdrawals(List<Transaction> transactions) {
        return filter(transactions, transaction -> transaction.amount < 0);
    }

    /**
     * Returns a subset of a List of Transactions, only containing deposits
     * @param transactions the List of Transactions to filter down
     * @return Transactions with positive amount fields
     */
    public static List<Transaction> findDeposits(List<Transaction> transactions) {
        return filter(transactions, transaction -> transaction.amount > 0);
    }

    /**
     * Returns a subset of a List of Transactions, which occurred after a given date
     * @param transactions the List of Transactions to filter down
     * @param cutoff the Data to search for Transactions following
     * @return Transactions that came after the cutoff date
     */
    public static List<Transaction> findRecent(List<Transaction> transactions, Date cutoff) {
        return filter(transactions, transaction -> transaction.getDate().after(cutoff));
    }
}
