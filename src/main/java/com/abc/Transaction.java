package com.abc;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class Transaction {
    private final double amount;
    private LocalDate transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = LocalDate.now();
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public double getAmount(){
        return amount;
    }

    public boolean isWithdrawal() {
        return amount < 0.0;
    }
}