package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.List;

/**
 * A class to represent account objects. Accounts have a type and a list of
 * transactions.
 * 
 * @author JT
 *
 */
public class Account {

	public static final int CHECKING = 0;
	public static final int SAVINGS = 1;
	public static final int MAXI_SAVINGS = 2;

	private final int accountType;
	private List<Transaction> transactions;

	/**
	 * Constructor for the Account class. This constructor sets the account type and
	 * creates a list to store transactions.
	 * 
	 * @param accountType
	 *            The type of account.
	 */
	public Account(int accountType) {
		this.accountType = accountType;
		this.transactions = new ArrayList<Transaction>();
	}

	/**
	 * A method for depositing into the account.
	 * 
	 * @param amount
	 *            The amount that will be deposited.
	 * 
	 * @return A boolean true value if the deposit is successful.
	 * 
	 */
	public boolean deposit(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("Amount must be greater than zero.");
		} else {
			transactions.add(new Transaction(amount));
			return true;
		}
	}

	/**
	 * A method for withdrawing from the account.
	 * 
	 * @param amount
	 *            The amount to be withdrawn.
	 * 
	 * @return A boolean true value if the transaction is successful.
	 * @throws Exception 
	 */
	public boolean withdraw(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("Amount must be greater than zero.");
		} else {
			if(sumTransactions() >= amount) {
				transactions.add(new Transaction(-amount));
				return true;
			}else {
				throw new IllegalArgumentException("The balance of the account is less than the withdrawing amount.");
			}
			
		}
	}

	/**
	 * A method to add the daily interest earned.
	 */
	public void dailyInterestAddition() {
		deposit(interestEarned() / 365);
	}

	/**
	 * A method to calculate the interest earned on an account.
	 * 
	 * @return A double value of the interest earned from the account.
	 */
	public double interestEarned() {
		double amount = sumTransactions();
		switch (accountType) {
		case CHECKING:
			return amount * 0.001;

		case SAVINGS:
			if (amount <= 1000) {
				return amount * 0.001;
			} else {
				return 1 + (amount - 1000) * 0.002;
			}

		case MAXI_SAVINGS:
			Date tenDaysAgo = dateTenDaysAgo();
			int index = indexOfLastWithdrawal(transactions);
			boolean dateTest = transactions.get(index).getTransactionDate().before(tenDaysAgo);
			if (dateTest) {
				return amount * 0.05;
			} else {
				return amount * 0.001;
			}

		default:
			throw new IllegalArgumentException("Account type not recognised.");
		}
	}

	/**
	 * A method to sum the transactions.
	 * 
	 * @return A double value of the total balance of the account.
	 */
	public double sumTransactions() {
		double amount = 0.0;
		if (checkIfTransactionsExist()) {
			for (Transaction t : transactions)
				amount += t.getAmount();
		}
		return amount;
	}

	/**
	 * A method to check if any transactions exist.
	 * 
	 * @return A boolean true if transactions exist. A boolean false if transactions
	 *         do not exist.
	 */
	private boolean checkIfTransactionsExist() {
		if (transactions.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * A method to give the date ten days ago.
	 * 
	 * @return A Date object which has a time set to ten days ago.
	 */
	private Date dateTenDaysAgo() {
		Calendar tempCal = Calendar.getInstance();
		tempCal.add(Calendar.DATE, -10);
		Date tenDaysAgo = tempCal.getTime();
		return tenDaysAgo;
	}

	/**
	 * A method to find the last withdrawal from the account.
	 * 
	 * @param list
	 *            The list of account transactions.
	 * @return An int value which is the index of the last withdrawal in the list of
	 *         transactions.
	 */
	private int indexOfLastWithdrawal(List<Transaction> list) {
		int i = 0;
		for (int index = list.size() - 1; index >= 0; index--) {
			if (list.get(index).getAmount() < 0) {
				return index;
			}
		}
		return i;
	}

	/**
	 * A method to get the account type of the account.
	 * 
	 * @return The int value corresponding to the account type.
	 */
	public int getAccountType() {
		return accountType;
	}

	/**
	 * A method to get the transactions from the account.
	 * 
	 * @return The list of transactions from the account.
	 */
	public List<Transaction> getTransactions() {
		return transactions;
	}

}
