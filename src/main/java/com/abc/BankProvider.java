package com.abc;

public interface BankProvider {

	public void addCustomer(Customer customer);
	public String customerSummary();
	public double totalInterestPaid();
	public String getFirstCustomer();
	
}