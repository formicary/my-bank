package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Account {

	public enum AccountType { CHECKING, SAVINGS, MAXI_SAVINGS};

    private AccountType accountType;
    private List<Transaction> transactions;

    public Account(AccountType accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
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

	
	//TODO ADD CONSTANTS??????
    public double interestEarned() {
        double amount = sumTransactions();
        double interestEarned = 0;
     
        switch(accountType){
        	case CHECKING:
        		interestEarned = amount * 0.001;
        		break;
        	case SAVINGS:
                if (amount <= 1000) {
                	interestEarned = amount * 0.001;
                }else {
                	interestEarned = 1 + (amount-1000) * 0.002;
                }
                break;
            case MAXI_SAVINGS:
            	Date currentDate = DateProvider.getInstance().now();
                Transaction lastTransaction = null;
            	
            	//check last transaction to see what rate to apply                
                //get last transaction
                for (int i = (transactions.size()-1); i>=0; i--) {
                	if (transactions.get(i).isWithdraw()) {
                		lastTransaction = transactions.get(i);
                	};	
                }
                
                long diffDays;
                if (lastTransaction != null) {
                	//find the differences between last withdraw and current day
                	long diff = currentDate.getTime() - lastTransaction.getTransactionDate().getTime();
                	//convert to days
        			diffDays = diff / (24 * 60 * 60 * 1000);
        			if (diffDays < 10) {
        				interestEarned = amount * 0.001;
            		}else {
                    	interestEarned = amount * 0.05;
                    }
                }else {
                	interestEarned = amount * 0.05;	
                }
                break;
        }	
        return interestEarned;
    }  
    
    
    public double sumTransactions() {
    	double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.getAmount();
        return amount;
    }
    
    public AccountType getAccountType() {
        return accountType;
    }

    public List<Transaction> getTransactions(){
    	return transactions;
    }
    
    public void addTransaction(Transaction transaction) {
    	transactions.add(transaction);
    }

}
