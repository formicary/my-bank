package com.abc;

import java.math.BigDecimal;
import java.util.List;

public class Teller extends Employee {

	// Constant values to define the different account types
	private final int checkingAccount = 0;
	private final int savingsAccount = 1;
	private final int maxiSavingsAccount = 2;

	public Teller(String name) {
		super(name);
	}

	// Open an account with the bank
	public String openAccount(Customer customer, int accountType) {
		// Check that the teller is an employee of the bank
		if (getBank() != null) {
			Account newAccount = null;
			int accountNumber = getBank().getAccounts().size();

			// Create the correct account type
			switch (accountType) {
			case checkingAccount:
				newAccount = new CheckingAccount(accountNumber);
				break;
			case savingsAccount:
				newAccount = new SavingsAccount(accountNumber);
				break;
			case maxiSavingsAccount:
				newAccount = new MaxiSavingsAccount(accountNumber);
				break;
			default:
				return "Account Type: " + accountType + " does not exist.";
			}

			// If the customer is new to the bank, add them
			if (!getBank().isCustomer(customer)) {
				getBank().addCustomer(customer, newAccount);
			} else {
				getBank().addAccount(customer, newAccount);
			}

			return "Account Number: " + newAccount.getAccountNumber() + " opened successfully.";
		}

		return notAnEmployee();
	}

	// Deposit money into an account
	public String deposit(BigDecimal amount, int accountNumber) {
		// Make sure a positive value is being deposited
		if (amount.compareTo(new BigDecimal(0)) <= 0)
			return "Deposit amount must be a positive value.";

		// Check if employee is working at the bank
		if (getBank() != null) {
			Account depositAccount = getBank().getAccount(accountNumber);

			// Check if the account number exists
			if (depositAccount != null) {
				depositAccount.deposit(amount);

				return toDollars(amount) + " deposited into Account Number: " + accountNumber + ".";
			}

			return "Account Number: " + accountNumber + " does not exist.";
		}

		return notAnEmployee();
	}

	// A customer can withdraw money from their bank account
	public String withdraw(BigDecimal amount, Customer customer, int accountNumber) {
		// Make sure that a positive value is being withdrawn
		if (amount.compareTo(new BigDecimal(0)) <= 0)
			return "Withdrawal amount must be positive.";

		if (getBank() != null) {
			List<Account> customerAccounts = getBank().getAccounts(customer);

			// Check that the customer has the specified account and withdraw the funds
			if (customerAccounts != null) {
				for (Account account : customerAccounts)
					if (account.getAccountNumber() == accountNumber) {
						// Transfer funds
						if (!account.withdraw(amount))
							return "Account Number: " + account.getAccountNumber() + " has insufficient funds.";

						return toDollars(amount) + " withdrawn from Account Number: " + accountNumber + ".";
					}

				return customer.getName() + " does not own the Account Number: " + accountNumber + ".";
			}

			return customer.getName() + " does not have an account with " + getBank().getName() + ".";
		}

		return notAnEmployee();
	}

	// Transfer money from one bank account to another
	public String transfer(BigDecimal amount, Customer customer, int transferorAccountNumber,
			int transfereeAccountNumber) {
		// Make sure that a positive value is being withdrawn
		if (amount.compareTo(BigDecimal.ZERO) <= 0)
			return "Withdrawal amount must be positive.";

		// Account number cannot be the same
		if (transferorAccountNumber == transfereeAccountNumber)
			return "The Transferor (" + transferorAccountNumber + ") and Transferee (" + transfereeAccountNumber
					+ ") account numbers must be different.";

		if (getBank() != null) {
			// Get the accounts belonging to the customer
			List<Account> customerAccounts = getBank().getAccounts(customer);

			// Attempt to find the transferor and transferee accounts for the given customer
			for (Account transferorAcc : customerAccounts) {
				if (transferorAcc.getAccountNumber() == transferorAccountNumber) {
					for (Account transfereeAcc : customerAccounts) {
						// Both accounts have been found and belong to the customer
						if (transfereeAcc.getAccountNumber() == transfereeAccountNumber) {
							// Transfer funds
							if (!transferorAcc.withdraw(amount))
								return "Account: " + transferorAccountNumber + " has insufficient funds.";

							transfereeAcc.deposit(amount);

							return toDollars(amount) + " has been transferred from Account: " + transferorAccountNumber
									+ " to Account: " + transfereeAccountNumber + ".";
						}
					}
				}
			}

			return "Transferor Account: " + transferorAccountNumber + " or Transferee Account: "
					+ transfereeAccountNumber + " does not belong to " + customer.getName() + ".";
		}

		return notAnEmployee();
	}

	// Get a bank statement for the customer
	public String getStatement(Customer customer) {
		if (this.getBank() != null) {
			String statement = "STATEMENT (" + customer.getName().toUpperCase() + "):\n";

			// Get details about each account and append it to the statement string
			BigDecimal total = BigDecimal.ZERO;
			for (Account account : getBank().getAccounts(customer)) {
				statement += "\n• Account Number: " + account.getAccountNumber() + ", Account Type: "
						+ account.getAccountName() + getTransactions(account) + "\n- Balance: "
						+ toDollars(account.getBalance()) + "\n";

				total = total.add(account.getBalance());
			}

			statement += "\n• Total Balance: " + toDollars(total);

			return statement;
		}

		return notAnEmployee();
	}

	// Return the transactions made by the account
	private String getTransactions(Account account) {
		String transactionStatement = "";

		for (Transaction transaction : account.getTransactions())
			transactionStatement += "\n - "
					+ (transaction.getAmount().compareTo(BigDecimal.ZERO) < 0 ? "Withdrawal:" : "Deposit:") + " "
					+ toDollars(transaction.getAmount()) + ", Date: " + transaction.getTransactionDate();

		return transactionStatement;
	}

}
