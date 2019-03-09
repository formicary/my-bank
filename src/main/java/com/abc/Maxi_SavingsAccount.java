package com.abc;

import java.util.Calendar;
import java.util.GregorianCalendar;

public final class Maxi_SavingsAccount extends Account{
	private double interestrate = 0.05;
	private final Calendar defaultdate = new GregorianCalendar(2000,0,1,0,0,0);
	
	public Maxi_SavingsAccount(){
		super(accountTypes.Maxi_Savings);
	}
	
	/* Calculate all interest earned in the account by iterating through the transaction
	 * and using the formula (method) in account class. Dateandinterest method set the right
	 * interest rate for different situations.
	 */
	@Override
	public double interestEarned(){
		double totalinterest = 0.0;
		double totalamount = 0.0;
		Calendar date = new GregorianCalendar(2000,0,1,0,0,0);
		for (int i = 0; i < transactions.size(); i++){
				ITransaction t = transactions.get(i);
				totalamount += t.getTransactionAmount();
				Calendar Date2 = getCompoundperiod(i,transactions.size());
				date = Dateandinterest(t,date);
				totalinterest += calcinterest(totalamount,t.getTransactionDate(),Date2,interestrate,totalinterest);
				
			}
			return totalinterest;

	}
	
	//Check if transaction is an withdrawal
	public boolean checkwithdrawal(ITransaction t){
		if (t.getTransactionAmount() < 0){
			return true;
		}
		else{
			return false;
		}
		
	}
	

	/* Use to set the interest rate for account. If transaction is an withdrawal, then we change interest rate to 0.001 
	 * and return the date 10 days later from the withdrawal date. If the date is default or transaction date is after the date
	 * this implies either no withdrawal was made or it was made but 10 days has been past, we change the interest rate back to 0.05 
	 * otherwise, we assume that 10 days has not been past and set interest rate to 0.001 and return the date back.   
	 * 
	 */
	public Calendar Dateandinterest(ITransaction t, Calendar date){
		Calendar temp = new GregorianCalendar(2000,0,1,0,0,0);
		if (checkwithdrawal(t)){
			interestrate = 0.001;
			temp =  t.getTransactionDate();
			temp.add(Calendar.DAY_OF_MONTH, 10); 
		}
		else if (date.equals(defaultdate) || t.getTransactionDate().after(date)){
			interestrate = 0.05;
		}
		else{
			interestrate = 0.001;
			temp = date;
		}
		
		return temp;
	}
	
}
