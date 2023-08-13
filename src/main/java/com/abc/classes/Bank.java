package com.abc.classes;

import java.util.ArrayList;
import java.util.List;

import com.abc.classes.Account.AccountType;

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
            summary += "\n - No customers found";
            return summary;
        }
        else{
            for (Customer c : customers)
                summary += "\n - " + c.getName() + ": [" + c.getNumberOfAccounts() + " account(s)" +  " open]";
            return summary; 
        }
    }

    

    public static void main(String[] args) {
        Bank bank = new Bank();
        Customer customer = new Customer("Test Name");

        bank.addCustomer(customer);
        Customer bob = bank.addCustomer(new Customer("Bob"));
        Account newAccount = customer.openAccount(AccountType.CHECKING);
        Account newAccount1 = customer.openAccount(AccountType.SAVINGS);
        Account newAccount2 = customer.openAccount(AccountType.MAXI_SAVINGS);
        bob.openAccount(AccountType.CHECKING);
        newAccount.tryDeposit(5000);
        newAccount1.tryDeposit(1500.0);
        newAccount2.tryDeposit((3000.0));

        //newAccount.addInterest();
        //newAccount1.addInterest();
        newAccount2.addInterest();
        

        System.out.println("Total interest across acounts = " + bank.getTotalInterestPaid());
        System.out.println(bank.getCustomerSummary());

        System.out.println("First customer is :" + bank.getFirstCustomer());

    
    }

    
}


