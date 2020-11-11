package com.abc.core;

import lombok.Getter;

import java.util.Date;

@Getter
public class Transaction {

    private final double amount;
    private final Date transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }

}
