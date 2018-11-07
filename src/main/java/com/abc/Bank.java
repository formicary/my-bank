package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Bank {
    private List<Customer> customers;

    public Bank() {
        customers = new ArrayList<Customer>();
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    @Override
    public String toString() {
        StringBuilder summary = new StringBuilder("Customer Summary");
        for (Customer c : customers)
            summary.append("\n - ").append(c.getName()).append(" (").append(format(c.getNumberOfAccounts(), "account")).append(")");
        return summary.toString();
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
            return customers.get(0).getName();
        } catch (NullPointerException e){
            e.printStackTrace();
            return "Error";
        }
    }

    public void payInterestToAllCustomers(){
        for(Customer c : customers){
            c.accrueInterestForAllAccounts();
        }
    }

    public double getTotalBalance(){
        double total = 0.0;
        for(Customer c: customers){
            total+=c.getTotalBalance();
        }
        return total;
    }

    public Customer getCustomer(int index){
        return customers.get(index);
    }

    public int getTotalCustomers(){ return customers.size();}

    public static String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}
