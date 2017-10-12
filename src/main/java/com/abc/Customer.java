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
  
  /**
   * Transfers amount of cash from one account to another (same customer). Throws exception
   * on error.
   * @param sourceAccount the account to take the money from
   * @param destAccount   the account to place the money in 
   * @param amount        the amount of money to transfer
   * @throws Exception    error in withdraw or deposit
   */
  public void transferMoney(Account sourceAccount, Account destAccount, double amount) {
    // transfer the money with fallbacks for failure
    try {
      sourceAccount.withdraw(amount);
      try {
        destAccount.deposit(amount);
      } catch (Exception e) {
        sourceAccount.deposit(amount); 
        throw e;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Computes total interest across all accounts.
   * @return total interest of all accounts
   */
  public double totalInterestEarned() {
    double total = 0;
    for (Account a : accounts) {
      total += a.interestEarned();
    }
    return total;
  }

  /**
   * Provides a pretty statement for all accounts.
   * @return readable customer statement
   */
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
