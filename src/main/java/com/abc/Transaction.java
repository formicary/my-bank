package com.abc;

import java.util.Date;

class Transaction {
    final long cents;
    Date transactionDate;

    Transaction(long cents) {
        this.cents = cents;
        this.transactionDate = DateProvider.getInstance().now();
    }
}
