package com.abc;

import java.util.Calendar;
import java.util.Date;

class Transaction {
    final double amount;

    Transaction(double amount) {
        this.amount = amount;
        Date transactionDate = DateProvider.getInstance().now();
    }
}
