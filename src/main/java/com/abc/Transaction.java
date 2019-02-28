package com.abc;

import java.time.LocalDate;

import static java.lang.Math.abs;

public class Transaction {

    public final double amount;
    public final LocalDate date;

    public Transaction(double amount, LocalDate date) {
        this.amount = amount;
        this.date = date;
    }

    public Transaction(double amount) {
        this(amount, LocalDate.now());
    }

    public String getType() {
        return (this.amount < 0 ? "withdrawal" : "deposit");
    }

    public String toString() {
        return this.getType() + " " + toDollars(this.amount);
    }

    public static String toDollars(double d) {
        return String.format("$%,.2f", abs(d));
    }

}
