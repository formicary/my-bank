package com.abc;

import java.util.Calendar;
import java.util.Date;

public class Transaction {

    private double amount;

    private Date transactionDate;

    public Transaction(double amount) {

        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }

    /**
     * Obtain the transaction timestamp.
     * @return The transaction timestamp.
     */

    public Date getTransactionDate () {

        return transactionDate;
    }

    public double getAmount () {
        return this.amount;
    }

}
