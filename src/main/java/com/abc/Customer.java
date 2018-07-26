package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Customer {
	private String firstName;
	private String lastName;
	private List<Account> accounts;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getFullName() {
		return firstName +" " + lastName;
	}

	

	public Customer(String firstName,String lasttName) {
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.accounts = new ArrayList<Account>();
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
		String statement = null;
		statement = String.format("Statement for %s %s /n", firstName,lastName);
		double total = 0.0;
		for (Account a : accounts) {
			statement += "\n" + statementForAccount(a) + "\n";
			total += a.getBalance();
		}
		statement += "\nTotal In All Accounts " + toDollars(total);
		return statement;
	}

	private String statementForAccount(Account a) {
		String s = "";

		// Translate to pretty account type
		switch (a.getAccountType()) {
		case CHECKING:
			s += "Checking Account\n";
			break;
		case SAVINGS:
			s += "Savings Account\n";
			break;
		case MAXI_SAVINGS:
			s += "Maxi Savings Account\n";
			break;
		}

        for (Transaction t : a.transactions) {
            s += "  " + (t.getAmount() < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.getAmount()) + "\n";
        }
		s += "Total " + toDollars(a.getBalance());
		return s;
	}

	private String toDollars(double d) {
		return String.format("$%,.2f", abs(d));
	}
}
