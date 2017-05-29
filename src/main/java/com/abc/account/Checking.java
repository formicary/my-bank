package com.abc.account;

public  class Checking extends Account {

	

	public double interestEarned() {
        double amount =getBalance();      
         return amount* 0.001;
        }
    }
	
	
	
	
