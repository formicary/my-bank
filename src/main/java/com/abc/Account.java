package com.abc;

import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.util.Date;




public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    public List<Transaction> transactions;
    private Date openDate;


    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        this.openDate = DateProvider.getInstance().now();
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
    	}
    }
  
    public int Days(Date d1, Date d2) {
    	int daysdiff=0;
    	long diff = d2.getTime() - d1.getTime();
    	daysdiff = (int)(diff / (24 * 60 * 60 * 1000));
    	return daysdiff;
    }
    
	public double interestedEarned(Date date){
		
		int T = transactons.size();
		int counter;
		double amount = 0.0;
		double interest_rate1;
		double interest_rate2;
		int numberofDays;

		switch(accountType){
			case SAVINGS:
				for (int i = 0; i<T ; i++){
					amount += transactions[i].amount;
					if (i==T-1)
						numberofDays = Days(transactions[i].transactionDate, date);
					else
				    	numberofDays = Days(transactions[i].transactionDate, transactions[i+1].transactionDate);
					if (numberofDays > 0){
						interest_rate1 = (1+0.001/365)^(numberofDays);
						if (amount<=1000){
							amount = amount*interest_rate1;
						}
						else{
							interest_rate2 = (1+0.002/365)^(numberofDays);
							amount = 1000*interest_rate1+(amount-1000)*interest_rate2;
						}
					}
				}
				return amount - sumTransactions();
				
			case MAX_SAVINGS:
				counter = 0;
				for (int i = 0; i<T; i++){
					amount += transactions[i].amount;
					if (i==T-1)
						numberofDays = Days(transactions[i].transactionDate, date);
					else
				    	numberofDays = Days(transactions[i].transactionDate, transactions[i+1].transactionDate);
					if(numberofDays>0){
						if(transactions[i].amount < 0){
							counter = numberofDays;
							if(numberofDays<=10){
								interest_rate1 = (1+0.001/365)^(numberofDays);
								amount = amount*interest_rate1;
							}
							else{
								interest_rate2 = (1+0.5/365)^(numberofDays-10);
								amount = amount*((1+0.001/365)^10)*interest_rate2;
							}
						}
						else{
							if(counter>=10){
								interest_rate2 = (1+0.5/365)^(numberofDays);
								amount = amount*interest_rate2;
							}
							else{
								if(counter+numberofDays<=10){
									interest_rate1 = (1+0.001/365)^(numberofDays);
									amount = amount*interest_rate1;
								}
								else{
									interest_rate1 = (1+0.001/365)^(10-counter);
									interest_rate2 = (1+0.5/365)^(numberofDays-10+counter);
									amount = amount*interest_rate1*interest_rate_2;
								}
							}
						}
					}
				}
				return amount - sumTransactions();
				
			default:
				for(int i=0;i<T;i++){
					amount += transactions[i].amount;
					if (i==T-1)
						numberofDays = Days(transactions[i].transactionDate, date);
					else
				    	numberofDays = Days(transactions[i].transactionDate, transactions[i+1].transactionDate);
					if(numberofDays>0){
						amount = amount*((1+0.001/365)^(numberofDays));
					}
				}
				return amount - sumTransactions();		
		}
	}
	


    public double sumTransactions() {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }

    public int getAccountType() {
        return accountType;
    }

}
