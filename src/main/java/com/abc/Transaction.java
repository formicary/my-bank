package com.abc;

import com.abc.util.Money;

import java.util.Calendar;
import java.util.Date;

public class Transaction {
    public static final int CUSTOMER_DEPOSIT = 0;
    public static final int CUSTOMER_WITHDRAW = 1;
    public static final int BANK_INTEREST_DEPOSIT = 2;
    public static final int BANK_FEE = 3;


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
        String s = null;

        //Translate to pretty account type
        switch(transactionType){
            case CUSTOMER_DEPOSIT:
                s = "Customer Deposit\n";
                break;
            case CUSTOMER_WITHDRAW:
                s = "Customer Withdraw\n";
                break;
            case BANK_INTEREST_DEPOSIT:
                s = "Bank Interest Deposit\n";
                break;

            case BANK_FEE:
                s = "Bank Fee";
        }
        return s;
    }
}
