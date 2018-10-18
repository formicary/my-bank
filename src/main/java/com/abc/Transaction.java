package com.abc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class Transaction {

    public static final int WITHDRAWAL = 0;
    public static final int DEPOSIT = 1;

    private final BigDecimal amount;
    private final int type;

    private LocalDateTime transactionDateTime;
    private LocalDate transactionDate;

    public Transaction(BigDecimal amount, DateProvider dateProvider) {
        this.amount = amount;
        this.transactionDateTime = dateProvider.getDateTime();
        this.transactionDate = dateProvider.getDate();
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

    public LocalDateTime getDateTime(){
        return transactionDateTime;
    }

    public LocalDate getDate(){
        return transactionDate;
    }

}
