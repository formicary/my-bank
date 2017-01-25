package com.abc;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;


public class Account {

	public static final int CHECKING = 0;
	public static final int SAVINGS = 1;
	public static final int MAXI_SAVINGS = 2;
	public double totalBalance;

	private final int accountType;
	public List<Transaction> transactions;

	public Account(int accountType) {
		this.accountType = accountType;
		this.transactions = new ArrayList<Transaction>();
	}

	/** Deposit amount into given account number
	 * @param amount 
	 * @throws IllegalArgumentException if the specified amount is negative or zero
	 */
	public void deposit(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else {
			transactions.add(new Transaction(amount));
		}
	}

	/** Withdraw specified amount from given account number
	 * @param amount 
	 * @throws IllegalArgumentException if the specified amount is negative or zero
	 */
	public void withdraw(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else if (amount > totalBalance) {
			throw new IllegalArgumentException("Withdrawal amount is greater than account balance");
		}

		else {
			transactions.add(new Transaction(-amount));
		}
	}

	/**
	 *  Calculate Interest Earned on the different account type
	 *  Maxi-Savings accounts to have an interest rate of 5% 
	 *  assuming no withdrawals in the past 10 days otherwise 0.1%
	 */
	public double interestEarned() {
	    double amount = sumTransactions();
	    if (amount <= 0) {
	      /**no interest earned on amounts less than 0 */
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

	/*
	 * public double sumTransactions() { return checkIfTransactionsExist(true);
	 * }
	 */

	@SuppressWarnings("unused")
	private double checkIfTransactionsExist(boolean checkAll) {
		double amount = 0.0;
		for (Transaction t : transactions)
			amount += t.amount;
		return amount;
	}

	/**
	 * Provides account type  
	*/
	public int getAccountType() {
		return accountType;
	}

	/**
	 * Provide lastWithdrawal Date.
	 * Returns a Date object that can be used to calculate interest rate.
	 */
	public Date latestWithdrawal() {
		int lastTransaction = transactions.size() - 1;
		Date maxDate = transactions.get(lastTransaction).getTransactionDate();

		for (int i = lastTransaction; i >= 0; i--) {
			if (transactions.get(i).getAmount() < 0) {
				maxDate = transactions.get(i).getTransactionDate();
				break;
			}
		}
		return maxDate;
	}
    /**
     * Provide total balance of account
     */
	public double sumTransactions() {
		double amount = 0.0;
		for (Transaction t : transactions)
			amount += t.getAmount();
		this.totalBalance = amount;
		return amount;
	}
	
	public boolean withdrawalInThePastNDays(int daysCount) {
		for (int i = transactions.size() - 1; i >= 0; i--) {
			Transaction curr = transactions.get(i);
			Date now = DateProvider.getInstance().now();
			if (curr.getAmount() >= 0)
				continue;
			if (DateProvider.getDayDifference(curr.getTransactionDate(), now) < daysCount) {
				return true;
			} else {
				break;
			}
		}
		return false;
	}
}
