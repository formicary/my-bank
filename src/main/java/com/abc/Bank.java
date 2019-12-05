package com.abc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Bank {
    private List<Customer> customers;
    private double lastInterestPaid;

    public Bank() {
        customers = new ArrayList<Customer>();
        // scheduleMidnightInterests();
    }
    
    // returns the latest interest gain
    public double getLastInterestPaid() { return lastInterestPaid; }

    
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

    // updates balances for all customers'accounts to include new interest gains
    public void payInterests() {
        double total = 0;
        for(Customer c: customers)
            total += c.earnDailyInterest();
        lastInterestPaid = total;
    }
    
    // returns the amount of money the bank spent on interests altogether
    public double getTotalInterestPaid() {
    	double total = 0;
        for(Customer c: customers)
            total += c.getTotalInterestEarned();
        return total;
    }

    public String getFirstCustomer() {
        if (customers.isEmpty()) throw new IllegalStateException();
        return customers.get(0).getName();
    }
    
    
    // schedule interest updates to happen midnight every day
    public void scheduleMidnightInterests() {
	  Calendar calendar = Calendar.getInstance();
	  calendar.set(Calendar.HOUR, 0);
	  calendar.set(Calendar.MINUTE, 0);
	  calendar.set(Calendar.SECOND, 0);	
	  scheduleInterests(calendar);
    }
    
    // schedule interest updates
    public void scheduleInterests(Calendar calendar) {
	      Timer timer = new Timer();
	      Date time = calendar.getTime();
	  
	      timer.schedule(new TimerTask() {
	    	  public void run() {
	              Bank.this.payInterests();
	           }
	      }, time);    
    }
   
}
