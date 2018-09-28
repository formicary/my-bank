package com.abc;

import static java.lang.Math.abs;

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

    public String printCustomerAccountsReport() {
        String report = "Customer Report";
        for (Customer c : customers)
            report += "\n - " + c.getCustomerSummary();
        return report;
    }
    
    public String printTotalInterestPaidReport(){
    	return "Total Interest Paid Report\n Total Interest Paid: " + toDollars(totalInterestPaid());
    }

    private double totalInterestPaid() {
        double total = 0;
        for(Customer c: customers)
            total += c.totalInterestEarned();
        return total;
    }

    public String getFirstCustomer() {
        try {
            return customers.get(0).getName();
        } catch (Exception e){
        	throw new NullPointerException();
        }
    }
    
    public static String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}
