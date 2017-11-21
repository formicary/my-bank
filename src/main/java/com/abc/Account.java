package com.abc;

import java.util.ArrayList;
import java.util.List;

/**
 * The Account Class represents an account owned by a customer
 * in a bank.
 * @author Matthew Mukalere
 * @version 0.5
 */
public class Account {

	public static final int CHECKING = 0;
	public static final int SAVINGS = 1;
	public static final int MAXI_SAVINGS = 2;

	private final int accountType;
	public List<Transaction> transactions;

	/**
	 * The Account constructor.
	 * @param accountType The type of account
	 */
	public Account(int accountType) {
		this.accountType = accountType;
		this.transactions = new ArrayList<Transaction>();
	}
	
	/**
	 * Deposit money into an account
	 * @param amount Amount of money to deposit
	 */
	public void deposit(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else {
			transactions.add(new Transaction(amount));
		}
	}
	
	/**
	 * Withdraw money from an account
	 * @param amount Amount of money to withdraw
	 */
	public void withdraw(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else {
			transactions.add(new Transaction(-amount));
		}
	}

	/**
	 * Interest earned based on savings amount.
	 * @return amount Amount of interest earned
	 */
	public double interestEarned() {
		double amount = sumTransactions();
		switch(accountType){
		case SAVINGS:
			if (amount <= 1000)
				return amount * 0.001;
			else
				return 1 + (amount-1000) * 0.002;
		case MAXI_SAVINGS:
			if (amount <= 1000)
				return amount * 0.02;
			if (amount <= 2000)
				return 20 + (amount-1000) * 0.05;
			return 70 + (amount-2000) * 0.1;
		default:
			return amount * 0.001;
		}
	}

	/**
	 * The sum of all transactions.
	 * @return amount The sum of all transactions
	 */
	public double sumTransactions() {
		double amount = 0.0;
		for (Transaction t: transactions)
			amount += t.amount;
		return amount;
	}

	/**
	 * The type of account.
	 * @return accountType Account type
	 */
	public int getAccountType() {
		return accountType;
	}

}
