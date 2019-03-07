package com.abc;

import java.util.ArrayList;
import java.util.List;


public class Account {

    /*public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;*/
	enum accountTypes {CHECKING, SAVINGS, MAXI_SAVINGS};
	private final accountTypes type;
    //changed to private
	private List<Transaction> transactions;
    
    public Account(accountTypes mytype) {
        this.type = mytype;
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount) {
    	if (checkpositiveAmount(amount)){
    		addtransaction(amount);
    		//transactions.add(new Transaction(amount));
    	}
    	/*
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }*/
    }

	public void withdraw(double amount) {
		if (checkpositiveAmount(amount)){
			checkAmountavailable(amount);
		}
		/*
	    if (amount <= 0) {
	        throw new IllegalArgumentException("Amount withdrawed must be greater than zero.");
	    } else {
	    	checkamountavailable(amount);
	    }*/
	}

	private boolean checkpositiveAmount(double amount){
	    if (amount <= 0) {
	        throw new IllegalArgumentException("Amount inputed must be greater than zero.");
	    } 
	    else {
	    	return true;
	    }
	    
	}
	
	private void checkAmountavailable(double amount){
		double totalamount = sumTransactions();
    	if (amount > totalamount){
    		throw new IllegalArgumentException("Amount withdraw is greater than amount in account. \n Total amount = "+Double.toString(totalamount));
    	}
    	else{
    		addtransaction(-amount);
            //transactions.add(new Transaction(-amount));
    	}
	}
	
	private void addtransaction(double amount){
		transactions.add(new Transaction(amount));
	}
	
    /*public double interestEarned() {
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
                if (amount <= 1000)
                    return amount * 0.02;
                if (amount <= 2000)
                    return 20 + (amount-1000) * 0.05;
                return 70 + (amount-2000) * 0.1;
            default:
                return amount * 0.001;
        }
    }*/

    public double sumTransactions() {
        if(checkIfTransactionsExist()){
        	return totalamount();
        }else{
        	double amount = 0.0;
        	return amount;
        }
        	
    }

    private boolean checkIfTransactionsExist() {
    	if (transactions.isEmpty()){
    		return false;
    	}
    	else{
    		return true;
    	}
        /*double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;*/
    }
    
    private double totalamount(){
    	double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.getTransactionAmount();
        return amount;
    }

    public String getAccountType() {
        return type.toString().toLowerCase();
    }
    
    public List<Transaction> getTransactions(){
    	return transactions;
    }
    
	public double interestEarned() {
		return 0;
	}
}
