package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Abstract Account class is extended by Checking, Savings and MaxiSavings classes. These three classes differ on how
 * the interest rate is calculated but share the basic operations of an account.
 */
public abstract class Account {

	/**
	 * Account has a unique id, in reality this should be complex but for the sake of this exercise an incrementing
	 * static integer to identify each object would suffice.
	 *
	 * A double to keep track of the balance of the account.
	 * 
	 * A double to represent the interest rate in percentage i.e 8% is 8.0, this is important
	 * 
	 * A list containing the transactions made by the account.
	 */
	private static int count = 0;
	private int accountNumber;
	private double balance;
	private double interestRate;
	private List<Transaction> transactions;

	/*
	 * Constructor
	 */
	public Account() {
		this.transactions = new ArrayList<Transaction>();
		accountNumber = count;
		count++;
	}

	/**
	 * Deposit money to an account
	 * 
	 * @param amount
	 *            value to deposit to an account
	 * @throws IllegalArgumentException
	 */
	public void deposit(double amount) throws IllegalArgumentException {
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else {
			Transaction t = new Transaction(amount);
			t.setTransactionType("deposit");
			transactions.add(t);
			balance += amount;
		}
	}

	/**
	 * Withdraw money from an account
	 * 
	 * @param amount
	 *            value to withdraw from an account
	 * @throws IllegalArgumentException
	 */
	public void withdraw(double amount) throws IllegalArgumentException {
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else if (amount > balance) {
			throw new IllegalArgumentException("withdrawal amount must be less than or equal to balance");
		} else {
			Transaction t = new Transaction(amount);
			t.setTransactionType("withdraw");
			transactions.add(t);
			balance -= amount;
		}
	}

	/**
	 * Customer can transfer money from account a to account b
	 * 
	 * @param amount
	 *            amount to transfer
	 * @param to
	 *            account to transfer money to
	 * @throws IllegalArgumentException
	 */
	public void transfer(double amount, Account to) throws IllegalArgumentException {
		if (this.getAccountNumber() == to.getAccountNumber()) {
			throw new IllegalArgumentException("THe two accounts must be differnt");
		} else {
			try {
				this.withdraw(amount);
				to.deposit(amount);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	/**
	 * calculates the sum of transactions
	 * 
	 * @return double sum of transactions
	 */
	public double sumTransactions() {
		double amount = 0.0;
		for (Transaction t : transactions)
			if (t.getTransactionType() == "withdraw") {
				amount -= t.getAmount();
			} else {
				amount += t.getAmount();
			}
		return amount;
	}

	/*
	 * Getters
	 */
	public double getBalance() {
		return balance;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	/**
	 * Generates a list of transactions made from the past days in this account
	 * 
	 * @param daysnumber
	 *            of days in the past
	 * @return List<Transaction> list of transactions for the past number of days
	 * @throws IllegalStateException
	 */
	public List<Transaction> getTransactions(int days) throws IllegalStateException {
		if (!transactions.isEmpty()) { // only proceed if this account made at least one transaction
			List<Transaction> l = new ArrayList<Transaction>();
			Date pastDate = DateProvider.giveDayInThePast(days); // Generate the day in the past
			for (Transaction t : this.transactions) {
				if (t.getTransactionDate().after(pastDate)) { // take all the transactions that are after this past date
					l.add(t);
				}
			}
			return l;
		} else {
			throw new IllegalStateException("No transactions has been made by this account yet");
		}
	}

	/**
	 * Setter for interestRate
	 * 
	 * @param rate
	 *            rate of interest
	 */
	public void setInterestRate(double rate) {
		this.interestRate = rate;
	}

	public String ToString() {
		return "Balance: " + Formatter.toDollars(balance);
	}

	/*
	 * Updates balance to include the interest earned after a year
	 */
	public void addInterestToBalance() {
		balance += interestEarned(this.balance, this.interestRate, 365, 1);
	}

	/**
	 * Calculates compound interest earned
	 * 
	 * @param principal
	 *            starting amount to earn interest
	 * @param rate
	 *            interest rating
	 * @param compoundTimes
	 *            compounded how many times in a year for daily 365, monthly 12, yearly 1
	 * @param periodInYears
	 *            how many years would it take.
	 * @return amount earned through interest.
	 */
	public abstract double interestEarned(double principal, double rate, double compoundTimes, double periodInYears);
}
