package com.abc.account;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class Transaction {

    private final double amount;
    private final TransactionType type;
    private final LocalDate transactionDate;

    public Transaction(double amount, TransactionType type) {
        this.amount = amount;
        this.type = type;
        this.transactionDate = LocalDate.now();
    }

}
