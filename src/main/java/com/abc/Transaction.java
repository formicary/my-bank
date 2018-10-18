package com.abc;

import com.abc.util.DateProvider;
import com.abc.util.Money;

import java.util.Date;

public class Transaction {
    public static final int CUSTOMER = 0; // Customer deposited or withdrew money
    public static final int BANK = 1; // Bank deposited or withdrew money (e.g. deposit money as interest earned)
    public static final int TRANSFER = 2; // Customer transfers from one account to another


    private final Money amount;

    private final Date transactionDate;

    private final int transactionType;

    public Transaction(Money amount, int transactionType) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
        this.transactionType = transactionType;
    }

    public Money getAmount() {
        return amount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public int getTransactionType() {
        return transactionType;
    }

    /**
     * transactionType as string
     * @return
     */
    public String getTransactionTypeString(){

        if (transactionType == CUSTOMER) {
            return "Customer";
        }else {
            return "Bank";
        }
    }
}
