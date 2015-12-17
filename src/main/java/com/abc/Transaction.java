package com.abc;

import java.util.Calendar;
import java.util.Date;

// TODO: Unused imports

public class Transaction {
  public final double amount;

  // TODO: Field never used
  private Date transactionDate;

  public Transaction(double amount) {
    this.amount = amount;
    this.transactionDate = DateProvider.getInstance().now();
  }

}
