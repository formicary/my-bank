package main;

import java.util.ArrayList;
import java.util.List;

import helper.AccountTypes;
import helper.Transaction;

import static java.lang.Math.abs;

public class Customer implements Comparable<Customer> {

	private int customerID;
	private String name;
	private List<Account> accounts;

	public Customer(int customerID, String name) {
		this.setCustomerID(customerID);
		this.name = name;
		this.accounts = new ArrayList<Account>();
	}

	public String getName() {
		return name;
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
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
		String statement = "Statement for " + name + "\n";
		double total = 0.0;
		for (Account a : accounts) {
			statement += "\n" + statementForAccount(a) + "\n";
			total += a.sumTransactions();
		}
		statement += "\nTotal In All Accounts " + toDollars(total);
		return statement;
	}

	private String statementForAccount(Account a) {
		String s = "";

		// Translate to pretty account type
		switch (a.getAccountType()) {
		case AccountTypes.CHECKING:
			s += "Checking Account\n";
			break;
		case AccountTypes.SAVINGS:
			s += "Savings Account\n";
			break;
		case AccountTypes.MAXI_SAVINGS:
			s += "Maxi Savings Account\n";
			break;
		}

		// Now total up all the transactions
		double total = 0.0;
		for (Transaction t : a.transactions) {
			s += "  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount) + "\n";
			total += t.amount;
		}
		s += "Total " + toDollars(total);
		return s;
	}

	private String toDollars(double d) {
		return String.format("$%,.2f", abs(d));
	}

	// Custom method which allows the custom to transfer money from one account to
	// another account.
	public void transferMoneyToOtherAcc(double amount, int firstAccount, int secondAccount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else if (firstAccount >= 0 && firstAccount < accounts.size() && firstAccount >= 0
				&& firstAccount < accounts.size() && firstAccount != secondAccount) {

			Account first = accounts.get(firstAccount);
			Account second = accounts.get(secondAccount);

			first.withdraw(amount);
			second.deposit(amount);

		}
	}

	// Checks if two customers are equal.
	@Override
	public boolean equals(Object obj) {
		if (obj == null || this.getClass() != obj.getClass())
			return false;

		Customer other = (Customer) obj;

		return this.customerID == (other.customerID) && this.name.equals(other.name);
	}

	// Allows two customers to be compared with each other.
	@Override
	public int compareTo(Customer other) {
		int result = this.name.compareTo(other.name);

		if (result == 0) {
			result = Integer.compare(this.customerID, other.customerID);
		}

		return result;
	}

	// Overriding the default toString representation of the Customer class.
	@Override
	public String toString() {
		return "Customer [customerID=" + customerID + ", name=" + name + ", accounts=" + accounts + "]";
	}

}
