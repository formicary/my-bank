package com.abc;

import java.util.Date;

/**
 * This is a Transaction object which is used to record the activity inside an Account.
 * @author Matthew Howard - <a href="mailto:m.o.howard@outlook.com">m.o.howard@outlook.com</a>
 */

public class Transaction {
    private final double amount;
    private Date transactionDate;

    /**
     * The constructor for this Transaction takes a double representing the money that is being processed
     * @param amount    This is a double that represents the amount of money that is being processed in this Transaction
     */
    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }

    /**
     * This function returns the Date that this Transaction occurred
     * @return A Date object timestamping when this Transaction occurred
     */
    public Date getTransactionDate(){
        return(transactionDate);
    }

    /**
     * This function returns the amount that was processed during this Transaction
     * @return A double representing the amount of money that was processed during this Transaction
     */
    public double getAmount(){
        return amount;
    }

}
