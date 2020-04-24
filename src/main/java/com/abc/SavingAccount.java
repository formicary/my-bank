package com.abc;

import static com.abc.Constants.SAVING_ACCOUNT;

public class SavingAccount extends AbstractAccount {

  private double interestRate = 0.001;

  public double calculateInterest() {
    if (balance <= 1000)
      return balance * interestRate;
    else
      return 1 + (balance-1000) * 0.002;
  }

  public String getAccountType() {
    return SAVING_ACCOUNT;
  }
}
