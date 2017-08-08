package com.abc;
import java.util.HashMap; 
import java.util.ArrayList;
import java.util.List;

public class Bank {
    // UPDATE: Customers need  to have a unique identifier, using HashMap to enable key and value pair
    // Assign ID for each customer and HashMap provides function to check if a key already exsit or not
    // private List<Customer> customers;
    private HashMap<String,Customer> customers;
    public InterestProvider interestProvider; 
    public Bank() {
        customers = new HashMap<String,Customer>();
        interestProvider = new InterestProvider();
    }

    public void addCustomer(Customer customer) {
        customer.updateInterestRate(this.interestProvider);
        // UPDATE: Check for duplicate customer name 
        // UPDATE: Repalce ArrayList with HashMap instead.
        if(this.customers.containsKey(customer.getName())){
            // NOTE: Current using customer name as identifier for each customer
            // TO-DO: Assign Customer with unique ID as identifier 
            throw new IllegalArgumentException("Duplicate customer name ");
        } else {
            customers.put(customer.getName(),customer);
        }   

    }

    public String customerSummary() {
        String summary = "Customer Summary";
        
        for (Customer c : this.customers.values())
            summary += "\n - " + c.getName() + " (" + format(c.getNumberOfAccounts(), "account") + ")";
        return summary;
    }

    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    public double totalInterestPaid() {
        double total = 0;
        for(Customer c : this.customers.values())
            total += c.totalInterestEarned();
        return total;
    }

    // REMOVED: Not used
    // public String getFirstCustomer() {
    //     try {
    //         customers = null;
    //         return customers.get(0).getName();
    //     } catch (Exception e){
    //         e.printStackTrace();
    //         return "Error";
    //     }
    // }

    // UPDATE: change interest rate to a new InteresProvider
    // NOTE: Ignore the possibility of accounts failed to update thier InterestProvider
    public void changeInterestRate( InterestProvider newInterestRate ){
        for(Customer c : this.customers.values()){
            c.updateInterestRate(newInterestRate);
        }
    }
}
