package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Account {

  public enum Type {
    CHECKING, SAVINGS, MAXI_SAVINGS;
  }

  private final Type accountType;
  public List<Transaction> transactions;

  public Account(Type accountType) {
    this.accountType = accountType;
    this.transactions = new ArrayList<Transaction>();
  }
  
  public Type getAccountType() {
    return accountType;
  }

  public void transact(double amount) {
    if (amount == 0) {
      throw new IllegalArgumentException("amount cannot be zero");
    } else {
      transactions.add(new Transaction(amount));
    }
  }

  // TODO: remove magic numbers
  public double interestEarned() {
    double amount = sumTransactions();
    switch (accountType) {
    case SAVINGS:
      if (amount <= 1000)
        return amount * 0.001;
      else
        return 1 + (amount - 1000) * 0.002;
      // case SUPER_SAVINGS:
      // if (amount <= 4000)
      // return 20;
    case MAXI_SAVINGS:
      if (amount <= 1000) {
        return amount * 0.02;
      } else if (amount <= 2000) {
        return 2 + (amount - 1000) * 0.05;
      } else {
        return 7 + (amount - 2000) * 0.01;
      }
    default:
      return amount * 0.001;
    }
  }

  public double sumTransactions() {
    return checkIfTransactionsExist(true);
  }

  // TODO: unused method argument
  private double checkIfTransactionsExist(boolean checkAll) {
    double amount = 0.0;
    for (Transaction t : transactions)
      amount += t.getAmount();
    return amount;
  }

}
