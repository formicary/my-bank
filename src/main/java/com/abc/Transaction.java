package com.abc;


import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Transaction {
    public final double amount;

    private Date transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Date getTransactionDatePlus10Days(){
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(transactionDate);
        cal.add(Calendar.DATE, 10);
        return cal.getTime();
    }
}
