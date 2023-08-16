package com.abc.DateUtils;

import java.util.Date;
import java.util.List;

import com.abc.Transaction;

public class DateChecker {
    public boolean isWithinLast10Days(Date transactionDate) {

        Date today = new Date();
        long differenceInMillis = today.getTime() - transactionDate.getTime();
        long tenDaysInMillis = 10 * 24 * 60 * 60 * 1000;

        return differenceInMillis <= tenDaysInMillis;
    }

    public boolean hasTransactionsWithinLastTenDays(List<Transaction> transactions) {
        Date today = new Date();

        for (Transaction transaction : transactions) {
            Date transactionDate = transaction.getTransactionDate();
            if (isWithinLast10Days(transactionDate) && transaction.amount <= 0) {
                return true;
            }
        }

        return false;
    }
}
