package com.abc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


public class Account {

	public enum accountType {
		CHECKING, SAVINGS, MAXI_SAVINGS;
	}
	
    private final accountType accountType;
    public List<Transaction> transactions;
    private Calendar calendar;

    public Account(accountType accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        this.calendar = new GregorianCalendar();
    }

    public int getDaysInYear(Date date) {
    	calendar.setTime(date);
    	int numbOfDays = calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
    	return numbOfDays;
    }
    
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

	public void withdraw(double amount) {
	    if (amount <= 0) {
	        throw new IllegalArgumentException("Amount must be greater than zero");
	    } else {
	        transactions.add(new Transaction(-amount));
	    }
	}

	
	// Calculates interest by adding all transactions for day, working out interest for day and repeating for all days
    public double interestEarned() {

    	double amount = 0.0;
        double compoundInterest = 0.0;
        Date previousTransactionDate = null;
        Date currentTransactionDate = null;
        int daysInYear = 0;
        for (Transaction t: transactions) {
        	if (currentTransactionDate == null) {
        		currentTransactionDate = t.getTransactionDate();
        		amount = t.getAmount();
        		daysInYear= getDaysInYear(currentTransactionDate);
        		continue;
        	}
        
        if (transactionsOnSameDay(currentTransactionDate, t.getTransactionDate())) {
        	amount += t.getAmount();
        	currentTransactionDate = t.getTransactionDate();
        	continue;
        }else {
        	compoundInterest += calculateDayInterest(amount, daysInYear, previousTransactionDate);
        	
        	previousTransactionDate = currentTransactionDate;
        	currentTransactionDate = t.getTransactionDate();
        	amount = t.getAmount();
        	daysInYear= getDaysInYear(currentTransactionDate);
        }
        
            amount += t.getAmount();
        }
        

    	compoundInterest += calculateDayInterest(amount, daysInYear, previousTransactionDate);

        
        return compoundInterest;

    }
    
    private double calculateDayInterest(double amount, int daysInYear, Date date) {
    	switch(accountType){
        case CHECKING:
        	return checkingInterest(amount, daysInYear);
        case SAVINGS:
        	return savingsInterest(amount, daysInYear);
        case MAXI_SAVINGS:
        	return maxiSavingsInterest(amount,daysInYear, date);
        default:
        	throw new IllegalArgumentException("Account type not recognised");
    	}
    }
    
    private boolean transactionsOnSameDay(Date transaction1, Date transaction2) {
    	Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(transaction1);
        cal2.setTime(transaction2);
        boolean sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                          cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
        return sameDay;
    }
    
    private double savingsInterest(double amount, int daysInYear) {
    	double lessThanThousandInterest = 0.001 / daysInYear;
    	double moreThanThousandInterest = 0.002 / daysInYear;
    	
    	if (amount <= 1000)
            return amount * lessThanThousandInterest;
        else
            return (1000 * lessThanThousandInterest) + ((amount-1000) * moreThanThousandInterest);
    }
    
    private double maxiSavingsInterest(double amount, int daysInYear, Date date) {
    	
    	long tenDays = 864000000; //In ms
    	
    	double interestTransactionWithinTenDays = 0.001 / daysInYear;
    	double interestTransactionNotWithinTenDays = 0.05 / daysInYear;
    	
    	for (Transaction t: transactions) {
    		if (date != null && date.getTime() - t.getTransactionDate().getTime() > tenDays) {
    			return amount * interestTransactionNotWithinTenDays;
    		}
    	}
    	
    	return amount * interestTransactionWithinTenDays;
    }
    
    private double checkingInterest(double amount,int daysInYear) {
    	double flatRateInterest = 0.001 / daysInYear;
    	return amount * flatRateInterest;
    }

    public double sumTransactions() {
       return checkIfTransactionsExist(true);
    }

    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.getAmount();
        return amount;
    }

    public accountType getAccountType() {
        return accountType;
    }

}
