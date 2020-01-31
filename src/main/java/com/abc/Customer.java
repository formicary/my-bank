package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Customer {
 private String name;
 private List < Account > accounts;

 public Customer(String name) {
  this.name = name;
  this.accounts = new ArrayList < Account > ();
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
  for (Account a: accounts)
   total += a.interestEarned();
  return total;
 }

 public String getStatement() {
  String statement = null;
  statement = "Statement for " + name + "\n";
  double total = 0.0;
  for (Account a: accounts) {
   statement += "\n" + statementForAccount(a) + "\n";
   total += a.sumTransactions();
  }
  statement += "\nTotal In All Accounts " + toDollars(total);
  return statement;
 }

 public void transfer(Account from, Account to, double amount) {
  if (accounts.contains(from) && accounts.contains(to)) {
   from.withdraw(amount);
   to.deposit(amount);
  } else
   throw new IllegalArgumentException("Both accounts must be owned by a single customer.");
 }

 private String statementForAccount(Account account) {
  String statement = "";
  //Translate to pretty account type
  switch (account.getAccountType()) {
   case Account.CHECKING:
    statement += "Checking Account\n";
    break;
   case Account.SAVINGS:
    statement += "Savings Account\n";
    break;
   case Account.MAXI_SAVINGS:
    statement += "Maxi Savings Account\n";
    break;
  }
  //Now total up all the transactions
  double total = 0.0;
  for (Transaction transaction: account.transactions) {
   statement += "  " + (transaction.getAmount() < 0 ? "withdrawal" : "deposit") + " " + toDollars(transaction.getAmount()) + "\n";
   total += transaction.getAmount();
  }
  statement += "Total " + toDollars(total);
  return statement;
 }

 private String toDollars(double amount) {
  return String.format("$%,.2f", abs(amount));
 }
}
