package com.abc;

import java.util.Calendar;
import java.util.Date;

/**
 * A simple Transaction class used to store the amount and current date/time of a Transaction.
 * A Transaction represents money going into or out of an account.
 */
public class Transaction {
    // TODO: 10/10/2019 Change to BigInteger
    public final double amount;

    private Date transactionDate;   //can be final as the date will never change

    /**
     * Creates a new Transaction object with the given  amount.
     *
     * @param amount the amount of the current transaction
     */
    public Transaction(double amount) {
        // TODO: 10/10/2019 add Validation
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }

    // TODO: 11/10/2019 Add method to convert pennies to decimal e.g input: 150   output: £1.50

}
