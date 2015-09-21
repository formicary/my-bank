package com.abc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Period;

public class Transaction {
    public final BigDecimal amount;

    private static LocalDateTime transactionDate;

    public Transaction(BigDecimal amount) {
        this.amount = amount;
        Transaction.transactionDate = LocalDateTime.now();
    }
    
    public static boolean sinceLast(){
    	Period period = getPeriod(transactionDate,LocalDateTime.now());
    	if(period.getDays() <= 10)
    		return false;
    	return true;
    }
    
    private static Period getPeriod(LocalDateTime dob, LocalDateTime now) {
        return Period.between(dob.toLocalDate(), now.toLocalDate());
    }
}
