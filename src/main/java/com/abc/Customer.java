package com.abc;

import static java.lang.Math.abs;

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
    for (Account a : accounts) {
      total += a.interestEarned();
    }
    return total;
  }

  public String getStatement() {
    String statement = null;
    statement = "Statement for " + name + "\n";
    double total = 0.0;
    for (Account a : accounts) {
      statement += "\n" + statementForAccount(a) + "\n";
      total += a.sumTransactions();
    }
    statement += "\nTotal In All Accounts " + toDollars(total);
    return statement;
  }

  private String statementForAccount(Account a) {
    String s = a.getPrettyAccountName() + "\n";
    
    // Now total up all the transactions
    double total = 0.0;
    for (Transaction t : a.getTransactions()) {
      s += "  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount) + "\n";
      total += t.amount;
    }
    s += "Total " + toDollars(total);
    return s;
  }

  private String toDollars(double d) {
    return String.format("$%,.2f", abs(d));
  }
}
