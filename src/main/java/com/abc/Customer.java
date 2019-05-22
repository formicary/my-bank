package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Customer {
	private String name;
	private List<Account> accounts;

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

	public double totalInterestEarned() {
		double total = 0;
		for (Account a : accounts)
			total += a.interestEarned();
		return total;
	}

	public String getStatement() {
		StringBuilder bld = new StringBuilder();
		bld.append("Statement for " + name + "\n");
		double total = 0.0;
		for (Account a : accounts) {
			bld.append("\n" + statementForAccount(a) + "\n");
			total += a.sumTransactions();
		}
		bld.append("\nTotal In All Accounts " + toDollars(total));
		return bld.toString();
	}

	private String statementForAccount(Account a) {
		StringBuilder bld = new StringBuilder();

		switch (a.getAccountType()) {
		case Account.CHECKING:
			bld.append("Checking Account\n");
			break;
		case Account.SAVINGS:
			bld.append("Savings Account\n");
			break;
		case Account.MAXI_SAVINGS:
			bld.append("Maxi Savings Account\n");
			break;
		default:
			bld.append("Account not found");
		}

		double total = 0.0;
		for (Transaction t : a.getTransactions()) {
			bld.append("  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount) + "\n");
			total += t.amount;
		}
		bld.append("Total " + toDollars(total));
		return bld.toString();
	}

	private String toDollars(double d) {
		return String.format("$%,.2f", abs(d));
	}
	
    public String transferBetweenAccounts(Account accountFrom, Account accountTo, Double amount){
        if(accounts.contains(accountFrom) && accounts.contains(accountTo)){
            accountFrom.withdraw(amount);
            accountTo.deposit(amount);
            return "accounts updated";
        }else{
            throw new IllegalArgumentException("you can only transfer between your accounts");
        }
    }
}
