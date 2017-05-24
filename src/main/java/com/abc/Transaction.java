package com.abc;

import java.util.Calendar;
import java.util.Date;

public class Transaction {
    public final double amount;
    public final int transactionType; // 0=withdraw 1=deposit 2=transfer

    public Date transactionDate;

    public Transaction(double amount, boolean payment) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
        if(!payment)
            this.transactionType = 2;
        else
            if(amount >= 0)
                this.transactionType = 1;
            else
                this.transactionType = 0;
    }

}
