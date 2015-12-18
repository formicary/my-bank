package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Customer {
  private String name;
  private List<Account> accounts;

  public Customer(String name) {
    this.name = name;
    this.accounts = new ArrayList<Account>();
  }

  // Getters
  public String getName() {
    return name;
  }

  public int getNumberOfAccounts() {
    return accounts.size();
  }
  
  // Opens an account, returns this to chain account opening
  public Customer openAccount(Account account) {
    accounts.add(account);
    return this;
  }

  // Calculates the total interest earned across all accounts
  public double totalInterestEarned() {
    double total = 0.0;
    for (Account a : accounts)
      total += a.interestEarned();
    return total;
  }

  // Returns a statement for the customer's accounts
  public String getStatement() {
    String statement = "Statement for " + name + "\n";
    double total = 0.0;
    for (Account a : accounts) {
      statement += "\n" + statementForAccount(a) + "\n";
      total += a.sumTransactions();
    }
    statement += "\nTotal In All Accounts " + toDollars(total);
    return statement;
  }

  // TODO: add default to switch
  private String statementForAccount(Account a) {
    String s = "";

    // Convert account enumeration to appropriate string
    switch (a.getAccountType()) {
    case Account.CHECKING:
      s += "Checking Account\n";
      break;
    case Account.SAVINGS:
      s += "Savings Account\n";
      break;
    case Account.MAXI_SAVINGS:
      s += "Maxi Savings Account\n";
      break;
    }

    // Creates string listing transactions and a total balance
    double total = 0.0;
    for (Transaction t : a.transactions) {
      s += "  " + (t.getAmount() < 0 ? "withdrawal" : "deposit") + " "
          + toDollars(t.getAmount()) + "\n";
      total += t.getAmount();
    }
    s += "Total " + toDollars(total);
    return s;
  }

  // Converts double to dollar format string
  private String toDollars(double d) {
    return String.format("$%,.2f", abs(d));
  }
}
