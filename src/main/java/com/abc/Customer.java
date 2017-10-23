package com.abc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.math.BigDecimal;

public class Customer {
    private String name;
    private final List<Account> ACCOUNTS;

    public Customer(String name) {
        this.name = name;
        this.ACCOUNTS = Collections.synchronizedList(new ArrayList<Account>());
    }

    public String getName() {
        return name;
    }
    
    public void setName(String aName) {
    	name = aName;
    }

    public Customer openAccount(Account account) {
    	if(account != null) {
    		ACCOUNTS.add(account);
    	}
    	
    	return this;
    }

    public int getNumberOfAccounts() {
        return ACCOUNTS.size();
    }
    
    public Iterator<Account> getAccountIterator() {
    	return ACCOUNTS.iterator();
    }
    
    public List<Account> getAccountListClone() {
    	return new ArrayList<Account>(ACCOUNTS);
    }

    public BigDecimal totalInterestEarned() {
        BigDecimal result = BigDecimal.ZERO;
        for (Account acc : ACCOUNTS)
            result = result.add(acc.interestEarned());
        return result;
    }
    
    public BigDecimal totalAccountHoldings() {
        BigDecimal result = BigDecimal.ZERO;
        for (Account acc : ACCOUNTS)
            result = result.add(acc.sumTransactions());
        return result;
    }

    public String getStatement() {
        StringBuilder result = new StringBuilder();
        BigDecimal total = BigDecimal.ZERO;
        
        result.append("Statement for ").append(name).append('\n');
        for (Account acc : ACCOUNTS) {
        	result.append('\n').append(acc.getStatement()).append('\n');
            total = total.add(acc.sumTransactions());
        }
        result.append("\nTotal In All Accounts ");
        result.append(Transaction.toCurrecy(total));
        
        return result.toString();
    }
    
    public boolean accountTransfer(Account from, Account to, BigDecimal amount) {
    	boolean result = false;

    	if(checkOwnAccounts(from, to)) {
    		synchronized(from) {
	    		if(from.sumTransactions().compareTo(amount) >= 0) {
	    			from.withdraw(amount);
	    			to.deposit(amount);
	    			result = true;
	    		}
    		}
    	}
    	
    	return result;
    }
    
    public boolean checkOwnAccounts(Account... accounts) {
    	boolean result = false;
    	
    	if(accounts != null && accounts.length > 0) {
    		for(Account a : ACCOUNTS) {
    			for(int i=0; i < accounts.length; i++) {
    				accounts[i] = accounts[i] == a ? null : accounts[i];
    			}
    		}
    		
    		result = true;
    		for(Account a : accounts) {
    			if(a != null) {
    				result = false;
    				break;
    			}
    		}
    	}
    	
    	return result;
    }
}
