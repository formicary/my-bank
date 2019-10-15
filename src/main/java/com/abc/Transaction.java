package com.abc;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class Transaction {
    public final double amount;

    private Date transactionDate;
    
    public static final int DEPOSIT = 0;
    public static final int WITHDRAWL = 1;
    public static final int TRANSFER = 2;
    
    private final int transactionType;

    public Transaction(Account account,double amount,int transactionType) {
    		if ((account.balance + amount) >= 0) {		//check there are sufficient funds in account
    			this.amount = amount;
    	        this.transactionDate = DateProvider.getInstance().now();
    	        account.balance += amount;				//update account balance
    	        this.transactionType = transactionType;
    		} else {
    			throw new IllegalArgumentException("Insufficient Funds");
    		} 
        
        
    }

	public Date getTransactionDate() {
		return transactionDate;
	}
	
	public void setTransactionDate(Date d) {
		this.transactionDate = d;
	}
	
	public Boolean checkTransactionIsInLastTenDays() {
		Date transactionDate = this.getTransactionDate();	//transaction date
		
		Date now = DateProvider.getInstance().now();		//get date now
		Date TenDaysAgo = subtractDays(now,10);			//get date 10 days ago
		
		return transactionDate.after(TenDaysAgo) && transactionDate.before(now);	//check if transaction date is between now and 10 days ago
	}
	
	public static Date subtractDays(Date date, int days) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE,-days);
				
		return calendar.getTime();
	}
	
	public int getTransactionType() {
		return this.transactionType;
	}
	
	public String getTransactionTypeString() {
		switch(getTransactionType()){
		case 0:
			return "deposit";
		case 1:
			return "withdrawl";
		case 2:
			return "transfer";
		default:
			return "Error";
		}
	}

}
