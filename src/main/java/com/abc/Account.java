package com.abc;

import java.util.ArrayList;
import java.util.List;

public abstract class Account {
  private final String prettyAccountName;
  private List<Transaction> transactions;


  public Account(String prettyAccountName) {
    this.prettyAccountName = prettyAccountName;
    this.transactions = new ArrayList<Transaction>();
  }
  
  public void deposit(double amount) {
    if (amount <= 0) {
      throw new IllegalArgumentException("amount must be greater than zero");
    } else {
      transactions.add(new Transaction(amount));
    }
  }

  public void withdraw(double amount) {
    if (amount <= 0) {
      throw new IllegalArgumentException("amount must be greater than zero");
    } else {
      transactions.add(new Transaction(-amount));
    }
  }

  abstract double interestEarned();

  public double sumTransactions() {
    return checkIfTransactionsExist(true);
  }

  private double checkIfTransactionsExist(boolean checkAll) {
    double amount = 0.0;
    for (Transaction t: transactions) {
      amount += t.amount;
    }
    return amount;
  }

  public String getPrettyAccountName() {
    return this.prettyAccountName;
  }

  public List<Transaction> getTransactions() {
    return this.transactions;
  }
  
  public void addTransaction(Transaction transaction) {
    this.transactions.add(transaction);
  }


}
