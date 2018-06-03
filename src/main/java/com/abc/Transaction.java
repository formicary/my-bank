package com.abc;


import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Transaction {

    //changed the data type to big decimal from double
    public final BigDecimal amount;

    private Date transactionDate;

    public Transaction(BigDecimal amount) {
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
