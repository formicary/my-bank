package com.abc;

import com.abc.util.Money;

import java.time.LocalDateTime;

public class Transaction {

    private final Money amount;
    private final LocalDateTime transactionDate;

    public Transaction(Money amount) {
        this.amount = amount;
        this.transactionDate = LocalDateTime.now();
    }

    public Money getAmount() {
        return amount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }


}
