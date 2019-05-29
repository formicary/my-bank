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
		switch (accountType) {
		case CHECKING:
			return balance * 0.001;
		case SAVINGS:
			if (balance <= 1000)
				return balance * 0.001;
			else
				return 1 + (balance - 1000) * 0.002;
		case MAXI_SAVINGS:
			if (transactions.size() < 1 ) {
				return balance * 0.05;
			}
			
			Transaction lastWithdrawal = getLastWithdrawal();
			if(lastWithdrawal == null)
				return balance * 0.05;
			
			Date lastTWithdrawalDate = transactions.get(transactions.size() - 1).getDate();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(DateProvider.getInstance().now());
			calendar.add(Calendar.DATE, -10);
			Date tenDaysAgo = calendar.getTime();

			if (lastTWithdrawalDate.after(tenDaysAgo)) {
				return balance * 0.001;
			} else {
				return balance * 0.05;
			}
		default:
			return 0;
		}
	}
	
	private Transaction getLastWithdrawal() {
		for(int i = transactions.size() - 1; i > 0; i--) {
			if(transactions.get(i).getAmount() < 0) {
				return transactions.get(i);
			}
		}
		
		return null;
	}

//	private static void calculateCompoundInterest(double rate) {
//		double compoundInterest = principle * (Math.pow((1 + rate / 365), time));
//	}

	public double getBalance() {
		if (transactions.size() < 1) {
			return 0;
		}

		double amount = 0.0;
		Date currDate = transactions.get(0).getDate();
		for (Transaction t : transactions) {
			Date tDate = t.getDate();
			int daysBetweenDates = daysBetween(tDate, currDate);

			if (daysBetweenDates == 0) {
				amount+= t.getAmount();
			} else { // Calculate accrued compound interest between days
				currDate = tDate;
				amount = amount * (Math.pow((1 + 0.01 / 365), daysBetweenDates/365));
			}
		}

		return amount;
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
