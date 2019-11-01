package com.abc;

import static java.lang.Math.abs;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Account {

	public static final int CHECKING = 0;
	public static final int SAVINGS = 1;
	public static final int MAXI_SAVINGS = 2;

	private final int accountType;
	public List<Transaction> transactions;

	public Account(int accountType) {
		this.accountType = accountType;
		this.transactions = new ArrayList<Transaction>();
	}

	public void deposit(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else {
			transactions.add(new Transaction(amount, Transaction.DEPOSIT));
		}
	}

	public void withdraw(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else {
			transactions.add(new Transaction(-amount, Transaction.WITHDRAWAL));
		}
	}

	public double interestEarned() {
		double amount = sumTransactions();
		double total = 0.0;
		double balance = 0.0;
		DecimalFormat df2 = new DecimalFormat("#.##");
		// sets the time as current
		long date = Calendar.getInstance().getTime().getTime();
		switch (accountType) {
		case SAVINGS:
			if (amount <= 1000) {
				// calculates the interest for each transaction from its date until now,
				// withdrawels will give negatives which will remove from overall interest
				// evening it out
				for (Transaction t : transactions)
					total += compoundInterest(t.amount, 0.001 / 365, (date - t.getDate().getTime()) / 86400000);
				return Double.parseDouble(df2.format(total));
			} else {
				// runs through each transaction
				for (Transaction t : transactions) {
					double interest = 0.0;
					// checks if balance is over 1000 currently
					if (balance > 1000)
						// if so applies compound interest of 0.2%
						total += compoundInterest(t.amount, 0.002 / 365, (date - t.getDate().getTime()) / 86400000);
					else if (total + t.amount < 1000) {
						// if the balance plus the amount of this transaction are under 1000 then it
						// uses the rate of 0.1%
						interest = compoundInterest(t.amount, 0.001 / 365, (date - t.getDate().getTime()) / 86400000);
						total += interest;
						balance += t.amount + interest;
					} else {
						// if some of this transaction is over 1000 and some isnt it calculates the
						// interest individually for each part

						interest = compoundInterest(1000 - total, 0.001 / 365,
								(date - t.getDate().getTime()) / 86400000);
						total += interest;
						balance += interest;
						interest = compoundInterest(t.amount - (1000 - total), 0.002 / 365,
								(date - t.getDate().getTime()) / 86400000);
						total += interest;
						balance += t.amount + interest;
					}

				}
				return Double.parseDouble(df2.format(total));
			}
		case MAXI_SAVINGS: // 864000000 = 10 days

			List<Transaction> deposits = new ArrayList<Transaction>();
			// set last withdrawal at a year before first transaction to prevent any issues
			long lastWithdrawal = transactions.get(0).getDate().getTime() - Long.parseLong("31536000000");
			double interest = 0.0;

			for (Transaction t : transactions) {
				// calculates 5% a base rate
				interest = compoundInterest(t.amount, 0.05 / 365, (date - t.getDate().getTime()) / 86400000);
				total += interest;
				balance += interest + t.amount;
				// if the transaction was made within 10 days of a withdrawal deduct the
				// interest for those days it is within 10 days of it
				if ((t.getDate().getTime() - 864000000) < lastWithdrawal) {
					// if the last withdrawal was within 10 days it removes interest that would have
					// been gained in that time
					interest = compoundInterest(t.amount, 0.049 / 365,
							(t.getDate().getTime() - lastWithdrawal) / 86400000);
					total -= interest;
					balance -= interest;
				}

				if (t.getTransactionType() == Transaction.WITHDRAWAL) {
					// if the transaction is a withdrawal it will then remove 4.9% of the interest
					// gain in 10 days for all previous transactions
					for (Transaction t2 : deposits) {
						interest = compoundInterest(t2.amount, 0.049 / 365, 10);
						total -= interest;
						balance -= interest;
					}
				} else
					deposits.add(t);

			}
		case CHECKING:
			// runs through each transaction calculating the amount of interest it receives
			for (Transaction t : transactions)
				total += compoundInterest(t.amount, 0.001 / 365, (date - t.getDate().getTime()) / 86400000);
			return Double.parseDouble(df2.format(total));
		default:
			throw new IllegalArgumentException("account type is not valid");
		}
	}

	// same as above but has date set as a year in the future, to test the daily
	// interest and within 10 day withdrawal interest
	public double interestEarnedAfterYear() {
		double amount = sumTransactions();

		double total = 0.0;
		double balance = 0.0;
		// used to round doubles to 2d.p
		DecimalFormat df2 = new DecimalFormat("#.##");

		// set time as 1 year in the future for testing purposes
		long date = Calendar.getInstance().getTime().getTime() + Long.parseLong("31536000000");
		switch (accountType) {
		case SAVINGS:
			if (amount <= 1000) {
				// calculates the interest for each transaction from its date until now,
				// withdrawels will give negatives which will remove from overall interest
				// evening it out
				for (Transaction t : transactions)
					total += compoundInterest(t.amount, 0.001 / 365, (date - t.getDate().getTime()) / 86400000);
				return Double.parseDouble(df2.format(total));
			} else {
				// runs through each transaction
				for (Transaction t : transactions) {
					double interest = 0.0;
					// checks if balance is over 1000 currently
					if (balance > 1000)
						// if so applies compound interest of 0.2%
						total += compoundInterest(t.amount, 0.002 / 365, (date - t.getDate().getTime()) / 86400000);
					else if (total + t.amount < 1000) {
						// if the balance plus the amount of this transaction are under 1000 then it
						// uses the rate of 0.1%
						interest = compoundInterest(t.amount, 0.001 / 365, (date - t.getDate().getTime()) / 86400000);
						total += interest;
						balance += t.amount + interest;
					} else {
						// if some of this transaction is over 1000 and some isnt it calculates the
						// interest individually for each part

						interest = compoundInterest(1000 - total, 0.001 / 365,
								(date - t.getDate().getTime()) / 86400000);
						total += interest;
						balance += interest;
						interest = compoundInterest(t.amount - (1000 - total), 0.002 / 365,
								(date - t.getDate().getTime()) / 86400000);
						total += interest;
						balance += t.amount + interest;
					}

				}
				return Double.parseDouble(df2.format(total));
			}
		case MAXI_SAVINGS: // 864000000 = 10 days

			List<Transaction> deposits = new ArrayList<Transaction>();
			// set last withdrawal at a year before first transaction to prevent any issues
			long lastWithdrawal = transactions.get(0).getDate().getTime() - Long.parseLong("31536000000");
			double interest = 0.0;

			for (Transaction t : transactions) {
				// calculates 5% a base rate
				interest = compoundInterest(t.amount, 0.05 / 365, (date - t.getDate().getTime()) / 86400000);
				total += interest;
				balance += interest + t.amount;
				// if the transaction was made within 10 days of a withdrawal deduct the
				// interest for those days it is within 10 days of it
				if ((t.getDate().getTime() - 864000000) < lastWithdrawal) {
					// if the last withdrawal was within 10 days it removes interest that would have
					// been gained in that time
					interest = compoundInterest(t.amount, 0.049 / 365,
							(t.getDate().getTime() - lastWithdrawal) / 86400000);
					total -= interest;
					balance -= interest;
				}

				if (t.getTransactionType() == Transaction.WITHDRAWAL) {
					// if the transaction is a withdrawal it will then remove 4.9% of the interest
					// gain in 10 days for all previous transactions
					for (Transaction t2 : deposits) {
						interest = compoundInterest(t2.amount, 0.049 / 365, 10);
						total -= interest;
						balance -= interest;
					}
				} else
					deposits.add(t);

			}
		case CHECKING:
			// runs through each transaction calculating the amount of interest it receives
			for (Transaction t : transactions)
				total += compoundInterest(t.amount, 0.001 / 365, (date - t.getDate().getTime()) / 86400000);
			// System.out.println(Double.parseDouble(df2.format(total)));
			return Double.parseDouble(df2.format(total));
		default:
			throw new IllegalArgumentException("account type is not valid");
		}
	}

	// function to calculate the compound interest after a given number of days
	private double compoundInterest(double amount, double rate, double days) {
		rate = Math.pow(rate + 1, days);
		double interest = (amount * rate) - amount;
		return interest;
	}

	public double sumTransactions() {
		// removed checkIfTransactionExists as irrelevant code jump
		double amount = 0.0;
		for (Transaction t : transactions)
			amount += t.amount;
		return amount;
	}

	public int getAccountType() {
		return accountType;
	}

}
