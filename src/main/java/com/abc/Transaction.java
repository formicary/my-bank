package com.abc;

import java.util.Date;

/* -- Transaction Class --
    Represents a transaction.

    double amount - The amount of the transaction

    dateTransactionCreated() - Returns date the transaction was made. 
*/
public class Transaction {
    public final double amount;
    private final DateProvider date_provider;

    private Date transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.date_provider = new DateProvider();
        this.transactionDate = this.date_provider.now();
    }

    public Date date_transaction_created() {
        return this.transactionDate;
    }
}
