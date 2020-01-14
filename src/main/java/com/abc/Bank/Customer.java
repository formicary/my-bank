package com.abc.Bank;

import java.util.ArrayList;
import java.util.List;

import com.abc.Account.Account;

public class Customer {
	private final String name;
	private final List<Account> accounts;

	public Customer(String name) {
		this.name = name;
		this.accounts = new ArrayList<>();
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

	public double totalInterestEarned() {
		double total = 0;
		for (Account a : accounts)
			total += a.earnedInterest();
		return total;
	}

	public String getStatement() {
		StringBuilder s = new StringBuilder("Statement for " + name + "\n");
		double total = 0.0;
		for (Account account : accounts) {
			s.append("\n").append(account.accountStatement()).append("\n");
			total += account.getBalance();
		}
		s.append("\nTotal In All Accounts ").append(Transaction.toDollars(total));
		return s.toString();
	}

	// A customer can only transfer between accounts they own.
	public void transfer(Account sender, Account receiver, double transferAmount) {

		if (accounts.contains(sender) == false) {
			throw new IllegalArgumentException("Sorry, the sender account doesn't belong to you.");
		} else if (accounts.contains(receiver) == false) {
			throw new IllegalArgumentException("Sorry, the receiver account doesn't belong to you.");
		} else {
			sender.withdraw(transferAmount);
			receiver.deposit(transferAmount);
		}
	}

}
