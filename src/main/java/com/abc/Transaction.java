package com.abc;

import java.util.Calendar;
import java.util.Date;

public class Transaction {
    private final double amount;

    private Date transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }
    
    public String getTransactionType() {
        if (amount < 0) {
            return "withdrawal";
        } else {
            return "deposit";
        }
    }
    
    public double getAmount() {
        return amount;
    }

}
