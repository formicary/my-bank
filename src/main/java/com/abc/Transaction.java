package com.abc;

import java.time.LocalDate;

public class Transaction {
    
    // Fields representing a transaction
    public final double amount;
    private final LocalDate transactionDate;

    /**
     * 
     * Creates a new instance of a transaction
     * 
     * @param amount the amount of money of the transaction
     */
    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = LocalDate.now();
    }
    
    public LocalDate getTransactionDate(){
        return transactionDate;
    }

}
