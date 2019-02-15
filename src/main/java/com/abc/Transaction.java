package com.abc;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {

    private final double amount;
    private Date transactionDate;
    private boolean isWithdrawable;

    public Transaction(int accountType , double amount, boolean isWithdrawable) {

        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
        this.isWithdrawable = isWithdrawable;
        checkNegativeTransaction();

    }

    public void checkNegativeTransaction() {
        if (amount<=0) {
            throw new IllegalArgumentException("The amount needs to be positive");
        }
    }


    public Date getTransactionDate() {
        return transactionDate;
    }

    public double getTransactionAmount() {
        return amount;
    }

    public boolean isWithdrawable() {
        return isWithdrawable;
    }

    // Sets the date of the transaction to be daysAgo days ago
    public void setDateToNDaysAgo(short daysAgo) {
        if (daysAgo < 0 )
            throw new IllegalArgumentException("The number of days ago should be positive");

        int millisecondsInADay = 86400000; //24 hours * 60 minutes * 60 seconds * 1000 milliseconds
        transactionDate.setTime(transactionDate.getTime() - (daysAgo * millisecondsInADay));
    }


    /**
     * Returns the day of the transaction in the format:
     * DAY_OF_WEEK DAY_OF_MONTH MONTH YEAR HOURS:MINUTES AM/PM
     * @return
     */
    public String getFormattedTransaction() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd MMM YYYY HH:mm a");
        return sdf.format(transactionDate);
    }

}
