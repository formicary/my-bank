package com.abc;

import jdk.nashorn.internal.codegen.CompilerConstants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.math.BigDecimal;

public class Bank {
    private List<Customer> customers;

    private Calendar now;

    public Bank() {
        this.customers = new ArrayList<Customer>();
        this.now = Calendar.getInstance();
        this.now.setTimeInMillis(System.currentTimeMillis());
    }

    public Bank(Calendar date){
        customers = new ArrayList<Customer>();
        now = date;
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public String customerSummary() {
        String summary = "Customer Summary";
        for (Customer c : customers)
            summary += "\n - " + c.getName() + " (" + format(c.getNumberOfAccounts(), "account") + ")";
        return summary;
    }

    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    public BigDecimal totalInterestPaid(boolean realtime) {
        BigDecimal total = BigDecimal.ZERO;
        for(Customer c: customers)
            total = total.add(c.totalInterestEarned(realtime));
        return total;
    }

    public String getFirstCustomer() {
        try {
            customers = null;
            return customers.get(0).getName();
        } catch (Exception e){
            e.printStackTrace();
            return "Error";
        }
    }

    public void setDate(Calendar date){
        this.now = date;
        for (Customer c: customers){
            c.setDate(date);
        }
    }
}
