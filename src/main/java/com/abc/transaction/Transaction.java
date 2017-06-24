package com.abc.transaction;

import com.abc.DateProvider;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/** The class {@code Transaction} represents a transaction of money. */
public class Transaction {

  private final BigDecimal amount;
  private final LocalDateTime date;

  /**
   * Initialise an {@code Transaction} with the given amount.
   *
   * @param amount The amount
   */
  public Transaction(final BigDecimal amount) {
    this.amount = amount;
    this.date = DateProvider.getInstance().now();
  }

  /**
   * Get the amount.
   *
   * @return The amount
   */
  public BigDecimal getAmount() {
    return amount;
  }

  /**
   * Get the date the transaction occurred on.
   *
   * @return The date
   */
  public LocalDateTime getDate() {
    return date;
  }

  /**
   * Does this transaction represent a withdrawal?
   *
   * @return {code true} if the transaction represents a withdrawal, {@code false} otherwise
   */
  public boolean isWithdrawal() {
    return amount.doubleValue() < 0;
  }
}
