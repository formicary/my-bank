package com.abc;

import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

public class Transaction {
    private final double amount;
    private final Date transactionDate;
    
    public Transaction(double amount) {
        this.amount = amount;

        Date date = new Date();
        this.transactionDate = date;
    }

    public double getAmount() {
        return amount;
    }

    public Date getDate() {
        return transactionDate;
    }

    public boolean validateData() {
        Date now = new Date();
        if(amount == 0 || transactionDate.after(now)) {
            return false;
        }
        return true;
    }

}
