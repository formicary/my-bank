package com.abc;

import java.util.Calendar;
import java.util.Date;

/**
 * Holds the details of a transaction, including its type, amount
 */
public class Transaction {

    public static final int DEPOSIT = 0;
    public static final int WITHDRAWAL = 1;
    public static final int TRANSFER_IN = 2;
    public static final int TRANSFER_OUT = 3;

    private final int type;
    private final String typeString;
    public final double amount;

    protected Date transactionDate;

    public Transaction(double amount, int type) {
        this.type = type;
        this.amount = amount;
        this.transactionDate = Calendar.getInstance().getTime();

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
            case TRANSFER_OUT:
                return "transfer-out";
            default:
                throw new IllegalArgumentException("error: invalid transaction type used");
        }

    }

    // public int getType(){ return this.type; }

    // returns the type of transaction made (i.e. DEPOSIT, WITHDRAWAL, ETC)
    public String getTypeString(){
        return this.typeString;
    }

}
