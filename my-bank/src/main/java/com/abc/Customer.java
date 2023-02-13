package com.abc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.abc.Account.AccountType;

import static java.lang.Math.abs;

public class Customer {
	private String name;
	private List<Account> accounts;
	private Bank bank;

	private static final String LINE_BREAK = "\n";

	public Customer(String name) {
		this.name = name;
		this.accounts = new ArrayList<Account>();
	}

	public String getName() {
		return name;
	}

	public Account openAccount(AccountType accountType) {
		if (bank == null) {
			throw new IllegalArgumentException("Customer should be assigned to a bank first.");
		}
		Account account = bank.createAccount(this, accountType);
		accounts.add(account);
		return account;
	}

	public int getNumberOfAccounts() {
		return accounts.size();
	}

	public double totalInterestEarned() {
		double total = 0;
		for (Account a : accounts)
			total += a.interestEarned();
		return total;
	}

	static String statementForAccount(Account account) {
		StringBuilder sb = new StringBuilder(account.getAccountType().getName());
		sb.append(LINE_BREAK);
		for (Transaction t : account.getTransactions()) {
			if (t.getAmount() < 0) {
				sb.append("  withdrawal ");
			} else {
				sb.append("  deposit ");
			}
			sb.append(toDollars(t.getAmount()));
			sb.append(LINE_BREAK);
		}
		sb.append("Total ");
		sb.append(toDollars(account.sumTransactions()));
		return sb.toString();
	}

	private static double sumAllTransactions(Customer customer) {
		double total = 0.0;
		for (Account account : customer.getAccounts()) {
			total += account.sumTransactions();
		}
		return total;
	}

	private static String toDollars(double d) {
		return String.format("$%,.2f", abs(d));
	}

	public List<Account> getAccounts() {
		return Collections.unmodifiableList(accounts);
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}
}
