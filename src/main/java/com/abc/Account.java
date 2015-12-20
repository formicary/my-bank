package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Account {

	private AccountType accountType;
	public List<Transaction> transactions;
	public double accountNumber;

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public Account(AccountType accountType) {
		this.accountType = accountType;
		this.transactions = new ArrayList<Transaction>();
		double accNumber = Math.random();
		Bank customers = new Bank();
		List<Customer> customerList = customers.getCustomerList();
		for(Customer c: customerList){
			List<Account> accounts = c.getAccounts();
    		for(Account acc: accounts){
    			if(acc.getAccountNumber() != accNumber){
    				this.accountNumber = accNumber;
    			}
    			else{
    				System.out.println("Account Number already exists");
    			}
    			}
		}
	}

	public void deposit(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else {
			transactions.add(new Transaction(amount));
		}
	}

	public void depositWithBackValueDate(double amount, Date transactionDate) {
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else {
			transactions.add(new Transaction(amount, transactionDate));
		}
	}

	public void withdraw(double amount) {
		double currentAmount = sumTransactions();
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else if (currentAmount < amount) {
			throw new IllegalArgumentException("No enough credit in your account");
		} else {
			transactions.add(new Transaction(-amount));
		}
	}

	public double interestEarned() {
		double amount = sumTransactions();

		switch (accountType) {
		case SAVINGS:
			if (amount <= 1000)
				return amount * 0.001;
			else
				return 1 + (amount - 1000) * 0.002;
			// case SUPER_SAVINGS:
			// if (amount <= 4000)
			// return 20;
		case MAXI_SAVINGS:
			if (transactions.get(transactions.size() - 1).getDate()
					.before(DateProvider.getInstance().dateTenDaysBefore()))
				return amount * 0.05;
			else
				return amount * 0.001;
		default:
			return amount * 0.001;
		}
	}

	public double interestPerDay(Date d) {
		double amount = sumTransactionsPerDay(d);
		switch (accountType) {
		case CHECKING:
			return amount * (0.001 / 365);
		case SAVINGS:
			if (amount <= 1000)
				return amount * (0.001 / 365);
			else
				return amount * (0.001 / 365) + (amount - 1000) * (0.002 / 365);
		case MAXI_SAVINGS:
			for (int i = transactions.size() - 1; i >= 0; i--) {
				int diff = transactions.get(i).fromBegining(d);
				if (transactions.get(i).amount < 0) {
					if (diff <= 10 && diff >= 0)
						return amount * (0.001 / 365);
				}
			}
			return amount * (0.05 / 365);
		default:
			throw new RuntimeException("account type not recognized");
		}
	}

	public double sumTransactions() {
			double amount = 0.0;
			for (Transaction t : transactions)
				amount += t.amount;
			return amount;
	}

	public double sumTransactionsPerDay(Date d) {
		double amount = 0.0;
		for (Transaction t : transactions) {
			if (t.fromBegining(d) >= 0)
				;
			amount += t.amount;
		}
		return amount;
	}

	public AccountType getAccountType() {
		return accountType;
	}
	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}
	public double getAccountNumber() {
		return accountNumber;
	}
}
