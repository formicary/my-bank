package com.abc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Account {

	public static final int CHECKING = 0;
	public static final int SAVINGS = 1;
	public static final int MAXI_SAVINGS = 2;

	private final int accountType;
	public List<Transaction> transactions;
	private double balance;
	

	public Account(int accountType) {
		this.accountType = accountType;
		this.transactions = new ArrayList<Transaction>();
		this.balance=0.0;
	}

	public void deposit(double amount,int accountType) {
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else {
			balance += amount;
			transactions.add(new Transaction(amount,accountType));
		}
	}

	public void withdraw(double amount, int accountType) {
		if (amount <= 0&&amount<getBalance()) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else {
			balance = balance-amount;
			transactions.add(new Transaction(-amount,accountType));
		}
	}

	public double interestEarned() {
		double amount = sumTransactions();
		switch (accountType) {
			case SAVINGS:
				return interestSavings(amount);
			case MAXI_SAVINGS:
				return interestMaxiSavings(amount);
			default:
				return amount * 0.001;	
		}
		
	}

	private double interestMaxiSavings(double amount) {
		for (Transaction transaction : transactions) {
			if (checkLastTenDaysTransactions(transaction)&&transaction.getAmount()<0)
				return amount * 0.001;
		}
		return amount * 0.05;
	}

	private double interestSavings(double amount) {
		return amount<=1000 ? amount*0.001 : 1 + (amount - 1000) * 0.002;
	}

	private boolean checkLastTenDaysTransactions(Transaction transaction) {
		Long transactionDayInMillSec =  transaction.getTransactionDateMillSec();
		Long todayInMillSec = System.currentTimeMillis();
		Long tenDayInMillSec = 864000000L;
		return transactionDayInMillSec>todayInMillSec-tenDayInMillSec;
	}

	public double sumTransactions() {
		double amount = 0.0;
		for (Transaction t : transactions)
			amount += t.amount;
		return amount;
	}

//	private double checkIfTransactionsExist(boolean checkAll) {
//		
//	}

	public int getAccountType() {
		return accountType;
	}

	public double getBalance() {
		return this.balance;
	}

}
