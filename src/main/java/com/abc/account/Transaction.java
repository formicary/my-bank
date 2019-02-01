package com.abc.account;

import java.time.LocalDate;

import static java.lang.Math.abs;

public class Transaction {

    public final double amount;
    private LocalDate transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = LocalDate.now();
    }

    public LocalDate getDate() {
      return transactionDate;
    }

    public static String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}
