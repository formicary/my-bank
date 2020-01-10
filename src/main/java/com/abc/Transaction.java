package com.abc;

import java.time.LocalDate;

public class Transaction {

    public final double amount; // Amount of transaction
    public final LocalDate transactionDate; // Date of transaction

    public Transaction(double amount, LocalDate date) {
        this.amount = amount;
        this.transactionDate = date;
    }

}
