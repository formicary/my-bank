package com.abc.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Customer {
	
    private final String name;
    private final Set<Account> accounts;

    public Customer(String name) {
    	Objects.requireNonNull("The name of the customer is required");
        this.name = name;
        this.accounts = new HashSet<Account>(0);
    }

    public String getName() {
        return name;
    }

    public void addAccount(final Account account){
    	Objects.requireNonNull(account);
    	this.accounts.add(account);
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    
    public Money totalInterestEarned() {
        Money amount = Money.ZERO_USD;
        for (final Account account : accounts){
        	amount = amount.plus( account.getIntrestPaid() );
        }
        return amount;
    }
    
    public Set<Account> getAccounts(){
    	return accounts;
    }

    public Account getAccountByNumber(final int number){
    	for(Account account : accounts){
    		if(account.getNumber() == number){
    			return account;
    		}
    	}
    	return null;
    }
    
}
