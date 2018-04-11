package com.abc;

import java.time.LocalDateTime;
import java.util.Date;

public class Transaction {
    private final double amount;
    // balance after this transaction
    private final double balance;
    private final LocalDateTime transactionDate;

    public Transaction(double amount, double balance, LocalDateTime time) {
        this.amount = amount;
        this.balance = balance;
        //this.transactionDate = DateProvider.getNow();
        // used for testing
        this.transactionDate = time;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getDate() {
        return transactionDate;
    }

    public int getDay(){
        return transactionDate.getDayOfYear();
    }

    public double getBalance() {
        return balance;
    }
}
