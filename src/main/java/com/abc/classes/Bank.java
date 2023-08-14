package com.abc.classes;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private static List<Customer> customers;

    public Bank() {
        customers = new ArrayList<Customer>();
    }

    public Customer addCustomer(Customer customer) {
        customers.add(customer);
        return customer;
    }

    //Getters//
    public Bank getBank(){
        return this;
    }

    public List<Customer> getCustomers(){
        return customers;
    }

    public Customer getFirstCustomer() {
        try{
            if(customers.isEmpty()){
            throw new NullPointerException("No customers found in list");
            }
            else{
                return customers.get(0);
            }
        }
        catch(NullPointerException e){
            return null;
        }
        
    }

    public double getTotalInterestPaid() {
        double total = 0;
        for(Customer c: customers)
            total += c.getTotalInterestEarned();
        return total;
    }
    
    public String getCustomerSummary() {
        String summary = "Customer Summary";
        if(customers.size() == 0){
            summary += " No customers found";
            return summary;
        }
        else{
            for (Customer c : customers)
                summary += "\n - " + c.getName() + ": [" + c.getNumberOfAccounts() + " account(s)" +  " open]";
            return summary; 
        }
    }

    //Pay interest function, can be called at set intervals
    public void payInterestToAllAccounts(){
        //Loop though all customers
        for(Customer customer : customers){
            List<Account> listOfAccounts = customer.getAccounts();
            //For each account in cusomters, add interest
            for (Account account : listOfAccounts){
                account.addInterest();
            }
        }
    }
    
}


