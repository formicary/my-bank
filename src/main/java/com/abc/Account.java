package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Account {

	private static int nextAccountNum = 0;
	
	protected final int accountNum;
	protected final AccountType accountType;
	protected Date dateLastWithdrawal;
    
	public List<Transaction> transactions;

    public abstract double interestEarned();
    
    public Account(AccountType accountType) {
        this.accountType = accountType;
        this.accountNum = nextAccountNum;
        nextAccountNum++; //This could be a problem if accounts are being made by multiple threads. Would require a lock. 
        this.transactions = new ArrayList<Transaction>();
    }

    /**
     * Deposits money into the account
     * @param amount - the amount money going into the account
     */
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    /**
     * Withdraw money from the account
     * @param amount - the amount of money coming out of the account
     */
    public void withdraw(double amount) {
    	if (amount <= 0) {
    		throw new IllegalArgumentException("amount must be greater than zero");
    	} else {
    		Transaction t = new Transaction(-amount);
    		transactions.add(t);
    		dateLastWithdrawal = t.getTransactionDate(); //It is more simple to note the last withdrawal than to search for it. (Especially for accounts with a very large number of transactions)
    	}
    }

    public double sumTransactions() {
    	  double amount = 0.0;
          for (Transaction t: transactions)
              amount += t.amount;
          return amount;
    }

    public AccountType getAccountType() {
        return accountType;
    }
    
    public int getAccountNumber(){
    	return accountNum;
    }
    
    public static void resetAccountNumber(){
    	nextAccountNum = 0;
    }

}
