package main.java.com.abc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    public List<Transaction> transactions;
    
    private boolean maxiRate;
    private Timer maxiRateTimer;
    
    private Timer interestTimer;

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        
        interestTimer = new Timer();
        
        interestTimer.scheduleAtFixedRate(new TimerTask() {
        	@Override
        	public void run() {
        		accrueDailyInterest();
        	}
        }, DateProvider.getInstance().now(), 86400000);
        
        if (accountType == MAXI_SAVINGS) {
        	maxiRate = true;
        }
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

	public void withdraw(double amount) {
	    if (amount <= 0) {
	        throw new IllegalArgumentException("amount must be greater than zero");
	    } else {
	        transactions.add(new Transaction(-amount));
	        if (accountType == MAXI_SAVINGS) {
	        	this.maxiRate = false;
	        	
	        	maxiRateTimer = new Timer();
	        	
	        	Date futureDate = getDate();
	        	
	        	maxiRateTimer.schedule(new TimerTask(){
	        		@Override
	        		public void run() {
	        			maxiRate = true;
	        			this.cancel();
	        		}
	        	}, futureDate );
	        }
	    }
	}
	
	private Date getDate() {
	Calendar c = Calendar.getInstance();
	c.setTime(new Date());
	c.add(Calendar.DATE, 10);

	return c.getTime();
	}

    public double interestEarned() {
        double amount = sumTransactions();
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001;
                else
                    return 1 + (amount-1000) * 0.002;
//            case SUPER_SAVINGS:
//                if (amount <= 4000)
//                    return 20;
            case MAXI_SAVINGS:
                if (maxiRate)
                    return (amount) * 0.05;
                else
                	return (amount) * 0.001;
            default:
                return amount * 0.001;
        }
    }
    
    private void accrueDailyInterest() {
    	double amount = sumTransactions();
    	switch(accountType) {
    		case SAVINGS:
    			if (amount <= 1000)
    				deposit(amount * (0.001/365));
    			else
    				deposit((amount - 1000) * (0.002/365));
    		case MAXI_SAVINGS:
    			if (maxiRate)
    				deposit(amount * (0.05/365));
    			else
    				deposit(amount * (0.001/365));
    		case CHECKING:
    			deposit(amount * (0.001/365));
    	}
    }

    public double sumTransactions() {
       return checkIfTransactionsExist(true);
    }

    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }

    public int getAccountType() {
        return accountType;
    }

}
