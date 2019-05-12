package com.abc;

import java.util.Calendar;
import java.util.Date;

public class Transaction {
    public final double amount;
    public static final int DEPOSIT = 0;
    public static final int WITHDRAWAL = 1;

    /*
        Two main type of transactions; deposit and withdrawal.
        The 'transactionalType' variable is used to differentiate between the two types.
        The purpose of this variable is to determine the type of transaction when applying interest.
     */
    private final int transactionalType;
    private Date transactionDate;

    public Transaction(double amount, int transactionType) {
        this.amount = amount;
        this.transactionalType = transactionType;
        this.transactionDate = DateProvider.getInstance().now();
    }

    public Date getTransactionDate(){
        return transactionDate;
    }

    public void setTransactionDate(Date newDate){
        transactionDate = newDate;
    }

    public int getTransactionalType(){
        return transactionalType;
    }

}
