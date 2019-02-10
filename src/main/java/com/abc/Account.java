package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Account {
	
	//Interest rates
	public final double CHECKING_INTEREST_RATE = 0.001;
	public final double SAVINGS_INTEREST_RATE = 0.002;
	
    public enum accountType{
    	CHECKING, SAVINGS, MAXI_SAVINGS
    }
    
    private final accountType accountType;
    public List<Transaction> transactions;

    public Account(accountType accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    // Deposit to account 
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be greater than zero.");
        } else {
            transactions.add(new Transaction(amount, "Deposit"));
        }
    }

    // Withdraw from account
	public void withdraw(double amount) {
	    if (amount <= 0) {
	        throw new IllegalArgumentException("Withdrawal amount must be greater than zero.");
	    } 
	    else if (amount > sumTransactions()){
	    	throw new IllegalArgumentException("Insufficient funds.");
	    }
	    else {
	        transactions.add(new Transaction(-amount, "Withdrawal"));
	    }
	}

    public double interestEarned() {
        double amount = sumTransactions();
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001;
                else
                    return 1 + (amount-1000) * SAVINGS_INTEREST_RATE;
//            case SUPER_SAVINGS:
//                if (amount <= 4000)
//                    return 20;
            case MAXI_SAVINGS:
                boolean date10 = false;
                
                // Check past transactions
        		Date currentTime = DateProvider.getInstance().now();
                for (int transactionNo = transactions.size(); transactionNo>0; transactionNo--){
                	
                	//Check if date of withdrawal
                	if (transactions.get(transactionNo).transactionType.equals("Withdrawal")){
            
                		Date transactionTime = transactions.get(transactionNo).getTime();

                		if (DateProvider.calcDateDiff(currentTime, transactionTime)){
                			date10 = true;
                		}
                		else{
                			date10 = false;
                			break;
                		}
                	}
                }
                //Apply Maxi-Savings Intrest
                if (date10){
                	return amount * 0.05;
                }
                else{
                	return amount * 0.001;
                }           
            //Case for checking accounts
            default:
                return amount * CHECKING_INTEREST_RATE;
        }
    }

    public double sumTransactions() {
       return checkIfTransactionsExist(true);
    }

    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }

    public accountType getAccountType() {
        return accountType;
    }

}
