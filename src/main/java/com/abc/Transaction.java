package com.abc;

import java.util.Calendar;
import java.util.Date;

public class Transaction {

    public static final int WITHDRAWAL = 0;
    public static final int DEPOSIT = 1;

    public final double amount;
    public final double type;

    private Date transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
        if (amount<0){
            type = WITHDRAWAL;
        }
        else{
            type = DEPOSIT;
        }

    }
    public Date getDate(){
        return transactionDate;
    }

}
