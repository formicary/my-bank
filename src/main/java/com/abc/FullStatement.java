package com.abc;

import java.util.Date;

/*
 * @author Accenture, rrana
 * @version v2.0
 */
public class FullStatement extends AbstractStatement{
    
	Customer customer;
	
	/**
	 * 
	 * @see com.abc.AbstractStatement
	 * @param customer the customer this statement is for 
	 */
	public FullStatement(Date date, Customer customer) {
		super(date);
		this.customer = customer;
	}

	/**
	 * @see com.abc.Statement
	 */
	public String getContent() {
		String s = "";
        s = "Statement for " + customer.getName() + "\n";   
        double total = 0;
        for(Account a: customer.getAccounts()) {
        	AccountStatement as = new AccountStatement(getDate(), a);
            s += "\n" + as.getContent() + "\n";
            total += a.getBalance();
        }
        
        s += "\nTotal In All Accounts: " + toDollars(total);
        return s;
	}

}
