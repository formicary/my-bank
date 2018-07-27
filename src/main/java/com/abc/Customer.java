package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

import java.text.DecimalFormat;

public class Customer {
	private String firstName;
	private String lastName;
	private List<Account> accounts;
	
	public Customer(String firstName,String lastName) {
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.accounts = new ArrayList<Account>();
	}
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

	public Customer openAccount(Account newAccount) {
		for(Account a: accounts) {
			if (newAccount.getAccountType() == a.getAccountType()) {
				System.out.println("Already had a "+ newAccount.getAccountType()+" Account");
				return this;
			}
		}
		accounts.add(newAccount);
		System.out.println(newAccount.getAccountType()+" Account created");
		return this;
	}

	public int getNumberOfAccounts() {
		return accounts.size();
	}

	public double totalInterestEarned() {
		double total = 0;
		for (Account a : accounts)
			total += a.interestEarned();
		DecimalFormat df = new DecimalFormat("0.00");
		return Double.valueOf(df.format(total));
	}
	
	public double totalInterestEarnedDaily() {
		double total = 0;
		for (Account a : accounts)
			total += a.interestEarnedDaily();
		DecimalFormat df = new DecimalFormat("0.00");
		return Double.valueOf(df.format(total));
	}

	public String getStatement() {
		StringBuilder statement = new StringBuilder();

		statement.append(String.format("Statement for %s %s ", firstName,lastName));
		double total = 0.0;
		for (Account a : accounts) {
			statement.append("\n" + statementForAccount(a) + "\n") ;
			total += a.getBalance();
		}
		statement.append("\nTotal In All Accounts " + toDollars(total));
		return statement.toString();
	}

	private String statementForAccount(Account a) {
		StringBuilder s = new StringBuilder();

		// Translate to pretty account type
		switch (a.getAccountType()) {
		case CHECKING:
			s.append( "Checking Account\n");
			break;
		case SAVINGS:
			s.append("Savings Account\n");
			break;
		case MAXI_SAVINGS:
			s.append("Maxi Savings Account\n");
			break;
		}

        for (Transaction t : a.transactions) {
            s.append((t.getAmount() < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.getAmount()) + "\n");
        }
		s.append("Total " + toDollars(a.getBalance()));
		return s.toString();
	}

	private String toDollars(double d) {
		return String.format("$%,.2f", abs(d));
	}
}
