package com.abc;

public class CheckingAccount extends Account {

  public CheckingAccount() {
    super("Checking Account");
  }

  @Override
  double interestEarned() {
    return (sumTransactions() * 0.001);
  }

}
