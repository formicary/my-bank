package main.java.com.abc;

import java.util.ArrayList;
import java.util.List;

/**
 * Account class to hold information relevant to the account (type, balance,
 * transactions), as well as relevant actions (deposit, withdraw and calculation
 * of interest)
 * 
 * @author Stavros Mobile
 * 
 */
public class Account {

	/**
	 * Different types of bank account
	 */
	public enum Type {
		CHECKING, SAVINGS, MAXI_SAVINGS
	};

	/**
	 * Current account type
	 */
	private final Type accountType;

	/**
	 * Account balance
	 */
	private double balance;

	/**
	 * List of transactions of the account
	 */
	private List<Transaction> transactions;

	/**
	 * Constructor
	 * 
	 * @param accountType
	 */
	public Account(Type accountType) {
		this.accountType = accountType;
		this.balance = 0.;
		this.transactions = new ArrayList<Transaction>();
	}

	/**
	 * Deposit to account
	 * 
	 * @param amount
	 */
	public void deposit(double amount) {
		if (amount <= 0.) {
			throw new IllegalArgumentException(
					"Amount must be greater than zero");
		} else {
			balance += amount;
			transactions.add(new Transaction(amount));
		}
	}

	/**
	 * Withdraw from account
	 * 
	 * @param amount
	 */
	public void withdraw(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException(
					"Amount must be greater than zero");
		} else if (amount > this.balance) {
			throw new IllegalArgumentException(
					"The requested transaction cannot be completed");
		} else {
			balance -= amount;
			transactions.add(new Transaction(-amount));
		}
	}

	/**
	 * Interest of account according to type and current balance
	 * 
	 * @return
	 */
	public double interestEarned() {
		switch (accountType) {
		case CHECKING:
			return this.balance * 0.001;
		case SAVINGS:
			if (this.balance <= 1000)
				return this.balance * 0.001;
			else
				return 1 + (this.balance - 1000) * 0.002;
		case MAXI_SAVINGS:

			int dayDif = DateProvider.getInstance().getDay()
					- this.transactions.get(this.transactions.size() - 1)
							.getDay();
			if (dayDif < 0)
				dayDif += 365;
			if (dayDif > 10)
				return this.balance * .05;
			else
				return this.balance * .001;
		default:
			return 0;
		}
	}

	/**
	 * 
	 * @return Account type
	 */
	public Type getAccountType() {
		return accountType;
	}

	/**
	 * 
	 * @return Balance
	 */
	public double getBalance() {
		return this.balance;
	}

	/**
	 * 
	 * @param i
	 * @return Requested transaction of it exists
	 */
	public Transaction getTransaction(int i) {
		if (i >= this.transactions.size())
			throw new IllegalArgumentException("Invalid transaction request");
		else
			return this.transactions.get(i);
	}

	/**
	 * 
	 * @return Total number of transactions completed
	 */
	public int getNumberOfTransactions() {
		return this.transactions.size();
	}

}