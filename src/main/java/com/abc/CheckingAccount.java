package com.abc;

public class CheckingAccount extends Account {
  private static double interestRate = 0.001;
  
  public CheckingAccount() {
    super("Checking Account");
  }

  @Override
  double interestEarned() {
    return (sumTransactions() * interestRate);
  }

}
