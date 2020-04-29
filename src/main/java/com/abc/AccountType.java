package com.abc;

public enum AccountType {
  MAX_SAVINGS_ACCOUNT("Maxi-Savings Account"),
  SAVING_ACCOUNT("Saving Account"),
  CHECKING_ACCOUNT("Checking Account");

  private String value;

  public String getValue() {
    return value;
  }

  private AccountType(String value) {
    this.value = value;
  }
}
