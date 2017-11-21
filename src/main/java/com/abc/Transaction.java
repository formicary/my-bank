package com.abc;

import java.util.Date;

/**
 * Transaction Class represents a transaction.
 * @author Matthew Mukalere
 *
 */
public class Transaction {
    public final double amount;

    private Date transactionDate;

    /**
     * Transaction constructor.
     * @param amount The transaction amount
     */
    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }

}
