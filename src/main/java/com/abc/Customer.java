package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Customer {
  private String name;
  private List<Account> accounts;

  public Customer(String name) {
    this.name = name;
    this.accounts = new ArrayList<Account>();
  }

  public String getName() {
    return name;
  }

  public Customer openAccount(Account account) {
    accounts.add(account);
    return this;
  }

  public int getNumberOfAccounts() {
    return accounts.size();
  }

  public double totalInterestEarned() {
    double total = 0;
    for (Account a : accounts)
      total += a.interestEarned();
    return total;
  }

  public String getStatement() {
    String statement = null;
    statement = "Statement for " + name + "\n";
    double total = 0.0;
    for (Account a : accounts) {
      statement += "\n" + a.toString() + "\n";
      total += a.sumTransactions();
    }
    statement += "\nTotal In All Accounts " + Transaction.toDollars(total);
    return statement;
  }
}
