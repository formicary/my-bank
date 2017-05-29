package com.abc;

import java.util.ArrayList;
import java.util.List;
/*
 * Banking class has a list of customers.
 * it allows to access all customers summary and amount of interest pain to customers
 * */
public class Bank {
    private List<Customer> customers;

    
    public Bank() {
        customers = new ArrayList<Customer>();
    }
/*addCustomer() to add new customers */
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

 /*customerSummary() provides a formatted summary of customers with their names and number of accounts open*/   
    public String customerSummary() {
    	StringBuilder summary= new StringBuilder();
         summary.append("Customer Summary") ;
         StringBuilder account= new StringBuilder();
         account.append("account");

        for (Customer c : customers)
            summary .append("\n - " ).append(c.getName() ).append( " (").append(format(c.getNumberOfAccounts(), account)+ ")");
        return summary.toString();
    }

    /*Make sure correct plural of word is created based on the number passed in:
    If number passed in is 1 just return the word otherwise add an 's' at the end*/
    private String format(int number, StringBuilder word) {
    	 
        return number + " " + (number == 1 ? word : word.append("s") );
    }
/*
 * totalInterestPaid() gives the total amount paid to all customers by the bank from all accounts
 * */
    public double totalInterestPaid() {
        double total = 0;
        for(Customer c: customers)
        	
            total += c.totalInterestEarned();
        
        return total;
    }

  
}
