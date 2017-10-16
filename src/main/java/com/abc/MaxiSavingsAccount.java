package com.abc;

import java.util.Date;

public class MaxiSavingsAccount extends Account {
  private static double normalInterest = 0.05;
  private static double reducedInterest = 0.01;
  private static double dayLimit = 10;
  
  public MaxiSavingsAccount() {
    super("Maxi Savings Account");
  }

  @Override
  double interestEarned() {
    double amount = sumTransactions();
    Date lastWithdrawal = getLastWithdrawalDate();
    if (lastWithdrawal == null 
        || DateProvider.getInstance().now().getTime() - lastWithdrawal.getTime() 
        > 24 * 60 * 60 * 1000 * dayLimit) {
      return amount * normalInterest;
    } else {
      return amount * reducedInterest;
    }
  }

}
