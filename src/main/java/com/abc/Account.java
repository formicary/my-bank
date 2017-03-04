package com.abc;

import java.util.ArrayList;
import java.util.List;

public abstract class Account {

	public static final double MONEY_ZERO = 0.00;

	private List<Transaction> transactions;
	private int accountNo;

	/**
	 * Create Account with list of transactions
	 * @param transactions list of transactions
	 */
	public Account() {
		this.transactions = new ArrayList<Transaction>();
	}

	/**
	 * Deposit a new amount into an account.
	 * If amount less than or equal to zero, user notified.
	 * Otherwise, amount added to ArrayList of transactions
	 * and method returns true.
	 * @param amount to deposit
	 * @return result of transaction
	 */
	public boolean deposit(double amount) {
		boolean result = false;
		if(MONEY_ZERO >= amount) {
			System.out.println("Amount to deposit must be greater than zero");
		} else {
			getTransactions().add(new Transaction(amount));
			result = true;
		}
		return result;
	}
	/**
	 * Withdraw a new amount from an account.
	 * If amount less than or equal to zero, user notified.
	 * If total account balance less than amount to withdraw, user notified.
	 * Otherwise, negative amount added to ArrayList of transactions
	 * and method returns true.
	 * @param amount to withdraw
	 * @return result of transaction
	 */
	public boolean withdraw(double amount) {
		boolean result = false;
		if (MONEY_ZERO >= amount) {
			System.out.println("Amount to withdraw must be greater than zero");
		}
		else {
			if (sumTransactions() < amount) {
				System.out.println("Insufficient funds to withdraw");
			}
			else {
				getTransactions().add(new Transaction(-amount));
				result = true;
			}
		}
		return result;

	}
	/**
	 * Calculates the interest earned on an account
	 * Logic defined in subclass
	 * @return interest earned
	 */
	public abstract double interestEarned();

	/**
	 * Check if list of transactions is empty
	 * @return true if list of transactions empty
	 * @return false if list of transactions populated
	 */
	public boolean isTransactionsEmpty() {
		return (null == getTransactions() || getTransactions().isEmpty()); 
	}

	/**
	 * Sum all transactions to calculate total account balance
	 * @return account balance
	 */
	public double sumTransactions() {
		double amount = 0.00;
		for (Transaction t: getTransactions()) {
			amount += t.amount;
		}
		return amount;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public int getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(int accountNo) {
		this.accountNo = accountNo;
	}	

}
