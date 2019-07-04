package com.abc;

import java.util.Date;
import java.util.LinkedList;
import java.time.temporal.ChronoUnit;

/**
 * Account class creates transactions for various types of account including
 * transfer request from one type of account to another type of account
 *
 * @version 2.0 03 July 2019
 * @updated by Dhurjati Dasgupta
 */

public class Account {

	public static final int CHECKING = 0;
	public static final int SAVINGS = 1;
	public static final int MAXI_SAVINGS = 2;
	public static final int DAYS_IN_YEAR = 365;
	public static final int CHECKFOR_WITHDRAWAL_DAYS = 10;

	private final int accountType;

	public LinkedList<Transaction> transactions; // linked list for transactions

	/*
	 * Sets accounts type and creates linked list of for transactions for the
	 * account type.
	 */
	public Account(int accountType) {

		this.accountType = accountType;

		this.transactions = new LinkedList<Transaction>();
	}

	/* process deposit requests */
	public void deposit(double amount) {
		/**
		 * If deposit amount is greater than zero then adds the amount to the
		 * transaction list otherwise throws error
		 */
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else {
			transactions.add(new Transaction(amount));
		}
	}

	/* process withdraw requests */
	public void withdraw(double amount) {
		/**
		 * If withdraw amount is greater than zero and if sufficient balance amount is
		 * available then adds to the transaction list otherwise throws error
		 */
		double balance = sumTransactions();
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else if (amount >= balance) {
			throw new IllegalArgumentException("Insufficient Balance");
		} else
			transactions.add(new Transaction(-amount));
	}

	/* transfers money from one account to another */
	public void transfer(Account fromAccount, Account toAccount, double amount) {
		/**
		 * If transfer amount is greater than zero then withdraws from the account from
		 * which amount to be transferred and deposits the same amount to the account to
		 * be transferred to
		 */

		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		}

		else {
			fromAccount.withdraw(amount); // checks in the withdraw method if sufficient balance amount
			toAccount.deposit(amount);

		}
	}

	/* This method calculates interest earned */
	public double interestEarned(Account forAccount) {
		double dayBalance = 0.0;
		double interestEarned = 0.0;
		Date previousDate = null;
		Date currentTransactionDate = null;
		for (Transaction t : forAccount.transactions) {
			/* Calculates balance amount for each transaction date */
			currentTransactionDate = new Date(t.transactionDate.getTime());
			if (previousDate == null) {
				previousDate = currentTransactionDate;
				dayBalance += t.amount;
			} else if (currentTransactionDate.equals(previousDate)) {
				previousDate = currentTransactionDate;
				dayBalance += t.amount;
			} else {
				interestEarned += calculateInterest(forAccount, previousDate, currentTransactionDate, dayBalance);
				previousDate = currentTransactionDate;
				dayBalance += t.amount;

			}
		}
		if (currentTransactionDate.equals(previousDate)) {
			/**
			 * Calculates interests of the balance amount based on number of days from the
			 * previous transaction date to the current transaction date
			 */
			interestEarned += calculateInterest(forAccount, previousDate, DateProvider.getInstance().now(), dayBalance);
		}

		return interestEarned;

	}

	/* Sums up transaction amount */
	public double sumTransactions() {
		return checkIfTransactionsExist(true);
	}

	/* Checks if transaction exists and sums up transaction amount */
	private double checkIfTransactionsExist(boolean checkAll) {
		double amount = 0.0;
		for (Transaction t : transactions) {
			amount += t.amount;
		}

		return amount;

	}

	/* Calculates interest earned */
	public double calculateInterest(Account forAccount, Date fromDate, Date toDate, double balanceAmount) {
		/**
		 * Calculates interests of the balance amount based on number of days between
		 * two dates based on interest rates of various account types and rules defined
		 * 
		 */

		long interestPeriod = 0;

		interestPeriod = ChronoUnit.DAYS.between(fromDate.toInstant(), toDate.toInstant());

		if (interestPeriod > 0) {
			switch (getAccountType()) {
			case SAVINGS:
				if (balanceAmount <= 1000)
					return getCompoundInterest(balanceAmount, DAYS_IN_YEAR, 0.001,
							(double) interestPeriod / DAYS_IN_YEAR);

				else
					return getCompoundInterest(1000, DAYS_IN_YEAR, 0.001, (double) interestPeriod / DAYS_IN_YEAR)
							+ getCompoundInterest(balanceAmount - 1000, DAYS_IN_YEAR, 0.002,
									(double) interestPeriod / DAYS_IN_YEAR);

			case MAXI_SAVINGS:
				if (interestPeriod > CHECKFOR_WITHDRAWAL_DAYS)
					return getCompoundInterest(balanceAmount, DAYS_IN_YEAR, 0.05,
							(double) interestPeriod / DAYS_IN_YEAR);

				else if ((interestPeriod <= CHECKFOR_WITHDRAWAL_DAYS)
						&& !(checkIfWithdraw(forAccount, fromDate, toDate)))
					return getCompoundInterest(balanceAmount, DAYS_IN_YEAR, 0.05,
							(double) interestPeriod / DAYS_IN_YEAR);
				else if ((interestPeriod <= CHECKFOR_WITHDRAWAL_DAYS)
						&& (checkIfWithdraw(forAccount, fromDate, toDate)))
					return getCompoundInterest(balanceAmount, DAYS_IN_YEAR, 0.001,
							(double) interestPeriod / DAYS_IN_YEAR);

			default:
				return getCompoundInterest(balanceAmount, DAYS_IN_YEAR, 0.001, (double) interestPeriod / DAYS_IN_YEAR);
			}

		}
		return 0;
	}

	/* checks if withdrawal or deposit */
	private Boolean checkIfWithdraw(Account forAccount, Date fromDate, Date toDate) {
		for (Transaction t : forAccount.transactions) {
			if ((t.transactionDate.equals(fromDate) || t.transactionDate.equals(toDate)) && (t.amount < 0))
				return true;
		}

		return false;

	}

	/* Returns Account Type as Integer Value */
	public int getAccountType() {
		return accountType;
	}

	/* Returns Account Type as Descriptive Value */
	public String getAccountType(int accountType) {
		switch (accountType) {
		case Account.CHECKING:
			return "Checking Account";
		case Account.SAVINGS:
			return "Savings Account";
		case Account.MAXI_SAVINGS:
			return "Maxi Savings Account";
		}
		return "Unknown";
	}

	/* Checks account types are defined account types */
	public Boolean checkAccountType(int accountType) {
		switch (accountType) {
		case Account.CHECKING:
			return true;
		case Account.SAVINGS:
			return true;
		case Account.MAXI_SAVINGS:
			return true;
		}
		return false;
	}

	/*
	 * Formula for compound interest calculation : p = Principal Amount t = Time
	 * Involved in years i.e. 365 days r = Interest Rate as a decimal n = number of
	 * compounding periods per unit t; at the END of each period, i.e. for 10 days
	 * of interest period, n=10/365
	 */
	public double getCompoundInterest(double p, int t, double r, double n) {
		double amount = p * Math.pow((1 + (r / t)), (n * t));

		double interest = amount - p;
		return interest;

	}

}
