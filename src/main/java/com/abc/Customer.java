package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Customer {

	private String firstName;
	private String surname;
	private int ID;
	private List<Account> accounts;

	// Create a customer with an empty list of accounts.
	// The ID would be unique and incremented by the database.
	public Customer(String firstName, String surname, int ID) {
		this.firstName = firstName;
		this.surname = surname;
		this.ID = ID;
		this.accounts = new ArrayList<Account>();
	}

	// Create a customer with an existing list of accounts.
	public Customer(String firstName, String surname, int ID, List<Account> accounts) {
		this.firstName = firstName;
		this.surname = surname;
		this.ID = ID;
		this.accounts = accounts;
	}

	// Add an account to the current list of accounts of the current user.
	public void openAccount(String accountType) {
		try {
			switch (accountType) {
			case Account.CHECKING:
				accounts.add(new CheckingAccount());
				break;
			case Account.SAVINGS:
				accounts.add(new SavingsAccount());
				break;
			case Account.MAXI_SAVINGS:
				accounts.add(new MaxiSavingsAccount());
				break;
			default:
				throw new NullPointerException();
			}
		} catch (Exception e) {
			System.out.println("The account type provided doesn't exist.");
		}
	}

	// Transfer currency between accounts.
	public void interAccountsTransfer(Account fromAccount, Account toAccount, double amount) throws Exception {
		if (fromAccount.getBalance() >= amount) {
			fromAccount.withdraw(amount);
			toAccount.deposit(amount);
		} else {
			throw new Exception("Inssuficient funds for the transfer.");
		}
	}

	// Get the number of accounts of the current user.
	public int getNumberOfAccounts() {
		return accounts.size();
	}

	// Calculate and return the total interest earned by all the customer's
	// accounts.
	public double getCustomerTotalInterestEarned() {
		double total = 0.0;
		for (Account a : accounts) {
			total += a.getTotalInterestEarned();
		}
		return total;
	}

	// Returns a statement for the current customer.
	public String getStatement() {
		String statement = null;
		statement = "Statement for " + getFirstName() + " " + getSurname() + "\n";
		double total = 0.0;
		for (Account a : accounts) {
			statement += "\n" + getStatementForAccount(a) + "\n";
			total += a.getBalance();
		}
		statement += "\nTotal In All Accounts " + toDollars(total);
		return statement;
	}

	// Returns a statement of the specified account.
	private String getStatementForAccount(Account a) {
		String s = "";
		// Translate to pretty account type.
		try {
			switch (a.getAccountType()) {
			case Account.CHECKING:
				s += "Checking Account\n";
				break;
			case Account.SAVINGS:
				s += "Savings Account\n";
				break;
			case Account.MAXI_SAVINGS:
				s += "Maxi Savings Account\n";
				break;
			default:
				throw new NullPointerException();
			}
		} catch (Exception e) {
			System.out.println("The account provided has an inexistent type.");
		}
		// Total up all the transactions.
		double total = 0.0;
		for (Transaction t : a.getTransactions()) {
			s += "  " + (t.getAmount() < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.getAmount()) + "\n";
			total += t.getAmount();
		}
		s += "Total " + toDollars(total);
		return s;
	}
	
	//Format the currency to dollars.
	private String toDollars(double d) {
		return String.format("$%,.2f", abs(d));
	}

	// Get the customer's first name.
	public String getFirstName() {
		return firstName;
	}

	// Get the customer's surname.
	public String getSurname() {
		return surname;
	}

	// Get the customer's ID.
	public int getID() {
		return ID;
	}

	// Get the customer's list of accounts.
	public List<Account> getAccounts() {
		return accounts;
	}
}
