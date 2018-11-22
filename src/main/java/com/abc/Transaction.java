package com.abc;

import java.math.BigDecimal;
import java.util.Date;

public class Transaction {
    // Changed to private for encapsulation and used BigDecimal to avoid
    // problems that come with the double's inability to represent base 10 numbers.
    private final BigDecimal amount;

    // Changed to final for security so it cannot be changed.
    private final Date transactionDate;

    public Transaction(BigDecimal amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getNow();
    }

    // Added for easier construction with double
    public Transaction(double amount) {
        this.amount = BigDecimal.valueOf(amount);
        this.transactionDate = DateProvider.getNow();
    }

    // Added for easier construction with int
    public Transaction(int amount) {
        this.amount = BigDecimal.valueOf(amount);
        this.transactionDate = DateProvider.getNow();
    }

    // Generated getter to be able to read the values so that they have a purpose.
    public BigDecimal getAmount() {
        return amount;
    }

    // Generated getter to be able to read the values so that they have a purpose.
    public Date getTransactionDate() {
        return transactionDate;
    }

}
