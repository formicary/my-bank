package com.mybank.entities;

import java.util.ArrayList;
import java.util.List;

import com.mybank.exceptions.UndefinedAccountTypeException;

public class Bank {
	private List<Customer> customers;

	public Bank() {
		customers = new ArrayList<Customer>();
	}

	public void addCustomer(Customer customer) {
		customers.add(customer);
	}

	public List<Customer> getCustomers(){
		return customers;
	}
	
	public String customersSummary() {
		String summary = "Customer Summary";
		for (Customer c : customers)
			summary += "\n - " + c.getName() + " (" + formatWord("account", c.getNumberOfAccounts()) + ")";
		return summary;
	}

	private String formatWord(String word, int number) {
		return number + " " + (number == 1 ? word : word + "s");
	}

	public double totalInterestPaid() {
		try{
		double total = 0;
		for (Customer c : customers)
			total += c.totalInterestEarned();
		return total;
		}catch(UndefinedAccountTypeException e){
			return -1;
		}
	}
	
}
