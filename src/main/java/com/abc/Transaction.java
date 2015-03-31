package com.abc;

import java.util.Date;

/**
 *
 * @author R. Fei
 */
class Transaction {
    final double amount;
    final Date transactionDate;

    Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = new Date();
    }
    /*This constructor is for test only: date is added with Transactions to test 
      the calculation of accured interest */
    Transaction(double amount, Date myDate) {
        this.amount = amount;
        this.transactionDate = myDate;
    }    
}
