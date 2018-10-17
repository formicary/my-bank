package com.abc;

import java.math.BigDecimal;
import java.time.LocalDate;


public class Transaction {

    public static final int WITHDRAWAL = 0;
    public static final int DEPOSIT = 1;

    private final BigDecimal amount;
    private final int type;

    private LocalDate transactionDate;

    public Transaction(BigDecimal amount) {
        this.amount = amount;
        this.transactionDate = LocalDate.now();
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
        return new CurrencyManager().roundBigDecimal(amount);
    }

    public LocalDate getDate(){
        return transactionDate;
    }

}
