package com.abc;

import java.util.Date;

import static java.lang.Math.abs;

public class Transaction {

    private final double amount;

    private Date transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.now();
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public double getAmount() {
        return amount;
    }

    public static String toDollars(double amount){
        return String.format("$%,.2f", abs(amount));
    }

    public String getTransactionDetails(){
        String transactionType = amount < 0 ? "withdrawal" : "deposit";
        return transactionType + " " + toDollars(amount);
    }
}
