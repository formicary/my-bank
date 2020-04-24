package com.abc;

import static com.abc.Constants.CHECKING_ACCOUNT;

public class CheckingAccount extends AbstractAccount {

  private double interestRate = 0.001;

  public double calculateInterest() {
    return balance * interestRate;
  }

  public String getAccountType() {
    return CHECKING_ACCOUNT;
  }
}
