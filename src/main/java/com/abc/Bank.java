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

    public String customerSummary() {
    	StringBuilder builder=new StringBuilder();
        String summary = "Customer Summary";
        for (Customer customer : customers) {
        	//string builders are a bit more efficient in for loops
        	builder.append("\n - ");
        	builder.append(customer.getName());
        	builder.append(" (");
        	builder.append(format(customer.getNumberOfAccounts(), "account"));
        	builder.append(")");
        }
        return summary+builder.toString();
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

    public String getFirstCustomer() {
        try {
        	//removed null as that crashed a test
            return customers.get(0).getName();
        } catch (Exception e){
            e.printStackTrace();
            return "Error";
        }
    }
}
