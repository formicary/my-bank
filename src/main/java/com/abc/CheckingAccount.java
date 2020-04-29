package com.abc;

import static com.abc.AccountType.CHECKING_ACCOUNT;

import java.util.ArrayList;
import java.util.List;

public class CheckingAccount implements Account {

  private double interestRate = 0.001;
  private double balance;
  private List<Transaction> transactions;
  private CommonOperationsHelper commonUtility;

  public CheckingAccount() {
    this.commonUtility = new CommonOperationsHelper();
    this.transactions = new ArrayList<>();
  }

  public double calculateInterest() {
    return getBalance() * interestRate;
  }

  public String getAccountType() {
    return CHECKING_ACCOUNT.getValue();
  }

  @Override
  public CommonOperationsHelper getCommonOperation() {
    return this.commonUtility;
  }

  @Override
  public void withdraw(double amount) {
    getCommonOperation().withdraw(amount, this);
  }

  @Override
  public void deposit(double amount) {
    getCommonOperation().deposit(amount, this);
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

  @Override
  public void setBalance(double amount) {
    this.balance = amount;
  }
}
