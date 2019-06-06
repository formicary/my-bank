package com.abc;

import java.util.Date;

/* This class implements a transaction from an account.
There are three different types of transactions, Withdraw, Deposit and Interest.
These types were introduced in order to able to distinguish each transaction by type.
A transaction can not exist on it's own meaning that a transaction can only be performed
by an account instance.
There must to be an account reference in order for a transaction to take place.
A transaction has 4 attributes string transactionId, TranType transaction type,
date of transaction transaction date and the amount that was transferred.
@param String transactionId, double amount,Date transactionDate,TranType transactionType
@author Ioannis Kafantaris
 */


public class Transaction {
    public final double amount;
   private String transactionId;
    private Date transactionDate;
    private TranType transactionType;

    public enum TranType{WITHDRAW, DEPOSIT,INTEREST};

    public double getAmount() {
        return amount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public TranType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TranType transactionType) {
        this.transactionType = transactionType;
    }

    public Transaction(String transactionId, TranType transactionType, double amount) {
        checkTransactionId(transactionId);
        checkTransactionType(transactionType);
        this.amount = amount;
        this.transactionType=transactionType;
        this.transactionId = transactionId;
        this.transactionDate = DateProvider.getInstance().now();
    }

    private void checkTransactionType(TranType transactionType) {
        if (transactionType == null) {
            throw new IllegalArgumentException("com.abc.Transaction type must not be null");
        }
    }

    private void checkTransactionId(String transactionId) {
        if (transactionId == null || transactionId.length() <= 1)
            throw new IllegalArgumentException("The transaction id should never be null");
    }


}
