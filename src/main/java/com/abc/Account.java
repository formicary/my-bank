package main.java.com.abc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;
    public static final String[] ACCOUNT_NAMES = {"Checking", "Savings", "Maxi Savings"};
    public static final double DAYS = 365;
    
    private final int accountType;
    public List<Transaction> transactions;
    ////////
    private double accountBalance;
    private String accountStatement;
    private double interestEarned;
    
    private Date lastWithdrawal; 
    private Date localDate;

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        this.accountBalance = 0;
        this.accountStatement = ACCOUNT_NAMES[accountType] + " Account\n";
        this.lastWithdrawal = DateProvider.getInstance().now();
        this.interestEarned = 0;
    }
    
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
            accountBalance+=amount;
            accountStatement += "  deposit " + Customer.toDollars(amount) + "\n";
        }
    }

	public void withdraw(double amount) {
	    if (amount <= 0) {
	        throw new IllegalArgumentException("amount must be greater than zero");
	    } else {
	        transactions.add(new Transaction(-amount));
	        accountBalance-=amount;
	        accountStatement += "  withdrawal " + Customer.toDollars(amount) + "\n";
	    }
	    lastWithdrawal = DateProvider.getInstance().now();
	}
	
	public void withdraw(double amount, int numberOfDaysAgo) {
	    if (amount <= 0) {
	        throw new IllegalArgumentException("amount must be greater than zero");
	    } else {
	        transactions.add(new Transaction(-amount));
	        accountBalance-=amount;
	        accountStatement += "  withdrawal " + Customer.toDollars(amount) + "\n";
	    }
	    Date today = DateProvider.getInstance().now();
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(today);
	    cal.add(Calendar.DATE, -numberOfDaysAgo);
	    lastWithdrawal = cal.getTime();
	}
	
//		Before Compound Interest
//    public double interestEarned() {
//        double amount = sumTransactions();
//        switch(accountType){
//            case SAVINGS:
//                if (amount <= 1000)
//                    return amount * 0.001;
//                else
//                    return 1 + (amount-1000) * 0.002;
////            case SUPER_SAVINGS:
////                if (amount <= 4000)
////                    return 20;
//            case MAXI_SAVINGS:
////                if (amount <= 1000)
////                    return amount * 0.02;
////                if (amount <= 2000)
////                    return 20 + (amount-1000) * 0.05;
////                return 70 + (amount-2000) * 0.1;
//            	if (lessThanTimePeriod()) {
//            		return amount * 0.05;
//            	} else {
//            		return amount * 0.001;
//            	}
//            default:
//                return amount * 0.001;
//        }
//    }
	
	public double getInterestEarned() {
		return interestEarned;
	}
	
	public double interestEarned(int numberOfDays) throws ParseException {
		localDate = DateProvider.getInstance().now();
		double interest=0;
		for (int i=0; i<numberOfDays; i++) {
			interest+= interestEarned();
			//Simulate adding a day to local date for interest
			Calendar c = Calendar.getInstance(); 
			c.setTime(localDate); 
			c.add(Calendar.DATE, 1);
			localDate = c.getTime();
		}
		interestEarned += interest;
		return interestEarned;
	}
    
    public double interestEarned() {
    	
        double amount = sumTransactions();
        double earned = 0;
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000)
                    earned = amount * (0.001/DAYS);
                else
                    earned = (1000 * (0.001/DAYS)) + ((amount-1000) * (0.002/DAYS));
            	accountBalance += earned;
                return earned;    	
            case MAXI_SAVINGS:
//                if (amount <= 1000)
//                    return amount * 0.02;
//                if (amount <= 2000)
//                    return 20 + (amount-1000) * 0.05;
//                return 70 + (amount-2000) * 0.1;
            	if (lessThanTimePeriod()) {
            		earned = amount * (0.05/DAYS);
            	} else {
            		earned = amount * (0.001/DAYS);
            	}
            	accountBalance += earned;
                return earned;
            default:
            	earned =  amount * (0.001/DAYS);
            	accountBalance += earned;
                return earned;
        }
    }
    
    private boolean lessThanTimePeriod() {
    	 long diffInMS = Math.abs(lastWithdrawal.getTime() - localDate.getTime());
    	 long diffInDays = TimeUnit.DAYS.convert(diffInMS, TimeUnit.MILLISECONDS);
    	 if (diffInDays >= 10) {
    		 return true;
    	 } else {
    		 return false;
    	 }
    	 
    }
    
    //Removed unused boolean parameter 
    public double sumTransactions() {
       return accountBalance;
    	//return checkIfTransactionsExist();
    }

//    private double checkIfTransactionsExist() {
//        double amount = 0.0;
//        for (Transaction t: transactions)
//            amount += t.amount;
//        return amount;
//    }

    public int getAccountType() {
        return accountType;
    }
    
    public String getAccountStatement() {
        return accountStatement + "Total " + Customer.toDollars(accountBalance);
    }

}
