package com.abc;

import java.math.BigDecimal;
import org.joda.time.LocalDateTime;

/** Represents an account transaction.
 * @author James Rogers
 * @version 1.0
 * @since 1.0
*/
public class Transaction {
    
    /** Amount for this transaction. */
    private final BigDecimal amount;

    /** Date for this transaction. */
    private final LocalDateTime date;

    /** 
    * Creates a transaction with specified amount.
    * @param amount The amount for this transaction.
    */
    public Transaction(BigDecimal amount) {
        this.amount = amount;
        this.date = new LocalDateTime();
    }

    /**
    * Gets the amount for this transaction.
    * @return The amount.
    */
    public BigDecimal getAmount() {
        return amount;
    }
    
    /**
    * Gets the date for this transaction.
    * @return The date.
    */
    public LocalDateTime getDate() {
        return date;
    }
}
