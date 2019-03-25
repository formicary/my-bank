package com.abc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Bank {
	private List<Customer> customerList;

	public Bank() {
		setCustomerList(new ArrayList<Customer>());
	}

	public void createCustomer(String customerName) {
		getCustomerList().add(new Customer(customerName));
	}

	public String customerSummaries() {
		if (getCustomerList().isEmpty()) {
			System.out.println(Constants.NO_CUSTOMERS_WITH_BANK);
			return Constants.NO_CUSTOMERS_WITH_BANK;
		} else { 
			String customerSummaries = Constants.EMPTY_STRING;
			for (Customer c : getCustomerList()) {
				System.out.println(Constants.NAME + c.getName() + Constants.NUMBER_OF_ACCOUNTS + c.getNumberOfAccounts() + Constants.NEW_LINE);
				customerSummaries += Constants.NAME + c.getName() + Constants.NUMBER_OF_ACCOUNTS + c.getNumberOfAccounts() + Constants.NEW_LINE;
			}	
			return customerSummaries;
		}
	}
;
	public String totalInterestPaid() {
		BigDecimal totalInterestPaid = Constants.ZERO_BD;
		for (Customer c : getCustomerList()) {
			totalInterestPaid = totalInterestPaid.add(c.totalInterestEarned());
		}
		return Constants.TOTAL_INTEREST_PAID + totalInterestPaid.setScale(2, RoundingMode.HALF_UP);
	}

	public List<Customer> getCustomerList() {
		return customerList;
	}

	public void setCustomerList(List<Customer> customerList) {
		this.customerList = customerList;
	}
}

