package com.abc;

import java.util.Date;
import java.util.LinkedList;
import java.time.temporal.ChronoUnit;

public class Account {

	public static final int CHECKING = 0;
	public static final int SAVINGS = 1;
	public static final int MAXI_SAVINGS = 2;
	public static final int DAYS_IN_YEAR = 365;
	public static final int CHECKFOR_WITHDRAWAL_DAYS = 10;

	private final int accountType;

	public LinkedList<Transaction> transactions;

	public Account(int accountType) {
		this.accountType = accountType;
		this.transactions = new LinkedList<Transaction>();
	}

	public void deposit(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException(
					"amount must be greater than zero");
		} else {
			transactions.add(new Transaction(amount));
		}
	}

	public void withdraw(double amount) {
		double balance = sumTransactions();
		if (amount <= 0) {
			throw new IllegalArgumentException(
					"amount must be greater than zero");
		} else if (amount > balance) {
			throw new IllegalArgumentException("Insufficient Balance");
		} else
			transactions.add(new Transaction(-amount));
	}

	public void transfer(Account fromAccount, Account toAccount, double amount) {
		/*
		 * Method to transfer money between accounts, takes arguments
		 * fromAccount, toAccount and the amount of money being transfered
		 */
		if (amount <= 0) {
			throw new IllegalArgumentException(
					"amount must be greater than zero");
		}

		else {
			fromAccount.withdraw(amount);
			toAccount.deposit(amount);

		}
	}

	public double interestEarned(Account forAccount) {
		/*
		 * Method taking arguments of specific account interest earned is to be
		 * calculated for, using the current date to check the interest accrued,
		 * returns interest earned
		 */
		double dayBalance = 0.0;
		double interestEarned = 0.0;
		Date previousDate = null;
		Date currentTransactionDate = null;
		for (Transaction t : forAccount.transactions) {
			currentTransactionDate = new Date(t.transactionDate.getTime());
			if (previousDate == null) {
				previousDate = currentTransactionDate;
				dayBalance += t.amount;
			} else if (currentTransactionDate.equals(previousDate)) {
				previousDate = currentTransactionDate;
				dayBalance += t.amount;
			} else {
				interestEarned += calculateInterest(forAccount, previousDate,
						currentTransactionDate, dayBalance);
				previousDate = currentTransactionDate;
				dayBalance += t.amount;

			}
		}
		if (currentTransactionDate.equals(previousDate)) {
			interestEarned += calculateInterest(forAccount, previousDate,
					DateProvider.getInstance().now(), dayBalance);
		}

		return interestEarned;

	}

	public double sumTransactions() {
		return checkIfTransactionsExist(true);
	}

	private double checkIfTransactionsExist(boolean checkAll) {
		double amount = 0.0;
		for (Transaction t : transactions) {
			amount += t.amount;
		}

		return amount;

	}

	public double calculateInterest(Account forAccount, Date fromDate,
			Date toDate, double balanceAmount) {
		/*
		 * Calculates interest based on difference between two dates which are
		 * arguments as well as the balance amount, returns compound interest
		 */
		long interestPeriod = 0;

		interestPeriod = ChronoUnit.DAYS.between(fromDate.toInstant(),
				toDate.toInstant());

		if (interestPeriod > 0) {
			switch (getAccountType()) {
			case SAVINGS:
				if (balanceAmount <= 1000)
					return getCompoundInterest(balanceAmount, DAYS_IN_YEAR,
							0.001, (double) interestPeriod / DAYS_IN_YEAR);

				else
					return getCompoundInterest(1000, DAYS_IN_YEAR, 0.001,
							(double) interestPeriod / DAYS_IN_YEAR)
							+ getCompoundInterest(balanceAmount - 1000,
									DAYS_IN_YEAR, 0.002,
									(double) interestPeriod / DAYS_IN_YEAR);

			case MAXI_SAVINGS:
				if (interestPeriod > CHECKFOR_WITHDRAWAL_DAYS)
					return getCompoundInterest(balanceAmount, DAYS_IN_YEAR,
							0.05, (double) interestPeriod / DAYS_IN_YEAR);

				else if ((interestPeriod <= CHECKFOR_WITHDRAWAL_DAYS)
						&& !(checkIfWithdraw(forAccount, fromDate, toDate)))
					return getCompoundInterest(balanceAmount, DAYS_IN_YEAR,
							0.05, (double) interestPeriod / DAYS_IN_YEAR);
				else if ((interestPeriod <= CHECKFOR_WITHDRAWAL_DAYS)
						&& (checkIfWithdraw(forAccount, fromDate, toDate)))
					return getCompoundInterest(balanceAmount, DAYS_IN_YEAR,
							0.001, (double) interestPeriod / DAYS_IN_YEAR);

			default:
				return getCompoundInterest(balanceAmount, DAYS_IN_YEAR, 0.001,
						(double) interestPeriod / DAYS_IN_YEAR);
			}

		}
		return 0;
	}

	private Boolean checkIfWithdraw(Account forAccount, Date fromDate,
			Date toDate) {
		for (Transaction t : forAccount.transactions) {
			if ((t.transactionDate.equals(fromDate) || t.transactionDate
					.equals(toDate)) && (t.amount < 0))
				return true;
		}

		return false;

	}

	public int getAccountType() {
		return accountType;
	}

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
	 * Involved in years i.e. 365 days r = Interest Rate as a decimal n = number
	 * of compounding periods per unit t; at the END of each period, i.e. for 10
	 * days of interest period, n=10/365
	 */
	public double getCompoundInterest(double p, int t, double r, double n) {
		double amount = p * Math.pow((1 + (r / t)), (n * t));

		double interest = amount - p;
		return interest;

	}

}
