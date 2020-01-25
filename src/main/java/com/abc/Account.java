package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    public List<Transaction> transactions;

    public Account(int accountType) {
    	//only allow specified account types to be created
    	if(accountType==CHECKING||accountType==SAVINGS||accountType==MAXI_SAVINGS) {
    		this.accountType = accountType;
            this.transactions = new ArrayList<Transaction>();
    	}else {
    		throw new IllegalArgumentException("account type must be CHECKING(0),"
    		 		+ " SAVINGS(1) or MAXI_SAVINGS(2)");
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
	        //programming test has not specified if overdrafts (negative balance) is allowed
	        //I have allowed overdrafts
	        //otherwise, if no overdrafts allowed check amount<=amount in account
	    }
	}

    public double interestEarned() {
        double amount = sumTransactions();
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001;
                else
                    return 1 + (amount-1000) * 0.002;
            case MAXI_SAVINGS:
            	//filter out withdrawals
            	List<Transaction> withdrawalList=new ArrayList<Transaction>();
            	for(Transaction transaction:transactions) {
            		if(transaction.getAmount()<0) {
            			withdrawalList.add(transaction);
            		}
            	}
            	
            	if(withdrawalList.size()>0) {
            		//gets the most recent transaction
                	Transaction lastTransaction=withdrawalList.get(withdrawalList.size()-1);
                	
                	//if transaction is over 10 days old use 5% else 0.1%
                	return (lastTransaction.transactionIsOverTenDaysOld(new Date()) 
                			? amount * 0.05 : amount *0.001);
            	}else {
            		//no withdrawals
            		return amount * 0.05;
            	}
        
            default:
                return amount * 0.001;
        }
    }

    public double sumTransactions() {
       //rewritten so sumTransaction sums transaction 
       //instead of returning a value from another function
       if(checkIfTransactionsExist()) {
    	   double amount = 0.0;
           for (Transaction t: transactions)
               amount += t.amount;
           return amount;
       }else {
    	   return 0;
       }
    }

    private boolean checkIfTransactionsExist() {
    	//rewritten so checkIfTransactionExists returns a boolean 
    	return (transactions.size()>0 ? true : false);
    }

    public int getAccountType() {
        return accountType;
    }

}
