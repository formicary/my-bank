package com.abc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Account {

	public static final int CHECKING = 0;
	public static final int SAVINGS = 1;
	public static final int MAXI_SAVINGS = 2;
	public static int accountIDAssign = 0;

	private int accountID;
	private final int accountType;
	private List<Transaction> transactions;

	public Account(int accountType) {
		this.accountID = accountIDAssign++;
		this.accountType = accountType;
		this.transactions = new ArrayList<Transaction>();
	}

	public void deposit(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else {
			transactions.add(new Transaction(amount));
		}
	}

	public void withdraw(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else {
			transactions.add(new Transaction(-amount));
		}
	}

	public double interestEarned() {
		double balance = getBalance();
		double balanceMinusInterest = 0.0;

		for (Transaction t : transactions) {
			balanceMinusInterest+=t.getAmount();
		}
		
		double res = balance - balanceMinusInterest;
		return ((double) Math.round(res * 100) / 100);
//		return balance - balanceMinusInterest;
//		
//		switch (accountType) {
//		case CHECKING:
//			return balance * 0.001;
//		case SAVINGS:
//			if (balance <= 1000)
//				return balance * 0.001;
//			else
//				return 1 + (balance - 1000) * 0.002;
//		case MAXI_SAVINGS:
//			if (transactions.size() < 1) {
//				return balance * 0.05;
//			} else {
//				return balance * getCurrentInterestRate(balance);
//			}
//		default:
//			return 0;
//		}
	}

	private Transaction getLastWithdrawal() {
		for (int i = transactions.size() - 1; i > 0; i--) {
			if (transactions.get(i).getAmount() < 0) {
				return transactions.get(i);
			}
		}

		return null;
	}

	/**
	 * Calculate the balance of this account as the sum of all Transactions
	 * @return balance - sum of all transactions
	 */
	public double getBalance() {
		if (transactions.size() < 1) {
			return 0;
		} 

		double amount = 0.0;
		Transaction dummyTransaction = new Transaction(0.0);
		transactions.add(dummyTransaction);
		Date currDate = transactions.get(0).getDate();
		for (Transaction t : transactions) {
			Date tDate = t.getDate();
			int daysBetweenDates = daysBetween(tDate, currDate);

			if (daysBetweenDates != 0 && amount > 0) { // Calculate accrued compound interest between days
				currDate = tDate;
				double currInterestRate = getCurrentInterestRate(amount);
				amount = amount * (Math.pow((1 + (currInterestRate / 365)), daysBetweenDates));
			}
			amount += t.getAmount();
		}
		
		transactions.remove(dummyTransaction);
		return ((double) Math.round(amount * 100) / 100);
	}

	/**
	 * For a given balance, calculate the correct interest rate for this account
	 * 
	 * @param balance
	 *            - Sum of all Transactions
	 * @return interestRate - Correct interest rate for given balance
	 */
	private double getCurrentInterestRate(double balance) {
		if (accountType == 0) {
			return 0.001;
		} else if (accountType == 1) {
			if (balance <= 1000) {
				return 0.001;
			} else {
				return 0.002;
			}
		} else if (accountType == 2) {
			Transaction lastWithdrawal = getLastWithdrawal();
			if (lastWithdrawal == null) {
				return 0.05;
			}

			Date lastTWithdrawalDate = lastWithdrawal.getDate();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(DateProvider.getInstance().now());
			calendar.add(Calendar.DATE, -10);
			Date tenDaysAgo = calendar.getTime();

			if (lastTWithdrawalDate.after(tenDaysAgo)) {
				return 0.001;
			} else {
				return 0.05;
			}
		}
		return 0;
	}

	public int getAccountType() {
		return accountType;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	private int daysBetween(Date d1, Date d2) {
		long difference = Math.abs(d1.getTime() - d2.getTime());
		float daysBetween = (difference / (1000 * 60 * 60 * 24));
		return Math.round(daysBetween);
	}

}
