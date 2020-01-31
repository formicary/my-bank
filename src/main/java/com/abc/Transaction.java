package com.abc;

import java.util.Calendar;
import java.util.Date;

public class Transaction {

 private final double amount;
 private Date transactionDate;

 public Transaction(double amount) {
  this.amount = amount;
  this.transactionDate = new Date();
 }

 public Date getDate() {
  return this.transactionDate;
 }

 public double getAmount() {
  return this.amount;
 }

}
