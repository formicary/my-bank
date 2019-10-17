package com.abc;

import java.util.Calendar;
import java.util.Date;

class Transaction {
    final double amount;

    private Date transactionDate;

    Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }
}
