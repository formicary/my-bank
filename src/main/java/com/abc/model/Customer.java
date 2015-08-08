package com.abc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Customer {
	
    private final String name;
    private final List<Account> accounts;

    public Customer(String name) {
    	Objects.requireNonNull("The name of the customer is required");
        this.name = name;
        this.accounts = new ArrayList<Account>(0);
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
        	amount = amount.add( account.getIntrestPaid() );
        }
        return amount;
    }
    
    public List<Account> getAccounts(){
    	return accounts;
    }

}
