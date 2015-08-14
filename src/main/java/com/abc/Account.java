package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Account {

	public enum AccountType{
		CHECKING("Checking Account"),
		SAVINGS("Savings Account"),
		SUPER_SAVINGS("Super-Savings Account"),
		MAXI_SAVINGS("Maxi-Savings Account");
		
		private final String accountDesc;
		
		private AccountType(String accountDesc) {
	        this.accountDesc = accountDesc;
	    }
		
		public String toString() {
	        return accountDesc;
	    }
	}
	
	private final AccountType accountType;
    public List<Transaction> transactions;
    private double accountTotal;

    public Account(AccountType accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        this.accountTotal = 0.0;
    }

    public void deposit(double amount) throws IllegalArgumentException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        } else {
            transactions.add(new Transaction(amount));
            accountTotal += amount;
        }
    }

	public void withdraw(double amount) throws IllegalArgumentException {
	    if (amount <= 0) {
	        throw new IllegalArgumentException("Amount must be greater than zero.");
	    } else if (amount > accountTotal){
	        throw new IllegalArgumentException("Insufficient funds in account!");
	    } else {
	        transactions.add(new Transaction(-amount));
	        accountTotal -= amount;
	    }
	}

    public double getInterestEarned() {
    	// Calculate interest differently based on account type
        switch(accountType){
            case SAVINGS:
                if (accountTotal <= 1000)
                    return accountTotal * 0.001;
                else
                    return 1 + (accountTotal-1000) * 0.002;
            case SUPER_SAVINGS:
                if (accountTotal <= 1000)
                    return accountTotal * 0.02;
                if (accountTotal <= 2000)
                    return 20 + (accountTotal-1000) * 0.05;
                return 70 + (accountTotal-2000) * 0.1;
            case MAXI_SAVINGS:
                if (accountTotal <= 1000)
                    return accountTotal * 0.02;
                if (accountTotal <= 2000)
                    return 20 + (accountTotal-1000) * 0.05;
                return 70 + (accountTotal-2000) * 0.1;
            default:
                return accountTotal * 0.001;
        }
    }

    public double getAccountTotal() {
        return accountTotal;
    }

    public AccountType getAccountType() {
        return accountType;
    }

}
