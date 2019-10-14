package com.abc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class BankManager extends Employee {

	public BankManager(String name) {
		super(name);
	}

	// Gets a customer summary of the number of account held by each customer
	public String getCustomerSummary() {
		// Checks that the bank manager works at the bank
		if (getBank() != null) {
			String customerSummary = "CUSTOMER SUMMARY (" + getBank().getName().toUpperCase() + "):\n";

			// Appends the customer details to each line of the customer summary
			for (Map.Entry<Customer, List<Account>> customer : getBank().getCustomers().entrySet())
				customerSummary += "\n• " + customer.getKey().getName() + ": "
						+ addPlural(customer.getValue().size(), "Account");

			return customerSummary;
		}

		return notAnEmployee();
	}

	// Gets a summary of all the total interest paid by each account
	public String getTotalInterestPaid() {
		// Checks that the bank manager works at the bank
		if (getBank() != null) {
			String totalInterestPaid = "TOTAL INTEREST PAID: (" + getBank().getName().toUpperCase() + "):\n";

			BigDecimal total = BigDecimal.ZERO;
			// Get all the accounts from the bank
			for (Account account : getBank().getAccounts()) {
				totalInterestPaid += "\n• Account Number: " + account.getAccountNumber() + ", Account Type: "
						+ account.getAccountName() + "\n- Total Interest Paid: " + toDollars(account.getInterestPaid());

				total = total.add(account.getInterestPaid());
			}

			totalInterestPaid += "\n\n• Total Interest Paid On All Accounts: " + toDollars(total);

			return totalInterestPaid;
		}

		return notAnEmployee();
	}

	// Adds an 's' to the end of a string if it is a plural
	private String addPlural(int number, String word) {
		return number + " " + (number == 1 ? word : word + "s");
	}

}
