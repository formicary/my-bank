package main.java.com.abc;

import java.util.Calendar;
import java.util.Date;

public class Transaction {
    public final double amount;

    private Date transactionDate;
    public String transactionType;

    public Transaction(double amount, String transactionType) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
        this.transactionType = transactionType;
    }

    public Date getTime(){
    	return transactionDate;
    }
}
