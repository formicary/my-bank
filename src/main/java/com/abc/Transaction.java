package com.abc;

import java.util.Calendar;
import java.util.Date;

public class Transaction {
    public final double amount;

    private Date transactionDate;

    /**
     * Transaction class constructor. Initialises amount and transactionDate
     * @param amount Double value of amount to be transacted.
     */
    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }

}
