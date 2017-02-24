package com.mybank.entities;

import java.util.ArrayList;
import java.util.List;

import com.mybank.calculators.InterestCalculator;
import com.mybank.exceptions.NotEnoughBalanceException;
import com.mybank.exceptions.UndefinedAccountTypeException;
import com.mybank.factories.InterestCalculatorFactory;

public class Account {

	public static final int CHECKING = 0;
	public static final int SAVINGS = 1;
	public static final int MAXI_SAVINGS = 2;

	private final int accountType;
	private List<Transaction> transactions;

	public Account(int accountType) {

		this.accountType = accountType;
		this.transactions = new ArrayList<Transaction>();
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void deposit(double amount) throws IllegalArgumentException {
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else {
			transactions.add(new Transaction(amount));
		}
	}

	public void withdraw(double amount) throws IllegalArgumentException, NotEnoughBalanceException {
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else if (getBalance() > amount) {
			transactions.add(new Transaction(-amount));
		} else
			throw new NotEnoughBalanceException();
	}

	public double getInterestEarnedCompundedDaily() throws UndefinedAccountTypeException {
		InterestCalculator calculator;
		switch (accountType) {
		case CHECKING:
			calculator = InterestCalculatorFactory.getAccountCalculator(this);
			return calculator.calculateInterest(transactions);	
		case SAVINGS:
			calculator = InterestCalculatorFactory.getAccountCalculator(this);
			return calculator.calculateInterest(transactions);
		case MAXI_SAVINGS:
			calculator = InterestCalculatorFactory.getAccountCalculator(this);
			return calculator.calculateInterest(transactions);
			
		default:
			throw new UndefinedAccountTypeException();
		}

	}

	public double getBalance() {
		double balance = 0.0;
		for (Transaction t : transactions)
			balance += t.getTransactionAmount();
		return balance;
	}

	public int getAccountType() {
		return accountType;
	}

}
