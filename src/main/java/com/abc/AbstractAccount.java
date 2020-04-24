package com.abc;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractAccount implements Account {
  protected double balance;

  private List<Transaction> transactions;


  public AbstractAccount() {
    this.transactions = new ArrayList<>();
  }

  public void deposit(double amount) {
    if (amount <= 0) {
      throw new IllegalArgumentException("amount must be greater than zero");
    } else {
      this.balance += amount;
      transactions.add(new Transaction(amount, "deposit", getAccountType()));
    }
  }

  public void withdraw(double amount) {
    if (amount <= 0) {
      throw new IllegalArgumentException("amount must be greater than zero");
    } else if (amount > balance) {
      throw new IllegalArgumentException("Insufficient Funds");
    } else {
      this.balance -= amount;
      transactions.add(new Transaction(-amount, "withdraw", getAccountType()));
    }
  }

  public void transferAmount(double amount, Account otherAccount) {
    otherAccount.deposit(amount);
    this.withdraw(amount);
  }

  public List<Transaction> getTransactions() {
    return transactions;
  }

  public double getBalance() {
    return balance;
  }
}
