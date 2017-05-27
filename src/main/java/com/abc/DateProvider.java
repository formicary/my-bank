package com.abc;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DateProvider {
    private static DateProvider instance = null;

    public static DateProvider getInstance() {
        if (instance == null)
            instance = new DateProvider();
        return instance;
    }
    
    public static boolean tenDaysPassed(int days,List<Transaction> transactions)    {
    	for(Transaction t: transactions){
    		int transactionType = t.getTransactionType();
    		
    		
    		/* If transaction is withdraw
    		 * It includes transfer for one account to another because the customer withdraws money from the account
    		 * I understand the Maxi-Savings account as an account to save money and get highly rewarded. So whenever 
    		 * a customer withdraws money even to transfer it to another account, they do not save money in Maxi-Savings 
    		 * account and they are not rewarded with the high interest.*/
    		if(transactionType == 0) { 
    			Date now = DateProvider.getInstance().now();
    			Date transactionDate = t.getTransactionDate();
    			//get time difference in milliseconds
    			long diffInMillies = now.getTime() - transactionDate.getTime();
    			long diffInMinutes = TimeUnit.MINUTES.convert(diffInMillies,TimeUnit.MILLISECONDS);
                
    			//24 hours per day * 60 minutes per hour = 1440
    			if(diffInMinutes > 1440*days)
                	return true;
    		}
    	}
		return false;
	}

    public Date now() {
        return Calendar.getInstance().getTime();
    }
}
