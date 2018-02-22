package com.abc;

import java.time.LocalDate;

public class Transaction {

    private double amount;
    private LocalDate date;

    public Transaction(double amount) {
        this.amount = amount;
        this.date = LocalDate.now();
    }

    // constructor with other than current date (only in case of interest earnings deposits)
    public Transaction(double amount, LocalDate date) {
        this.amount = amount;
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }
}