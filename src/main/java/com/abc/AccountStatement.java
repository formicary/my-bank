package com.abc;

import java.util.Date;

/**
 * 
 * @author Accenture, rrana
 * @version v2.0
 */
public class AccountStatement extends AbstractStatement{

    private Account account;
    
	/**
	 * 
	 * @see Com.abc.AbstractStatement
	 * @Param account the account this statement is for
	 */
	public AccountStatement(Date date, Account account) {
		super(date);
		this.account = account;
	}

	/**
	 * @see com.abc.Statement
	 */
	public String getContent() {
		 String s = "";
		 s += account.getAccountType()+ "\n";
		 
	        //Now total up all the transactions
	        double total = 0.0;
	        for (Transaction t : account.getTransactions()) {
	            s += t.getType() + ": " + toDollars(t.getAmount()) + "\n";
	            total += t.getAmount();
	        }
	        s += "Total: " + toDollars(total);
	        return s;
	}
	
}
