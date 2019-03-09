package com.abc;

public interface IBank {

	void addCustomer(Customer customer);

	//Return a list of customers in bank along with their number of accounts. 
	String customerSummary();

	//Calculate the total interest paid to all customers
	double totalInterestPaid();

	//Get the first customer in bank
	ICustomer getFirstCustomer();

}