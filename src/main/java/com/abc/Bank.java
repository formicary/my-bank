package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<Customer> customers;

    public Bank() {
        customers = new ArrayList<Customer>();
    }
    
    public int getNumberOfCustomers () {
    	return customers.size();
    }

    public void addCustomer(Customer customer) {
    	String customerName = customer.getName();
    	String regex = "[A-Z][a-z]+";
    	if (customerName != null && customerName.matches(regex) && customerName != "") {
    		customers.add(customer);
    	}
    	
    }

    public String customerSummary() {
        String summary = "Customer Summary";
            	if (customers.isEmpty())
            		summary = "There are currently no customers";
                else {
        	    	summary = "Customer Summary";
        	    	for (Customer c : customers)
        	            summary += "\n - " + c.getName() + " (" + format(c.getNumberOfAccounts(), "account") + ")";
                }
        return summary;
    }

    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    public double totalInterestPaid() {
        double total = 0;
        for(Customer c: customers)
            total += c.totalInterestEarned();
        return total;
    }
    
     
    public String getFirstCustomerName() {
        try {
            if (customers != null && customers.size() > 0) {
            	return customers.get(0).getName();
            }
            return null;
        } catch (Exception e){
            e.printStackTrace();
            return "Error";
        }
    }
    
    public Customer getFirstCustomer() {
        try {
            if (customers != null && customers.size() > 0) {
            	return customers.get(0);
            }
            return null;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public String getAccountDetails(double accountNumber){

		String result = "";
    	for(Customer c: customers){
    		List<Account> accounts = c.getAccounts();
    		for(Account acc: accounts){
    			if(acc.getAccountNumber() == accountNumber){
    				AccountType type = acc.getAccountType();
    				String name = c.getName();
    				return result = "Account Details: Name- " + name + " Account Type- " + type;    				
    			}
    			else {
    	    		return null;
    	    	}
    				
    		}
    	}
		return result;
    	
    }

	public List<Customer> getCustomerList() {
		// TODO Auto-generated method stub
		return customers;
	}
    
    
}
