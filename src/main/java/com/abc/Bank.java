package com.abc;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Bank {
    public List<Customer> customers;

    public Bank() {
        customers = new ArrayList<Customer>();
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

    public double totalInterestPaid() {
        double total = 0;
        for(Customer c: customers)
            total += c.totalInterestEarned();
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
    
    
    private LocalDateTime localNow = LocalDateTime.now();
    private ZoneId currentZone = ZoneId.of("Greenwich");
    public ZonedDateTime zonedNow = ZonedDateTime.of(localNow, currentZone);
    public ZonedDateTime zonedNext1AM = zonedNow.withHour(1).withMinute(0).withSecond(0);
    public int secondsBeforeNextInterest = 24*60*60;
    
    
    public void dailyInterest() {
    	
        if(zonedNow.compareTo(zonedNext1AM) > 0)
        	zonedNext1AM = zonedNext1AM.plusDays(1);

        Duration duration = Duration.between(zonedNow, zonedNext1AM);
        long initalDelay = duration.getSeconds();

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);            
        scheduler.scheduleAtFixedRate(payDailyInterest, initalDelay, secondsBeforeNextInterest, TimeUnit.SECONDS);
        System.out.println("we get here");
    	
    		 
    	
    }
    
    Runnable payDailyInterest = () -> {
	    String threadName = Thread.currentThread().getName();
	    System.out.println("Hello " + threadName);
	    System.out.println("Count of customers: "+customers.size());
	    payInterest();
	};
    
    
    
    public void payInterest() {
    	
    	for (Customer c: customers) {
    		for (Account a: c.accounts) {
        		double interestEarned = a.interestEarned();
        		a.deposit(interestEarned);
        		System.out.println("Interest Paid: £" +interestEarned);
        		
        	}
    	}
    	
    	
    }
}
