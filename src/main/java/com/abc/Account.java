package com.abc;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class Account {

 public static final int CHECKING = 0;
 public static final int SAVINGS = 1;
 public static final int MAXI_SAVINGS = 2;

 private final int accountType;
 public List < Transaction > transactions;

 public Account(int accountType) {
  if (accountType >= 0 && accountType <= 2) {
   this.accountType = accountType;
   this.transactions = new ArrayList < Transaction > ();
  } else
   throw new IllegalArgumentException("Account type must be CHECKING(0), SAVINGS(1) or MAXI_SAVINGS(2).");
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

 public double interestEarned() {
  double amount = sumTransactions();
  switch (accountType) {
   case SAVINGS:
    if (amount <= 1000)
     return amount * 0.001;
    else
     return 1 + (amount - 1000) * 0.002;
   case MAXI_SAVINGS:
    if (isThereWithdraw(10))
     return amount * 0.001;
    else
     return amount * 0.05;
   default:
    return amount * 0.001;
  }
 }

 private boolean isThereWithdraw(int numberOfdays) {
  //look at the last "numberOfdays" days of transactions and return true if any of them are withdrawals
  Date now = new Date();
  long dayDiff;
  int day_in_ms = 1000 * 60 * 60 * 24;
  for (Transaction transaction: transactions) {
   dayDiff = (now.getTime() - transaction.getDate().getTime()) / day_in_ms;
   if (transaction.getAmount() < 0 && dayDiff <= numberOfdays)
    return true;
   else if (dayDiff > numberOfdays)
    return false;
  }
  return false;
 }

 public double sumTransactions() {
  if (checkIfTransactionsExist()) {
   double amount = 0.0;
   for (Transaction transaction: transactions)
    amount += transaction.getAmount();
   return amount;
  } else
   return 0;
 }

 private boolean checkIfTransactionsExist() {
  return transactions.size() > 0;
 }

 public int getAccountType() {
  return accountType;
 }

}
