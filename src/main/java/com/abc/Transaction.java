package com.abc;

import java.text.*;
import java.util.*;

public class Transaction {
    private final double amount;
    
    private Date transactionDate;
    
    
    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }

    public Date getTransactionDate(){
        return transactionDate;
    }
    
    public double getTransactionAmount(){
    	return amount;
    }
    
}
