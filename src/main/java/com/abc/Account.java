package com.abc;

//Imports
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abc.Transaction;

public class Account {

	//Set constants to define account types
    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    //Set variables to define an account instance's type and list of transactions
    private final int accountType;
    public List<Transaction> transactions;

    //Constructor
    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    //Method to deposit money into an account
    public void deposit(double amount) {
        if (amount <= 0.0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }
    
    //Method to withdraw money out of an account
	public void withdraw(double amount) {
	    if (amount <= 0.0) {
	        throw new IllegalArgumentException("amount must be greater than zero");
	    } else {
	        transactions.add(new Transaction(-amount));
	    }
	}

	//Method to determine the daily accrued interest made in an account of the types defined.
    public double interestEarned() {
    	//Variables to define the amount of money in the account after totalling all transactions made as well as the number of days in the year, whether it be 365 or 366
        double amount = sumTransactions();
        int numOfDays = DateProvider.daysInYear();
        
        switch(accountType){
        	//Interest earned for a savings account
            case SAVINGS:
                if (amount <= 1000.0)
                    return amount * (0.001 / numOfDays);
                else
                    return (1.0 / numOfDays) + ((amount-1000) * (0.002 / numOfDays));
            //Interest earned for a maxi savings account
            case MAXI_SAVINGS:
            	int numOfTransactions = transactions.size();
            	boolean goodInterestRate = true;
            	while(numOfTransactions > 0) {
            		Date currentDate = DateProvider.getInstance().now();
                	Date transactionDate = transactions.get(numOfTransactions - 1).getTransactionDate();
                	double transactionAmount = transactions.get(numOfTransactions - 1).amount;
                	
                	if(currentDate.getTime() - transactionDate.getTime() < (10 * 24 * 60 * 60 * 1000) && transactionAmount < 0.0) {
                		goodInterestRate = false;
                	}
                	if(currentDate.getTime() - transactionDate.getTime() > (10 * 24 * 60 * 60 * 1000)) {
                		break;
                	}
                	
                	numOfTransactions--;
            	}
            	if(goodInterestRate == true) {
            		return amount * (0.05 / numOfDays);
            	}
            	else {
            		return amount * (0.001 / numOfDays);
            	}
            //Interest earned for a checking account
            default:
                return amount * (0.001 / numOfDays);
        }
    }

    //Method to sum all transactions provided there has been at least one transaction made
    public double sumTransactions() {
       return checkIfTransactionsExist(true);
    }

    //Child method for above method
    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }

    //Account type getter
    public int getAccountType() {
        return accountType;
    }

}
