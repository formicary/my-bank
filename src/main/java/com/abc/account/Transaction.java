package com.abc.account;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class Transaction {

    private final double amount;
    private final LocalDate transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = LocalDate.now();
    }

}
