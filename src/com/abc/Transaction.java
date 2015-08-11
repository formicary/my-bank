package com.abc;

import java.util.Calendar;
import java.util.Date;

public class Transaction {
    public final double amount;

    private Date transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }
    public boolean before(int days) {
        /**
         * Returns true if the transaction occurred before x num days ago
         * @arg int - days
         */
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,(-1)*days);
        if(this.transactionDate.before(calendar.getTime()))
            return true;
        return false;
    }
    public Date getTransactionDate() { return transactionDate; }
    public double getAmount()        { return amount; }

}
