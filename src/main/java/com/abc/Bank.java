package com.abc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<Customer> customers;

	public Bank() {
        customers = new ArrayList<Customer>();
    }

    public String customerSummary() {
        String summary = "Customer Summary";
        for (Customer customer : customers) {
            summary += "\n - " + customer.getName() + " (" + customer.getNumberOfAccounts() + " account"; 
            if (customer.getNumberOfAccounts() > 1) {
            	summary += "s";
            }
            summary += ")";
        }
        return summary;
    }

    public BigDecimal totalInterestPaid() {
    	BigDecimal totalInterest = BigDecimal.ZERO;
        for (Customer customer: customers) {
        	totalInterest = totalInterest.add(customer.totalInterestAccrued());
        }
        return totalInterest;
    }

    public List<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}
	
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }
}
