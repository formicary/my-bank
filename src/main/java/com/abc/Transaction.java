package com.abc;

import java.util.Date;

import com.abc.DateUtils.DateProvider;

public class Transaction {
    public final double amount;

    private Date transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }

    /**
     * @return transaction date
     */
    public Date getTransactionDate() {
        return transactionDate;
    }

    /**
     * 
     * @return transaction amount
     */
    public double getTransactionAmount() {
        return amount;
    }

    /**
     * 
     * @param date
     * @return boolean
     *         True if provided date comes after current date
     *         False otherwise
     */

    public boolean isAfter(Date date) {

        return transactionDate.after(date);
    }

}
