package com.abc;

import java.time.LocalDate;

public class Transaction {
    public final double amount;

    public final LocalDate transactionDate;

    public Transaction(final double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.now();
    }

}
