package com.abc;

import java.util.Date;

/**
 * Class to handle transaction level functions
 */
public class Transaction {

    /**
     * Amount of the transaction
     */
    private final double amount;

    /**
     * Date of the transaction
     */
    private Date transactionDate;

    /**
     * Constructor of the Transaction class
     * @param amount amount of the transaciton
     */
    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }

    /**
     * Getter of the transaction date
     * @return transaction date
     */
    public Date getTransactionDate() {
        return transactionDate;
    }

    /**
     * Getter of the amount
     * @return amount of the transaction
     */
    public double getAmount() {
        return amount;
    }
}
