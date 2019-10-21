package com.abc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

class Transaction {
    final double AMOUNT;
    final Date TRANSACTION_DATE;

    Transaction(double amount) {
        this.AMOUNT = amount;
        this.TRANSACTION_DATE = DateProvider.getInstance().now();
    }

    Transaction(double amount, String customTransactionDate) throws ParseException {
        this.AMOUNT = amount;
        this.TRANSACTION_DATE = new SimpleDateFormat("E MMM d k:m:s z y").parse(customTransactionDate);
    }
}
