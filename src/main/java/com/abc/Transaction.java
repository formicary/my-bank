package com.abc;

import java.util.Calendar;
import java.util.Date;
import java.time.LocalDate;

public class Transaction {
    public final double amount;
    
    public final LocalDate transactionDate;
    
    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = LocalDate.now();
    }

}
