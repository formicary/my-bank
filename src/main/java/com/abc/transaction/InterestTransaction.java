package com.abc.transaction;

import java.math.BigDecimal;

/**
 * The class {@code InterestTransaction} extends {@code Transaction} and represents a transaction
 * that increased an {@code Account}'s interest.
 */
public class InterestTransaction extends Transaction {

  /**
   * Initialises an {@code InterestTransaction} with the given amount.
   *
   * @param amount The amount
   */
  public InterestTransaction(final BigDecimal amount) {
    super(amount);
  }
}
