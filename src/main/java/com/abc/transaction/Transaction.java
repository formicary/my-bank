package com.abc.transaction;

import java.util.Date;

import com.abc.helper.DateProvider;
import com.abc.helper.Strings;

/**
 * Stores a transaction for a given amount on a given date
 */
public class Transaction {

    public final double amount; // the transaction amount, in dollars
    private Date date;          // the date when the transaction took place

    /**
     * Constructor, requires the dollar amount involved
     * @param amount double representing the dollar amount of the transaction
     */
    public Transaction(double amount) {
        this.amount = amount;
        this.date = DateProvider.now();
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return String.format("%s %s", Strings.toDollars(amount), amount < 0 ? "withdrawal" : "deposit");
    }
}
