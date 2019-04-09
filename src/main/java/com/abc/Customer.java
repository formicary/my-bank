package com.abc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.abc.account.Account;
import com.abc.account.CurrencyUtil;

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

	public double totalInterestEarned() {
		return accounts.stream().map(Account::interestEarned).collect(Collectors.summingDouble(Double::doubleValue));
	}

	public String getStatement() {
		StringBuilder sb = new StringBuilder(String.format("Statement for %s\n", name));
		accounts.forEach(a -> sb.append(String.format("\n%s\n", a.getStatement())));
		sb.append(String.format("\nTotal In All Accounts %s", CurrencyUtil.toDollars(sumAllTransactions())));
		return sb.toString();
	}

	public Double sumAllTransactions() {
		return accounts.stream().map(Account::sumTransactions).collect(Collectors.summingDouble(Double::doubleValue));
	}

	public String getSummary() {
		return String.format("%s (%s)", getName(), StringUtil.pularize(getNumberOfAccounts(), "account"));
	}

	synchronized public void selfTransfer(Account src, Account dest, Double amount) {
		if (!accounts.contains(src)) {
			throw new IllegalArgumentException("Source account must belong to the customer!");
		}

		if (!accounts.contains(dest)) {
			throw new IllegalArgumentException("Destination account must belong to the customer!");
		}

		if (amount <= 0) {
			throw new IllegalArgumentException("Amount must be greater than zero!");
		}

		if (src.sumTransactions() < amount) {
			throw new IllegalArgumentException("Insufficient money on the source account to perform that transaction!");
		}

		src.withdraw(amount);
		dest.deposit(amount);
	}
}
