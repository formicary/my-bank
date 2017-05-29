package com.abc.account;

public class MaxiSaving extends Account {


	public double interestEarned() {
		
		/*
		 * return amount*(1+0.05/365)math.exp 365;
		 * 
		 * 
		 * 
		 * 
		 * */
        double amount = sumTransactions();
        boolean withdrawTransaction = checkforWithdrawals(10,2);
        if (withdrawTransaction){
        	return amount*0.05;
        }else
        	return amount*0.001;
        
                     
        }
    }
