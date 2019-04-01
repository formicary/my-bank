package com.abc;

import java.time.LocalDateTime;
import lombok.Data;

/**
 * Class to represent a transaction which can be made on an account.
 */
@Data
public class Transaction {
    private final double amount;

    private final LocalDateTime transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = LocalDateTime.now();
    }

    public String getType() {
        return this.amount < 0 ? "withdrawal" : "deposit";
    }

}
