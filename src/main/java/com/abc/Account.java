package com.abc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public abstract class Account {

	private int accountNumber;
	private BigDecimal balance;
	private List<Transaction> transactions;
	private double interestRate;
	private BigDecimal interestPaid;
	private LocalDate dateCreated;
	private String accountName;

	public Account(int accountNumber) {
		this.accountNumber = accountNumber;

		balance = BigDecimal.ZERO;
		transactions = new ArrayList<Transaction>();
		// Flat interest rate of 0.1%
		interestRate = 1;
		interestPaid = BigDecimal.ZERO;
		dateCreated = DateProvider.getInstance().getCurrentDate();
	}

	public abstract void deposit(BigDecimal amount);

	public abstract boolean withdraw(BigDecimal amount);

	public abstract void dailyTasks();

	// BigDecimal implementation of (p(1 + r/n)^(nt)) - p to calculate compound
	// interest
	public BigDecimal compoundInterest(BigDecimal principle) {
		int numberCompounded = 365;
		double time = (double) dateCreated.until(DateProvider.getInstance().getCurrentDate(), ChronoUnit.DAYS) / 365;
		// (1 + r/n)^(nt)
		double rateCompoundTime = Math.pow((1 + ((interestRate / 100) / numberCompounded)), numberCompounded * time);

		// Implement compound interest formula to get new amount and interest earned
		BigDecimal amount = principle.multiply(new BigDecimal(rateCompoundTime)).setScale(2, RoundingMode.CEILING);
		BigDecimal compoundInterest = amount.subtract(principle);

		interestPaid = interestPaid.add(compoundInterest);

		return compoundInterest;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	public BigDecimal getInterestPaid() {
		return interestPaid;
	}

	// Created for JUnit testing purposes
	public void setInterestPaid(BigDecimal interestPaid) {
		this.interestPaid = interestPaid;
	}

	public LocalDate getDateCreated() {
		return dateCreated;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String name) {
		accountName = name;
	}
	
}
