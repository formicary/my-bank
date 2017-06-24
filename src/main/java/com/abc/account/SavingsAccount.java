package com.abc.account;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/** The class {@code SavingsAccount} extends {@code Account} that represents a savings account */
public class SavingsAccount extends Account {

  /** Initialise a {@code SavingsAccount}. */
  public SavingsAccount() {
    super();
  }

  @Override
  protected BigDecimal getDailyInterestEarned(final LocalDateTime day) {
    final BigDecimal amount = getTransactionsSum();
    if (amount.compareTo(BigDecimal.valueOf(1000)) <= 0) {
      return amount.multiply(convertToDailyRate(BigDecimal.valueOf(0.001)));
    }
    return getOverOneThousandDailyInterest(amount);
  }

  private BigDecimal getOverOneThousandDailyInterest(final BigDecimal amount) {
    return BigDecimal.ONE
        .divide(BigDecimal.valueOf(365), 7, BigDecimal.ROUND_HALF_UP)
        .add(
            amount
                .subtract(BigDecimal.valueOf(1000))
                .multiply(convertToDailyRate(BigDecimal.valueOf(0.002))));
  }

  @Override
  public String getName() {
    return "Savings Account";
  }
}
