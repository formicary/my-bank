package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Account {

  public static final int CHECKING = 0;
  public static final int SAVINGS = 1;
  public static final int MAXI_SAVINGS = 2;

  private final Customer owner; // reference to account holder
  private final int accountType;
  public List<Transaction> transactions;

  public Account(Customer owner, int accountType) {
    this.owner = owner;
    this.accountType = accountType;
    this.transactions = new ArrayList<Transaction>();
  }

  public void deposit(double amount) {
    if (amount <= 0) {
      throw new IllegalArgumentException("amount must be greater than zero");
    } else {
      transactions.add(new Transaction(amount));
    }
  }

  public void withdraw(double amount) {
    if (amount <= 0) {
      throw new IllegalArgumentException("amount must be greater than zero");
    } else {
      transactions.add(new Transaction(-amount));
    }
  }

  public Customer getOwner() {
    return owner;
  }

  public void transfer(double amount, Account to) {
    if (owner.equals(to.getOwner())) {
      /*
       * equals is used and it compares the pointer since it is possible for
       * multiple customers to exist with same name and same number of accounts
       */
      withdraw(amount);
      to.deposit(amount);
    } else {
      throw new IllegalArgumentException(
          "Can only transfer between account holder's accounts");
    }
  }

  public double interestEarned() {
    double amount = sumTransactions();
    if (amount <= 0) {
      // no interest earned on amounts less than 0
      return 0;
    }
    switch (accountType) {
    case SAVINGS:
      double firstThousand = 0.001;
      double rate = 0.002;
      if (amount <= 1000)
        return amount * 0.001;
      else
        return 1000 * firstThousand + (amount - 1000) * rate;
    case MAXI_SAVINGS:
      rate = withdrawalInThePastNDays(10) ? 0.001 : 0.05;
      return amount * rate;
    default:
      return amount * 0.001;
    }
  }

  public double sumTransactions() {
    double amount = 0.0;
    for (Transaction t : transactions)
      amount += t.getTransactionAmount();
    return amount;
  }

  /*
   * public int getAccountType() { return accountType; }
   */
  public boolean withdrawalInThePastNDays(int n) {
    for (int i = transactions.size() - 1; i >= 0; i--) {
      Transaction curr = transactions.get(i);
      Date now = DateProvider.getInstance().now();
      if (curr.getTransactionAmount() >= 0)
        continue;
      if (DateProvider.getDayDifference(curr.getTransactionDate(), now) < n) {
        return true;
      } else {
        /*
         * since we go through the list backwards most recent transactions will
         * appear first if we encounter a transaction from before n days then we
         * can break out the loop and return false
         */
        break;
      }
    }
    return false;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    // Translate to pretty account type
    switch (accountType) {
    case Account.CHECKING:
      sb.append("Checking Account\n");
      break;
    case Account.SAVINGS:
      sb.append("Savings Account\n");
      break;
    case Account.MAXI_SAVINGS:
      sb.append("Maxi Savings Account\n");
      break;
    }

    // Now total up all the transactions
    double total = 0.0;
    for (Transaction t : transactions) {
      sb.append("  "
          + (t.getTransactionAmount() < 0 ? "withdrawal" : "deposit") + " "
          + Transaction.toDollars(t.getTransactionAmount()) + "\n");
      total += t.getTransactionAmount();
    }
    sb.append("Total " + Transaction.toDollars(total));
    return sb.toString();
  }

}
