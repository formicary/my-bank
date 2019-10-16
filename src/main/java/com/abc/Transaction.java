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
    public final static BigDecimal TWO_HUNDRED = new BigDecimal("200.00");

    //Actual amount transferred
    private final BigDecimal amount;
    //Date/Time of the transaction
    private final Date transactionDate;

    /**
     * Creates a new Transaction object with the given amount.
     *
     * @param amount the amount of the current transaction
     */
    public Transaction(BigDecimal amount) {
        if (amount == null) {
            throw new IllegalArgumentException("amount must not be null");
        }
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }

    /**
     * Get the amount of this transaction.
     *
     * @return transaction amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Date of transaction.
     *
     * @return date
     */
    public Date getTransactionDate() {
        return transactionDate;
    }

}
