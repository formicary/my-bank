package com.abc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
		switch (accountType) {
		case CHECKING:
			return balance * 0.001;
		case SAVINGS:
			if (balance <= 1000)
				return balance * 0.001;
			else
				return 1 + (balance - 1000) * 0.002;
		case MAXI_SAVINGS:
			if(transactions.size()<1) {
				return balance * 0.05;
			}
			
			Date lastTransactionDate = transactions.get(transactions.size()-1).getDate();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(DateProvider.getInstance().now());
			calendar.add(Calendar.DATE, -10);
			Date tenDaysAgo = calendar.getTime();

			if (lastTransactionDate.after(tenDaysAgo)) {
				return balance * 0.001;
			} else {
				return balance * 0.05;
			}
		default:
			return 0;
		}
	}

	private static void calculateCompoundInterest(double rate) {
		double principle = 10000, time = 5;
		
		double dailyInterestRate = rate/365;

		double compoundInterest = principle * (Math.pow((1 + rate / 365), time));
	}

	public double getBalance() {
		double amount = 0.0;
		for (Transaction t : transactions)
			amount += t.getAmount();
		return amount;
	}

	private Transaction getLastTransaction() {
		return null;
	}

	public int getAccountType() {
		return accountType;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

}
