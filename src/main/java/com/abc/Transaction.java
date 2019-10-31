package com.abc;


import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;


public final class Transaction {

    private final Instant transactionDate;
    private final double amount;
    public static final  DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime( FormatStyle.MEDIUM )
                                                        .withLocale( Locale.UK ).withZone( ZoneId.systemDefault() );


    /**
     *
     * @param amount cash in units of dollars, positive for deposits, negative for withdrawals
     */
    public Transaction(double amount){
        this(amount, Instant.now());
    }


    /**
     *For testing purposes, allow transactions to be created at future dates.
     */
    public Transaction(double amount, Instant transactionDate){
        this.transactionDate =  transactionDate;
        this.amount = amount;

    }

    @Override
    public String toString() {
        return (toDollars(this.amount) +(this.amount < 0 ? " withdrawal" : " deposit") +
                " on " + formatter.format(this.transactionDate));
    }

    public Instant getTransactionDate() {
        return transactionDate;
    }

    public double getAmount() {
        return amount;
    }
    public static String toDollars(double d){
        return String.format("$%,.2f", Math.abs(d));
    }




}
