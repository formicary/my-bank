package com.abc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CommonOperationsHelper {

  public void deposit(double amount, Account account) {
    if (amount <= 0) {
      throw new IllegalArgumentException("amount must be greater than zero");
    } else {
      double balance = account.getBalance();
      account.setBalance(balance + amount);
      account.getTransactions().add(new Transaction(amount, "deposit", account.getAccountType()));
    }
  }

  public void withdraw(double amount, Account account) {
    if (amount <= 0) {
      throw new IllegalArgumentException("amount must be greater than zero");
    } else if (amount > account.getBalance()) {
      throw new IllegalArgumentException("Insufficient Funds");
    } else {
      account.setBalance(account.getBalance() - amount);
      account.getTransactions().add(new Transaction(-amount, "withdraw", account.getAccountType()));
    }
  }

  public void transferAmount(double amount, Account fromAccount, Account toAccount) {
    toAccount.deposit(amount);
    fromAccount.withdraw(amount);
  }

  public List<Transaction> getLast10DaysWithdrawTransactions(MaxiSavingsAccount account) {
    return Optional.ofNullable(account.getTransactions()).orElseGet(Collections::emptyList).stream()
        .filter(trans -> "withdraw".equals(trans.getTransactionType()))
        .filter(trans -> LocalDateTime.now().minusDays(10).isBefore(trans.getTransactionDate()))
        .sorted((t1, t2) -> t2.getTransactionDate().compareTo(t2.getTransactionDate()))
        .collect(Collectors.toList());
  }
}
