package com.abc;

public class SavingsAccount extends Account {
  private static double firstInterest = 0.001;
  private static double firstLimit = 1000;
  private static double secondInterest = 0.002;

  public SavingsAccount() {
    super("Savings Account");
  }

  @Override
  double interestEarned() {
    double amount = sumTransactions();
    if (amount <= firstLimit) {
      return amount * firstInterest;
    } else {
      return (firstLimit * firstInterest) + ((amount - firstLimit) * secondInterest);
    }
  }

}
