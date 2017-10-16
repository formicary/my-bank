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
  
  /**
   * Deposit money into the account as a transaction.
   * @param amount to be added (positive number)
   * @throws IllegalArgumentException for non-positive number
   */
  public void deposit(double amount) throws IllegalArgumentException {
    if (amount <= 0) {
      throw new IllegalArgumentException("amount must be greater than zero");
    } else {
      transactions.add(new Transaction(amount));
    }
  }

  /**
   * Withdraws money from the account as a transaction.
   * @param amount to be withdrawn (positive number)
   * @throws IllegalArgumentException for non-positive number
   */
  public void withdraw(double amount) throws IllegalArgumentException {
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
      amount += t.getAmount();
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
