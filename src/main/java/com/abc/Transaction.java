package com.abc;

// import java.util.Calendar;
import java.util.Date;

import com.abc.Enums.TransactionType;

public class Transaction {
    public final double amount;
    private Date transactionDate;
    private TransactionType transactionType;
    public Transaction(double amount, TransactionType transactionType) {
        if (transactionType==TransactionType.WITHDRAW) {
            this.amount = -amount;
        }
        else this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
        this.transactionType = transactionType;
    }
    public double getAmount() {
        return amount;
    }
    public Date getTransactionDate() {
        return transactionDate;
    }
    public void setTransactionDate() {
        this.transactionDate =  DateProvider.getInstance().addDays(transactionDate, -20);
    }
    public TransactionType getTransactionType(){
        return transactionType;
    }

}
