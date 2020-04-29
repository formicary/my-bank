package com.abc;

import static com.abc.AccountType.SAVING_ACCOUNT;

import java.util.ArrayList;
import java.util.List;

public class SavingAccount implements Account {

  private double interestRate = 0.001;
  private double balance;
  private List<Transaction> transactions;
  private CommonOperationsHelper commonUtility;

  public SavingAccount() {
    this.commonUtility = new CommonOperationsHelper();
    this.transactions = new ArrayList<>();
  }

  public double calculateInterest() {
    if (this.getBalance() <= 1000)
      return this.getBalance() * interestRate;
    else
      return 1 + (getBalance()-1000) * 0.002;
  }

  public String getAccountType() {
    return SAVING_ACCOUNT.getValue();
  }

  @Override
  public CommonOperationsHelper getCommonOperation() {
    return this.commonUtility;
  }

  @Override
  public void withdraw(double amount) {
    this.getCommonOperation().withdraw(amount, this);
  }

  @Override
  public void deposit(double amount) {
    this.getCommonOperation().deposit(amount, this);
  }

  @Override
  public void transferAmount(double amount, Account otherAccount) {
    getCommonOperation().transferAmount(amount, this, otherAccount);
  }

  @Override
  public List<Transaction> getTransactions() {
    return this.transactions;
  }

  @Override
  public double getBalance() {
    return this.balance;
  }

  public void setBalance(double amount) {
    this.balance = amount;
  }

}
