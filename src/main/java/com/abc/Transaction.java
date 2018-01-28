package com.abc;

import java.util.Date;

/**
 * This is a Transaction object which is used to record the activity inside an Account.
 * @author Matthew Howard - <a href="mailto:m.o.howard@outlook.com">m.o.howard@outlook.com</a>
 */

public class Transaction {
    private final double amount;


    private Date transactionDate;

    public Date getTransactionDate(){
        return(transactionDate);
    }

    public double getAmount(){
        return amount;
    }

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }

}
