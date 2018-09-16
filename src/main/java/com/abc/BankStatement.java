package com.abc;

import java.text.MessageFormat;
import java.util.List;

/**
 * Creates a Summary report of all {@link Customer}s' {@link Account}s.
 */
public class BankStatement implements IReport {
	
	private static final String REPORT_TITLE = "Customer Summary";
	private static final String TEMPLATE_CUSTOMER_LINE_REPORT = "\n - {0} ({1} account{2})";
	private final List<Customer> customers;

	/**
	 * Constructs a new {@link BankStatement}.
	 * 
	 * @param customers
	 *            The {@link Customer}s of the {@link Bank}.
	 */
	public BankStatement(List<Customer> customers) {
		this.customers = customers;
	}

	public String build() {
		final StringBuilder bankStatementSummary = new StringBuilder();
		bankStatementSummary.append(REPORT_TITLE);
		for (Customer customer : customers) {
			final String customerName = customer.getName();
			final int accountsCount = customer.getNumberOfAccounts();
			final String emptyOrPlural = emptyOrPluralIfMany(accountsCount);
			final String customerSummary = 
					MessageFormat.format(TEMPLATE_CUSTOMER_LINE_REPORT, customerName, accountsCount, emptyOrPlural);
			bankStatementSummary.append(customerSummary);
		}
		return bankStatementSummary.toString();
	}

	private String emptyOrPluralIfMany(int number) {
		return number == 1 ? "" : "s";
	}
}
