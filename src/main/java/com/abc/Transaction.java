package com.abc;

import com.abc.Account.ACCOUNT_TYPE;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Transaction {
    private ACCOUNT_TYPE account_type;
    private final double amount;
    private Date transactionDate;
    private boolean isWithdrawal;

    public Transaction(ACCOUNT_TYPE account_type, double amount, boolean isWithdrawal) {
        this.account_type = account_type;
        this.amount = amount;
        this.transactionDate = Calendar.getInstance().getTime();
        this.isWithdrawal = isWithdrawal;
    }

    // Sets the date of the transaction to be daysAgo days ago
    public void setDateToNDaysAgo(short daysAgo) {
        if (daysAgo < 0 )
            throw new IllegalArgumentException("The number of days ago should be positive");

        int millisecondsInADay = 86400000; //24 hours * 60 minutes * 60 seconds * 1000 milliseconds
        transactionDate.setTime(transactionDate.getTime() - (daysAgo * millisecondsInADay));
    }

    // Returns the day of the transaction in the format:
    // DAY_OF_WEEK DAY_OF_MONTH MONTH YEAR HOURS:MINUTES AM/PM
    // Example - Tuesday 05 Feb 2019 11:16 AM
    public String getFormattedTransactionDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd MMM YYYY HH:mm a");
        return dateFormat.format(transactionDate);
    }

    public Date getTransactionDate() {
        return  transactionDate;
    }

    public double getTransactionAmount() {
        return amount;
    }

    public boolean isWithdrawal() {
        return  isWithdrawal;
    }
}
