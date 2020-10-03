package com.abc.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Transaction interface for creating a transaction
 * @author aneesh
 */
public interface Transaction {

    /**
     * Obtain the date and time the transaction was made
     * @return transaction date and time
     */
    LocalDateTime getTransactionDate();

    /**
     * Obtain the amount the transaction is for.
     * @return transaction value
     */
    BigDecimal getAmount();

}
