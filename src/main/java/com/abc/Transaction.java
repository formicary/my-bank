package com.abc;

import java.util.Date;

/**
 * This is a Transaction object which is used to record the activity inside an Account.
 * @author Matthew Howard
 */

public class Transaction {
    public final double amount;


    private Date transactionDate;

    public Date getTransactionDate(){
        return(transactionDate);
    }

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }

}
