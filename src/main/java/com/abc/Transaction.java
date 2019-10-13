package com.abc;

import java.math.BigDecimal;
import java.util.Date;

/**
 * A simple Transaction class used to store the amount and current date/time of a Transaction.
 * A Transaction represents money going into or out of an account.
 */
public class Transaction {
    //Common transactions
    public final static BigDecimal FIVE = new BigDecimal("5.00");
    public final static BigDecimal TEN = new BigDecimal("10.00");
    public final static BigDecimal FIFTEEN = new BigDecimal("15.00");
    public final static BigDecimal TWENTY = new BigDecimal("20.00");
    public final static BigDecimal FIFTY = new BigDecimal("50.00");
    public final static BigDecimal ONE_HUNDRED = new BigDecimal("100.00");

    //Actual amount transferred
    private final BigDecimal amount;
    //The current Date
    private final Date transactionDate;

    /**
     * Creates a new Transaction object with the given amount.
     *
     * @param amount the amount of the current transaction
     */
    public Transaction(BigDecimal amount) {
        // TODO: 10/10/2019 add Validation
        if (amount == null) {
            throw new IllegalArgumentException("amount must not be null");
        }
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

}
