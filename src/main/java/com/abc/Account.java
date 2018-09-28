package com.abc;

import java.util.ArrayList;
import java.util.List;

enum AccountType {
	CHECKING, 
	SAVINGS,
	MAXI_SAVINGS
}

public class Account {

    private final AccountType accountType;
    private List<Transaction> transactions;

    public Account(AccountType accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }
    
    public AccountType getAccountType() {
        return accountType;
    }
    
    public List<Transaction> getTransactions(){
    	return this.transactions;
    }
    
    public double getBalance() {
    	double amount = 0.0;
    	if (transactionsExist() == true) {
    		for (Transaction t: transactions)
    			amount += t.getAmount();
    	}
        return amount;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    public void withdraw(double amount) {
    	double accountTotal = getBalance();
    	if (accountTotal - amount < 0){ //condition to catch customers trying to withdraw more than is available in their account
    		throw new IllegalArgumentException("Insufficient funds. Try to withdraw a smaller amount");
    	} else {
    		transactions.add(new Transaction(-amount));
    	}
    }

    public double interestEarned() {
        double amount = getBalance();
        
        // if the account total = 0, no interest can be earned so return 0
        if (amount == 0.0){
        	return 0.0;
        }
        
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001;
                else
                    return 1 + (amount-1000) * 0.002;
            case MAXI_SAVINGS:
                if (amount <= 1000)
                    return amount * 0.02;
                if (amount <= 2000)
                    return 20 + (amount-1000) * 0.05;
                if (amount > 2000)
                	return 70 + (amount-2000) * 0.1;
            default:
                return amount * 0.001;
        }
    }

    //checks if there are any transactions existing 
    private boolean transactionsExist() {
        if(transactions.isEmpty()){
        	return false;
        } else {
        	return true;
        }
    }



}
