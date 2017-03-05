package com.abc;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class Account {



	private Map<Transaction, Integer> transactions;
	private int accountNo;
	private static int lastAccountNo = 0;

	/**
	 * Create account with list of transactions and assign sequential account number
	 */
	public Account() {
		this.transactions = new LinkedHashMap<Transaction, Integer>();
		lastAccountNo++;
		this.accountNo = lastAccountNo;
	}

	/**
	 * Deposit a new amount into an account.
	 * If amount less than or equal to zero, user notified.
	 * Otherwise, amount added to LinkedHashMap of transactions
	 * and method returns true.
	 * @param amount to deposit
	 * @return result of transaction
	 */
	public boolean deposit(double amount) {
		boolean result = false;
		if(amount <= Common.MONEY_ZERO) {
			System.err.println("Unable to deposit: Amount to deposit must be greater than zero");
		} else {
			getTransactions().put(new Transaction(amount), Common.DEPOSIT);
			result = true;
		}
		return result;
	}
	/**
	 * Deposit a new amount into an account.
	 * If amount less than or equal to zero, user notified.
	 * Otherwise, amount added to LinkedHashMap of transactions
	 * and method returns true.
	 * @param amount to deposit
	 * @param type of transaction
	 * @return result of transaction
	 */
	private boolean deposit(double amount, int flag) {
		boolean result = false;
		int type = (flag == Common.TRANSFER ? Common.TRANSFER_DEPOSIT : Common.DEPOSIT);
		if(amount <= Common.MONEY_ZERO) {
			System.err.println("Unable to deposit: Amount to deposit must be greater than zero");
		} else {
			getTransactions().put(new Transaction(amount), type);
			result = true;
		}
		return result;
	}	
	/**
	 * Withdraw a new amount from an account.
	 * If amount less than or equal to zero, user notified.
	 * If total account balance less than amount to withdraw, user notified.
	 * Otherwise, negative amount added to LinkedHashMap of transactions
	 * and method returns true.
	 * @param amount to withdraw
	 * @return result of transaction
	 */
	public boolean withdraw(double amount) {
		boolean result = false;
		if (amount <= +Common.MONEY_ZERO) {
			System.err.println("Unable to withdraw: Amount to withdraw must be greater than zero");
		}
		else {
			if (sumTransactions() < amount) {
				System.err.println("Unable to withdraw: Insufficient funds");
			}
			else {
				getTransactions().put(new Transaction(-amount), Common.WITHDRAW);
				result = true;
			}
		}
		return result;

	}
	/**
	 * Withdraw a new amount from an account.
	 * If amount less than or equal to zero, user notified.
	 * If total account balance less than amount to withdraw, user notified.
	 * Otherwise, negative amount added to LinkedHashMap of transactions
	 * and method returns true.
	 * @param amount to withdraw
	 * @param flag type of transaction
	 * @return result of transaction
	 */
	private boolean withdraw(double amount, int flag) {
		boolean result = false;
		int type = (flag == Common.TRANSFER ? Common.TRANSFER_WITHDRAW : Common.WITHDRAW);
		if (amount <= +Common.MONEY_ZERO) {
			System.err.println("Unable to withdraw: Amount to withdraw must be greater than zero");
		}
		else {
			if (sumTransactions() < amount) {
				System.err.println("Unable to withdraw: Insufficient funds");
			}
			else {
				getTransactions().put(new Transaction(-amount), type);
				result = true;
			}
		}
		return result;

	}
	/**
	 * Transfer to another bank account
	 * @param account account to transfer to
	 * @param amount amount to transfer
	 */	
	public void transferTo(Account account, double amount) {
		boolean validCheck = false;
		try {
			validCheck = withdraw(amount, Common.TRANSFER);
			if(validCheck) {
				account.deposit(amount, Common.TRANSFER);
			}			
		} catch (NullPointerException e) {
			System.err.println("Error: The specified account does not exist.\nTransaction unauthorized.");
		}

	}

	/**
	 * Calculates the interest earned on an account
	 * Logic defined in subclass
	 * @return interest earned
	 */
	public abstract double interestEarned();

	/**
	 * Check if list of transactions is empty
	 * @return true if list of transactions empty; 
	 * false if list of transactions populated
	 */
	private boolean isTransactionsEmpty() {
		return (getTransactions() == null || getTransactions().isEmpty()); 
	}

	/**
	 * Sum all transactions to calculate total account balance
	 * @return account balance
	 */
	public double sumTransactions() {
		double amount = 0.00;
		for (Map.Entry<Transaction, Integer> entry: getTransactions().entrySet()) {
			amount += entry.getKey().getAmount();
		}
		return amount;
	}

	public Map<Transaction, Integer> getTransactions() {
		return transactions;
	}

	public int getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(int accountNo) {
		this.accountNo = accountNo;
	}	

}
