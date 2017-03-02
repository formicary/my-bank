package com.abc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Account {

	public static final int CHECKING = 0;
	public static final int SAVINGS = 1;
	public static final int MAXI_SAVINGS = 2;
	
	public static final int max = 100;
	public static final int min = 1;

	private final int accountType;
	private final int accountNumber;
	public List<Transaction> transactions;

	/**
	 * Constructor for the account
	 * @param accountType
	 */
	public Account(int accountType) {
		this.accountType = accountType;
		
		// Generating random account number and assigning to all the accounts opened.
		Random random = new Random();
		this.accountNumber =random.nextInt(max - min + 1) + min;
		
		this.transactions = new ArrayList<Transaction>();
	}

	/**
	 * Function to deposit the money into an account
	 * @param amount
	 */
	public void deposit(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else {
			transactions.add(new Transaction(amount));
		}
	}

	/**
	 * Function to withdraw money from an account
	 * @param amount
	 */
	public void withdraw(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else {
			transactions.add(new Transaction(-amount));
		}
	}

	/**
	 * Function to calculate simple interest earned by a customer
	 * @return simple interest earned on an account
	 */
	public double interestEarned() {
		double amount = sumTransactions();
		switch(accountType){
		case CHECKING:
			return amount * 0.001;  // CI = Math.pow((1 + (0.001/365)),365)
		case SAVINGS:
			if (amount <= 1000)
				return amount * 0.001;
			else
				return 1 + (amount-1000) * 0.002;
			//            case SUPER_SAVINGS:
			//                if (amount <= 4000)
			//                    return 20;
		case MAXI_SAVINGS:
			if (amount <= 1000)
				return amount * 0.02;
			if (amount <= 2000)
				return 20 + (amount-1000) * 0.05;
			return 70 + (amount-2000) * 0.1;
		default:
			return 0.0;
		}
	}
	
	/**
	 * Function to calculate compound interest accrued & compounded daily for a customer
	 * using formula A = P(1 + r/n) ^ t & CI = A - P
	 * @return compound interest earned
	 */
	public double compoundInterestEarned() {
		double amount = sumTransactions();
		switch(accountType){
		case CHECKING:
			return amount * Math.pow((1 + (0.001/365)),365) - amount;  
		case SAVINGS:
			if (amount <= 1000)
				return amount * Math.pow((1 + (0.001/365)),365) - amount;
			else
				return 1001.0004987954705 + (amount-1000) * Math.pow((1 + (0.002/365)),365) - amount;
		case MAXI_SAVINGS:
			if (amount <= 1000)
				return amount * Math.pow((1 + (0.02/365)),365) - amount;
			if (amount <= 2000)
				return 1020.200781032923 + (amount-1000) * Math.pow((1 + (0.05/365)),365) - amount;
			return 2071.4682775003703 + (amount-2000) * Math.pow((1 + (0.1/365)),365) - amount;
		default:
			return 0.0;
		}
	}

	public double sumTransactions() {
		return checkIfTransactionsExist(true);
	}

	private double checkIfTransactionsExist(boolean checkAll) {
		double amount = 0.0;
		for (Transaction t: transactions)
			amount += t.amount;
		return amount;
	}
	
	// Getter Methods
	public int getAccountType() {
		return accountType;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

}
