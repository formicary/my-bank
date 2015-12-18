package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Account {

  public enum Type {
    CHECKING, SAVINGS, MAXI_SAVINGS;
  }

  private final double interestRateThreshold = 1000;
  private final Type accountType;
  private List<Transaction> transactions;

  public Account(Type accountType) {
    this.accountType = accountType;
    this.transactions = new ArrayList<Transaction>();
  }

  // Getters 
  
  public Type getAccountType() {
    return accountType;
  }
  
  public List<Transaction> getTransactions() {
    return transactions;
  }

  // Records transaction for this account
  // Positive amount for deposit, negative amount for withdrawal
  public void transact(double amount) {
    if (amount == 0) {
      throw new IllegalArgumentException("amount cannot be zero");
    } else {
      transactions.add(new Transaction(amount));
    }
  }
  
  // Calculates the total amount in the account based on transactions
  public double sumTransactions() {
    double amount = 0.0;
    for (Transaction t : transactions)
      amount += t.getAmount();
    return amount;
  }

  // Calculates the total interest earned
  public double interestEarned() {
    double amount = sumTransactions();
    switch (accountType) {
    case CHECKING:
      return checkingInterest(amount);
    case SAVINGS:
      return savingsInterest(amount);
    case MAXI_SAVINGS:
      return maxiSavingsInterest(amount);
    default:
      return 0.0;
    }
  }
  
  // Calculates interest for a checking account
  private double checkingInterest(double amount) {
    double interestRate = 0.001;
    return amount * interestRate;
  }
  
  // Calculates interest for a savings account
  private double savingsInterest(double amount) {
    double firstInterestRate = 0.001;
    double secondInterestRate = 0.002;
    
    if (amount <= interestRateThreshold) {
      return amount * firstInterestRate;
    }
    double result = interestRateThreshold * firstInterestRate;
    amount -= interestRateThreshold;
    result += amount * secondInterestRate;
    return result;
  }
  
  // Calculates interest for a maxi savings account
  private double maxiSavingsInterest(double amount) {
    double firstInterestRate = 0.02;
    double secondInterestRate = 0.05;
    double thirdInterestRate = 0.1;
    
    if (amount <= interestRateThreshold) {
      return amount * firstInterestRate;
    }
    double result = interestRateThreshold * firstInterestRate;
    amount -= interestRateThreshold;
    if (amount <= interestRateThreshold) {
      result += amount * secondInterestRate;
      return result;
    }
    result += interestRateThreshold * secondInterestRate;
    amount -= interestRateThreshold;
    result += amount * thirdInterestRate;
    return result;
  }

}
