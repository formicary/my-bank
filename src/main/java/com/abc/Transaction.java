package com.abc;

import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.math.BigDecimal;


public class Transaction {

    public static final int WITHDRAWAL = 0;
    public static final int DEPOSIT = 1;

    private final BigDecimal amount;
    private final int type;

    private Date transactionDate;

    public Transaction(BigDecimal amount) {
        this.amount = amount.setScale(2, RoundingMode.HALF_UP);
        this.transactionDate = DateProvider.getInstance().now();
        if (amount.compareTo(BigDecimal.ZERO)<0){
            type = WITHDRAWAL;
        }
        else{
            type = DEPOSIT;
        }

    }
    public int getType(){
        return type;
    }

    public BigDecimal getAmount(){
        return amount.setScale(2, RoundingMode.HALF_UP);
    }

    public Date getDate(){
        return transactionDate;
    }

}
