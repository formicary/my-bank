package com.abc;

import java.util.Date;

public class Transaction {

  private double amount;
  private Date date;

  public Transaction(double amount) {
    this.amount = amount;
    this.date = DateProvider.getInstance().now();
  }
  
  // Getters
  
  public double getAmount() {
    return amount;
  }
  
  public Date getDate() {
    return date;
  }

}
