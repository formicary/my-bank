package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<Customer> customers;

    
    public Bank() {
        customers = new ArrayList<Customer>();
    }
    

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }


	public double totalInterestPaid(InterestCalculator calculator) {
		double total = 0;
		for (Customer c : customers)
			total += c.totalInterestEarned(calculator);
		return total;
	}

    //fixed errors in this method to return banks first customer if any customers exist.
	public String getFirstCustomer() {
		try {
			if (customersExist())
				return customers.get(0).getName();
			else
				return "Bank has no customers";

		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
			return "Error";
		}
	}
	
	
	public String customerSummary() {
		if(customersExist()) {
			StringBuilder summary = new StringBuilder("Customer Summary");
			for (Customer c : customers) {
			summary.append("\n - ")
				   .append(c.getName())
	               .append(" (")
	               .append(format(c.getNumberOfAccounts()))
	               .append(")");
			}
			return summary.toString();

		} else
			return "Bank has no customers!";
	}

	
	
	public boolean customersExist() {
		return !customers.isEmpty();
	}

	// Make sure correct plural of word is created based on the number passed in:
	// If number passed in is 1 just return the word otherwise add an 's' at the end
	private String format(int number) {
		String accountStr = "account";
		return number + " " + (number == 1 ? accountStr : accountStr + "s");
	}
}
