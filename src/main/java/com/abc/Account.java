package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Account {

  public enum Type {
    CHECKING, SAVINGS, MAXI_SAVINGS;
  }

  private final int daysPerYear = 365;
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

  // Create list of total transactions for each day
  private List<Double> sumTransactionsPerDay() {
    List<Double> amounts = new ArrayList<Double>();
    long day = 86400000;
    long time = transactions.get(0).getDate().getTime()
        - (transactions.get(0).getDate().getTime() % day);
    int current = 0;
    
    for (Transaction t : transactions) {
      // Advance to correct day
      while (t.getDate().getTime() >= time + day) {
        time += day;
        current++;
      }
      // Sum the total transactions for each day in list bucket
      double amount = t.getAmount();
      if (current < amounts.size()) {
        amount += amounts.get(current);
      }
      amounts.add(current, amount);
    }
    return amounts;
  }
  
  // Calculates the total compound interest earned at a daily rate
  // Annual interest rates are split on days per year
  public double interestEarned() {
    if (transactions.size() <= 0) {
      return 0.0;
    }
    List<Double> amountsPerDay = sumTransactionsPerDay();
    
    // Calculate compounded interest using list of totals
    double total = 0.0;
    double interest = 0.0;
    for (Double d : amountsPerDay) {
      total += d;
      switch (accountType) {
      case CHECKING:
        interest += checkingInterest(total);
        break;
      case SAVINGS:
        interest += savingsInterest(total);
        break;
      case MAXI_SAVINGS:
        interest += maxiSavingsInterest(total);
        break;
      }
      total += interest;
    }
    return interest;
  }
  
  // Old version of method
  /*
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
  */
  
  // Calculates interest for a checking account
  private double checkingInterest(double amount) {
    double interestRate = 0.001 / daysPerYear;
    return amount * interestRate;
  }
  
  // Calculates interest for a savings account
  private double savingsInterest(double amount) {
    double firstInterestRate = 0.001 / daysPerYear;
    double secondInterestRate = 0.002 / daysPerYear;
    
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
    double standardInterestRate = 0.001 / daysPerYear;
    double noWithdrawalsInterestRate = 0.05 / daysPerYear;
    
    // Check for withdrawals within 10 days
    long withdrawalsTimeThreshold = 86400000 * 10; // 10 days in ms
    
    boolean withdrawals = false;
    long withdrawalsTime = DateProvider.getInstance().now().getTime()
        - withdrawalsTimeThreshold;
    for (Transaction t : transactions) {
      if (t.getDate().getTime() >= withdrawalsTime) {
         if (t.getAmount() < 0) {
           withdrawals = true;
           break;
         }
      } else {
        // The time is before 10 days ago, so stop checking
        break;
      }
    }
    
    if (withdrawals) {
      return amount * standardInterestRate;
    } else {
      return amount * noWithdrawalsInterestRate;
    }
  }
  
  // Old version of method
  /*
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
  */

}
