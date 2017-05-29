package com.abc.account;

public class MaxiSaving extends Account {


	public double interestEarned() {
		
		
        double amount = getBalance();
        // 10 is the number of days without withdrawal
        boolean NowithdrawTransaction = checkforWithdrawals(10);
        if (NowithdrawTransaction){
        	return amount*0.05;
        }else
        	return amount*0.001;
        
                     
        }
    }
