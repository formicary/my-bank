package com.abc;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class of account. A specific account type need to extend this class.
 * 
 * @author Others and Fei
 * 
 */
public abstract class Account {
    //Account balance
	private double balance; 
	//All the transactions of this account
	private List<Transaction> transactions;
	//Name of the account
	private String accountName;

	// Initial account balance are set to 0.0
	Account() {
		this.balance = 0.0;
		transactions = new ArrayList<Transaction>();
	}

	public double getBalance() {
		return balance;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	/**
	 * This method is used to deposit money in bank. Synchronized method to
	 * avoid race condition on the account modifying.
	 * 
	 * @param amount
	 *            double Amount for deposit. Must be positive
	 * @return boolean True if deposit success
	 */
	public synchronized boolean deposit(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException(
					"amount must be greater than zero");
		} else {
			this.doTransaction(amount, "deposit");
			return true;
		}
	}

	/**
	 * This method is used to withdraw money from bank. Synchronized method to
	 * avoid race condition on the account modifying
	 * 
	 * @param amount
	 *            double The amount of withdraw
	 * @return boolean return true if withdraw success and false if fail
	 */
	public synchronized boolean withdraw(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException(
					"amount must be greater than zero");
		} else {
			// Can't withdraw more money than the account balance.
			if (amount > balance) {
				//Tell user the amount they want to withdraw exceed the balance
				//If there are other ways to show user messages, change here
				System.out
						.println("Withdraw amount exceed available funds in the account.");
				return false;
			} else {
				this.doTransaction(-amount, "withdraw");
				return true;
			}
		}
	}

	/**
	 * Transfer money between accounts. Synchronized method to avoid race
	 * condition on the account modifying
	 * 
	 * @param amount
	 *            double The amount for transfer.
	 * @param account
	 *            Account The account to which the money transfer.
	 */
	public synchronized boolean transfer(double amount, Account account) {
		if (amount <= 0) {
			throw new IllegalArgumentException(
					"amount must be greater than zero");
			// The transfer amount must not be larger than available balance.
		} else if (amount <= balance) {
			this.doTransaction(-amount, "To " + account.getAccountName());
			account.doTransaction(amount, "From " + this.getAccountName());
			return true;
		} else {
			//Tell user they have insufficient funds to do transfer
			//If there are other ways to show user messages, change here
			System.out.println("Insufficient funds in your account.");
			return false;
		}

	}

	/**
	 * This is the actual method that do the transaction Synchronized method to
	 * avoid race condition on the account modifying
	 * 
	 * @param amount
	 *            double Amount for transaction
	 * @param type
	 *            String To indicate the transaction type.
	 */
	public synchronized void doTransaction(double amount, String type) {
		this.balance += amount;
		this.transactions.add(new Transaction(amount, type));
	}

	// Abstract method must me implemented. Different accounts have different
	// interest calculation formula
	public abstract double interestEarned();

	public String getAccountName() {
		return this.accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

}
