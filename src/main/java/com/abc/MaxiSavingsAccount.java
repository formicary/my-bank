package com.abc;

import static com.abc.AccountType.MAX_SAVINGS_ACCOUNT;


import java.util.ArrayList;
import java.util.List;

public class MaxiSavingsAccount implements Account {

  private double interestRate = 0.1;
  private double balance;
  private List<Transaction> transactions;
  private CommonOperationsHelper commonOperationsHelper;

  public MaxiSavingsAccount() {
    this.commonOperationsHelper = new CommonOperationsHelper();
    this.transactions = new ArrayList<>();
  }

  public MaxiSavingsAccount(CommonOperationsHelper helper) {
    this.commonOperationsHelper = helper;
    this.transactions = new ArrayList<>();
  }

  public String getAccountType() {
    return MAX_SAVINGS_ACCOUNT.getValue();
  }

  @Override
  public double calculateInterest() {
    List<Transaction> last10DaysWithdraw = getCommonOperation().getLast10DaysWithdrawTransactions(this);
    if (last10DaysWithdraw.isEmpty())
      return getBalance() * 0.05;
    else
      return this.getBalance() * Math.pow((1 + interestRate/ 365), 365) - getBalance();
  }

  @Override
  public void withdraw(double amount) {
    getCommonOperation().withdraw(amount, this);
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

  @Override
  public void deposit(double amount) {
    getCommonOperation().deposit(amount, this);
  }


  /*public List<Transaction> getLast10DaysWithdrawTransactions() {
    return Optional.ofNullable(getTransactions()).orElseGet(Collections::emptyList).stream()
        .filter(trans -> "withdraw".equals(trans.getTransactionType()))
        .filter(trans -> LocalDateTime.now().minusDays(10).isBefore(trans.getTransactionDate()))
        .sorted((t1, t2) -> t2.getTransactionDate().compareTo(t2.getTransactionDate()))
        .collect(Collectors.toList());
  }*/

  @Override
  public CommonOperationsHelper getCommonOperation() {
    return this.commonOperationsHelper;
  }
}
