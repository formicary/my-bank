package com.abc;

import com.abc.DateProvider;

import com.abc.Money;;
import java.util.Calendar;
import java.util.Date;

public class Transaction {
    private static final String WITHDRAWAL = "withdrawal";
    private static final String DEPOSIT = "deposit";

    private final Money amount;
    private final Date transactionDate;
    private final String type;

    public Transaction(Money amount){
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
        this.type = (amount.compareTo(new Money("0")) < 0) ? WITHDRAWAL : DEPOSIT;
    }

    public String getType() {
        return type;
    }

    public Money getAmount(){
        return amount;
    };

}
