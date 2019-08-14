package com.abc;

import java.util.ArrayList;
import java.util.List;

public abstract class Account {

    protected List<Transaction> transactions;
    private AccountType accountType;
    
    
    protected Account(AccountType type) {
        this.transactions = new ArrayList<Transaction>();
        this.accountType = type;
    }

    // Thread safe concrete generic operations for all bank account types using Money type

    public synchronized void deposit(Money money) {
    	// Check money value is greater than 0 using compareTo
        if (money.getAmount().compareTo(Money.ZERO_VALUE) == 1) {
        	transactions.add(new Transaction(money));
        } else {          
            throw new IllegalArgumentException("Deposit must be greater than zero");
        }
    }

    // unsure if going overdrawn is allowed so didn't look for it
	public synchronized void withdraw(Money money) {
		if (money.getAmount().compareTo(Money.ZERO_VALUE) == 1) {
			// Needs to be minus added to tx as withdrawal
			transactions.add(new Transaction(new Money("-" + money.getAmount())));
	    } else {
	    	throw new IllegalArgumentException("Amount must be greater than zero");
	    }
	}
	
    public synchronized Money sumTransactions() {
    	Money money = new Money("0.00");
    	for (Transaction t: transactions){
    		//TODO - Not scalable - needs revision
            money = new Money(money.getAmount().add(t.getMoney().getAmount()));
    	}
        return money;
     }
    
    // Polymorphic 
	public AccountType getAccountType() {
		return accountType;
	}
	
	// Simple utility method to transfer money between customer accounts
	static void transfer(Customer c, Account a, Account b, Money amount){
		if(c.getAccounts().contains(a) && c.getAccounts().contains(b)){
			a.withdraw(amount);
			b.deposit(amount);
		}else{
			throw new IllegalArgumentException("Customer doesn't own both accounts");
		}
	}
    

	public abstract Money interestEarned();




}
