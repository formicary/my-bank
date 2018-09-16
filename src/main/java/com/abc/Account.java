package com.abc;

import java.util.ArrayList;
import java.util.List;

/**
 * An {@link Account} records any deposits and withdrawals, and interest earned.
 */
public abstract class Account {
    private static final String ERROR_MESSAGE_AMOUNT_GREATER_THAN_ZERO = "amount must be greater than zero";
	private List<Transaction> transactions;
	private final String accountNumber;
    
    /**
     * Constructs a new {@link Account}.
     * @param accountType The account type.
     */
    public Account(String accountNumber) {
        this.accountNumber = accountNumber;
		this.transactions = new ArrayList<Transaction>();
    }
    
	/**
	 * Calculates the interest earned by an {@link Account}.
	 * @return The interest earned.
	 */
    public abstract double interestEarned();
    
    /**
     * Returns the name of the account type.
     * @return See above.
     */
    public abstract String getAccountName();
    
    /**
     * Deposits money into the {@link Account}.
     * @param amount The amount of money to deposit.
     */
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException(ERROR_MESSAGE_AMOUNT_GREATER_THAN_ZERO);
        } else {
            transactions.add(new Transaction(amount, DateProvider.getInstance().now()));
        }
    }
    
    /**
     * Withdraws money from the {@link Account}.
     * @param amount The amount of money to withdraw.
     */
	public void withdraw(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException(ERROR_MESSAGE_AMOUNT_GREATER_THAN_ZERO);
		} else {
			transactions.add(new Transaction(-amount, DateProvider.getInstance().now()));
		}
	}
	
    /**
     * Sums the transactions of the {@link Account}.
     * @return the sum of the transactions.
     */
	public double sumTransactions() {
		double amount = 0.0;
		for (Transaction transaction : transactions) {
			amount += transaction.getAmount();	
		}
		return amount;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}
}
