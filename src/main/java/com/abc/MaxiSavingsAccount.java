package com.abc;

import static com.abc.Constants.MAX_SAVINGS_ACCOUNT;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MaxiSavingsAccount extends AbstractAccount {

  private double interestRate = 0.1;

  public String getAccountType() {
    return MAX_SAVINGS_ACCOUNT;
  }

  @Override
  public double calculateInterest() {
    List<Transaction> last10DaysWithdraw = getLast10DaysWithdrawTransactions();
    if (last10DaysWithdraw.isEmpty())
      return balance * 0.05;
    else
      return balance * Math.pow((1 + interestRate/ 365), 365) - balance;
  }

  public List<Transaction> getLast10DaysWithdrawTransactions() {
    return Optional.ofNullable(getTransactions()).orElseGet(Collections::emptyList).stream()
        .filter(trans -> "withdraw".equals(trans.getTransactionType()))
        .filter(trans -> LocalDateTime.now().minusDays(10).isBefore(trans.getTransactionDate()))
        .sorted((t1, t2) -> t2.getTransactionDate().compareTo(t2.getTransactionDate()))
        .collect(Collectors.toList());
  }
}
