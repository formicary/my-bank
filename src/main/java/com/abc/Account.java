package com.abc;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public abstract class Account {
	
    public static final BigDecimal LOWER_LIMIT = new BigDecimal(1000);
    public static final BigDecimal UPPER_LIMIT = new BigDecimal(2000.0);
    
    public static final double ONE_TENTH_PERCENT = 0.001;
    public static final double TWO_TENTH_PERCENT = 0.002;
    public static final double FIVE_PERCENT = 0.05;  
    
    public static final int WITHDRAWALS_DAY_GAP = 10;
    public static final int YEAR = 365;
    
    public String accountType;
    public List<Transaction> transactions;

    public Date newestWithdrawal;
    public Date lastWithdrawal;
    
    public BigDecimal balance = new BigDecimal(0.0).setScale(2, BigDecimal.ROUND_HALF_UP);
    
    public Account() {
        
        this.transactions = new ArrayList<Transaction>();   
    }
    
    public abstract BigDecimal interestEarned();
    
    public abstract BigDecimal compoundDailyInterest();
    
    public synchronized void withdraw(double amount) {
		
    	if (amount <= 0) {
    		
			throw new IllegalArgumentException("amount must be greater than zero");
		} else {
			
			transactions.add(new Transaction(-amount));
			BigDecimal withdrawal = new BigDecimal(amount);
			balance = balance.subtract(withdrawal);
					
			//first withdraw
			if (newestWithdrawal == null) {
				newestWithdrawal = DateProvider.now();
				lastWithdrawal = newestWithdrawal;
			}
			else {
				lastWithdrawal = newestWithdrawal;
				newestWithdrawal = DateProvider.now();
			}	
		}
	}

    public synchronized void deposit(double amount) {
        
    	if (amount <= 0) {
    		
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
        	
            transactions.add(new Transaction(amount));  
            BigDecimal deposit = new BigDecimal(amount);
			balance = balance.add(deposit);	
        }
    }
      
    public int getWithdrawalGap() {

    	int days = 0;
    	long diff = 0;
    	
    	//no withdraws
    	if (newestWithdrawal == null) {
    		
    		//if no transaction recorded yet, act same as no transaction past 10 days
    		days = WITHDRAWALS_DAY_GAP + 1;
    	}else {
    		
    		diff = newestWithdrawal.getTime() - lastWithdrawal.getTime();
    		days = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    	}
    	
    	return days;
    }

    public String getAccountType() {
    	
        return accountType;
    }
    
    public synchronized BigDecimal getBalance() {
    	
    	return balance;
    }
    
    public void setNewestWithdrawal(Date d) {
    	
    	this.newestWithdrawal = d;
    }
    
    public void setLastWithdrawal(Date d) {
    	
    	this.lastWithdrawal = d;
    }
}
