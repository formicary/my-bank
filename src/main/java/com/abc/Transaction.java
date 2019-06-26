package com.abc;

import java.util.Calendar;
import java.util.Date;

public class Transaction {

    public static final int DEPOSIT = 0;
    public static final int WITHDRAWAL = 1;
    public static final int TRANSFER_IN = 2;
    public static final int TRANSFER_OUT = 3;

    public final int type;
    public final String typeString;
    public final double amount;

    private Date transactionDate;

    public Transaction(double amount, int type) {
        this.type = type;
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();

        this.typeString = determineType(type);
    }

    private static String determineType(int type) {

        switch(type){
            case DEPOSIT:
                return "deposit";
            case WITHDRAWAL:
                return "withdrawal";
            case TRANSFER_IN:
                return "transfer-in";
            default:
                return "transfer-out";
        }

    }

    public int getType(){
        return this.type;
    }

    public String getTypeString(){
        return this.typeString;
    }

}
