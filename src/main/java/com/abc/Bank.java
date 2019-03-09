package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Bank implements IBank {
    private List<Customer> customers;

    public Bank() {
        customers = new ArrayList<Customer>();
    }
    
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }
    
    //Return a list of customers in bank along with their number of accounts. 
    public String customerSummary() {
        String summary = "Customer Summary";
        if (checkCustomerSize()){
        	summary += "\n - no customers";
        }
        else{
        	for (ICustomer c : customers)
                summary += "\n - " + c.getName() + " (" + checkplural(c.getNumberOfAccounts(), "account") + ")";
        }
        return summary;
        
    }
    
    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    private String checkplural(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }
    
    //Calculate the total interest paid to all customers
    public double totalInterestPaid() {
        double total = 0;
        if (!checkCustomerSize()){
        	for(ICustomer c: customers)
                total += c.sumInterestEarned();
        }
        return total;
    }
    
    //Get the first customer in bank
    public ICustomer getFirstCustomer() {
    	if (checkCustomerSize()){
    		throw new IllegalArgumentException("No customer exists");
    	}
    	else{
    		return customers.get(0);
    	}
    }
    
    //Checking that customers exist in banks
    private boolean checkCustomerSize() {
    	if (customers.isEmpty()){
    		return true;
    	}
    	else{
    		return false;
    	}
    }
    
}
