package com.abc;

import java.util.ArrayList;
import java.util.Calendar;
import java.lang.Math;
import java.util.List;


public class Account implements IAccount {

	enum accountTypes {Checking, Savings, Maxi_Savings};
	protected final accountTypes type;
	protected List<Transaction> transactions;
    
    public Account(accountTypes mytype) {
        this.type = mytype;
        this.transactions = new ArrayList<Transaction>();
    }
    
    //Deposit money into account
    public void deposit(double amount) {
    	if (checkpositiveAmount(amount)){
    		addtransaction(amount);
    	}
    }
    
    //Withdraw money into account
	public void withdraw(double amount) {
		if (checkWithdraw(amount)){
			addtransaction(-amount);
		}
	}
	
	//Make sure the inputed amount is positive
	private boolean checkpositiveAmount(double amount){
	    if (amount <= 0) {
	        throw new IllegalArgumentException("Amount inputed must be greater than zero.");
	    } 
	    else {
	    	return true;
	    }
	}
	
	//Use to check return the boolean value of two conditions
	private boolean checkWithdraw(double amount){
		return (checkpositiveAmount(amount) && checkAmountavailable(amount));
	}
	
	//Make sure the amount withdraw from amount does not exceed amount in account
	private boolean checkAmountavailable(double amount){
		double totalamount = sumTransactions();
    	if (amount > totalamount){
    		throw new IllegalArgumentException("Amount inputed is greater than amount in account. \n Total amount = "+Double.toString(totalamount));
    	}
    	else{
    		return true;
    	}
	}
	
	//Add the transaction into the array.
	private void addtransaction(double amount){
		transactions.add(new Transaction(amount));
	}
	
	//Return the total amount in account is transaction exists in account.
	public double sumTransactions() {
        if(checkIfTransactionsExist()){
        	return totalamount();
        }else{
        	return 0.0;
        }
    }

	//Check if transactions exists in account.
    private boolean checkIfTransactionsExist() {
    	if (transactions.isEmpty()){
    		return false;
    	}
    	else{
    		return true;
    	}
    }
    
   //Calculate the total amount of money by iterating through the list of transactions.
    private double totalamount(){
    	double amount = 0.0;
        for (ITransaction t: transactions)
            amount += t.getTransactionAmount();
        return amount;
    }
    
    //Return the account type
    public String getAccountType() {
        return type.toString();
    }
    
    //Return the list of transaction for account
    public List<Transaction> getTransactions(){
    	return transactions;
    }
    
    //Method used to calculate interested earned, to be override by subclasses
	public double interestEarned() {
		return 0;
	}
	
	//Calculate the interest between two different transactions with different dates
	protected double calcinterest(double amount, Calendar date1, Calendar date2, double rate, double currentinterest){
		IDateProvider dates = new DateProvider();
		if (dates.ifdateafter(date2,date1)){
			double diff = dates.daydifference(date1, date2);
			double amountwithInterest = amount + currentinterest;
			return CalcCompoundInterest(amountwithInterest, rate, diff);
		}
		else{
			return 0.0;
		}
	}
	
	/*Perform the calculation for compound interest formula
	*Formula = p (1 - r/n)^(n*t) - p where p = principle amount,  r = interest rate per annum
	*n = number of times the interest is compounded per year, t = the number of years the money is invested 
	* Money is compounded daily so n = 365 
	*/
	private double CalcCompoundInterest(double amount, double rate, double numofdays){
		double interest = (1 + rate/365.00);
		interest = Math.pow(interest,(365.00 * (numofdays/365.00)) );
		return (amount*interest) - amount;
	}
	
	//Get the date for next transaction otherwise return the last transaction date 
	protected Calendar getCompoundperiod(int i, int size){
		if (i+1 != size){
			return transactions.get(i+1).getTransactionDate();					
		}else{
			//DateProvider dates = new DateProvider();
			//return dates.getcurrentdate();
			return transactions.get(i).getTransactionDate();
		}
	}
	
	//Get the total interest earned for the account. 
	public double totalinterestEarned(){
		if (checkIfTransactionsExist()){
			return interestEarned();
		}
		else{
			return 0.0;
		}
	}
}
