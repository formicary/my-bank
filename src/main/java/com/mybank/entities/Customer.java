package com.mybank.entities;

import java.util.ArrayList;
import java.util.List;

import com.mybank.exceptions.NonExistentAccountException;
import com.mybank.exceptions.NotEnoughBalanceException;
import com.mybank.exceptions.UndefinedAccountTypeException;

public class Customer {
	private String name;
	private List<Account> accounts;

	public Customer(String name) {
		this.name = name; 
		this.accounts = new ArrayList<Account>();
	}

	public String getName() {
		return name;
	}

	public Customer openAccount(Account account) {
		accounts.add(account);
		return this;
	}

	public int getNumberOfAccounts() {
		return accounts.size();
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public double totalInterestEarned() throws UndefinedAccountTypeException {
		double total = 0;
		for (Account a : accounts) {
			total += a.getInterestEarnedCompundedDaily();
		}
		return total;
	}

	public String getStatement() {
		try {
			String statement = "Statement for " + this.name + "\n";
			double total = 0.0;
			for (Account a : accounts) {
				statement += "\n" + statementForAccount(a) + "\n";
				total += a.getBalance();
			}

			statement += "\nTotal In All Accounts " + toDollars(total);
			return statement;
		} catch (UndefinedAccountTypeException e) {
			return "Please contact the bank for the details of yout accounts.";
		}

	}

	private String statementForAccount(Account a) throws UndefinedAccountTypeException {

		String accountType = accountTypeToString(a.getAccountType());
		String transactionsAndTotalAmount = getTransactionsAndTotalAmount(a);

		String statement = accountType + transactionsAndTotalAmount;
		return statement;

	}

	private String getTransactionsAndTotalAmount(Account a) {
		String transactions = "";
		for (Transaction t : a.getTransactions()) {
			if (t.getTransactionAmount() < 0) {
				transactions += " withdrawal" + " " + toDollars(Math.abs(t.getTransactionAmount())) + "\n";
			} else
				transactions += " deposit" + " " + toDollars(t.getTransactionAmount()) + "\n";
		}

		return transactions + "Total " + toDollars(a.getBalance());
	}

	private String accountTypeToString(int i) throws UndefinedAccountTypeException {
		String accountType;
		switch (i) {
		case Account.CHECKING:
			accountType = "Checking Account\n";
			break;
		case Account.SAVINGS:
			accountType = "Savings Account\n";
			break;
		case Account.MAXI_SAVINGS:
			accountType = "Maxi Savings Account\n";
			break;
		default:
			throw new UndefinedAccountTypeException();
		}
		return accountType;
	}

	private String toDollars(double d) {
		return String.format("$%,.2f", d);
	}

	public void transferFromTo(Account fromAccount, Account toAccount, double amount)
			throws NonExistentAccountException, IllegalArgumentException, NotEnoughBalanceException {

		if (accounts.contains(fromAccount) && accounts.contains(toAccount)) {
			fromAccount.withdraw(amount);
			toAccount.deposit(amount);
		} else {

			throw new NonExistentAccountException();
		}
	}
}
