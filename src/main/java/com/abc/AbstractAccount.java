package com.abc;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * This class defines methods whose implementation is same for all types of accounts
 * @author Accenture, rrana
 *
 */
public abstract class AbstractAccount implements Account{
	
    private Customer customer;
    protected double balance;
    private String accountType;

    
    public ArrayList<Transaction> transactions;

    /**
     * Constructor 
     * @param customer The customer this account belongs to
     * @param balance The starting balance
     */
    public AbstractAccount(Customer customer, double balance) {
    	this.customer = customer;
    	this.balance = balance;
        this.transactions = new ArrayList<Transaction>();
    }
    
    /**
     * Set type of account
     * @param en the Enum type  
     */
    protected void setType(AccountEnum en) {
    	accountType = en.getType();
    }
    
    /**
     * @return balance the current balance of the account
     */
    public double getBalance() {
    	return balance;
    }
    
    /**
     * Deposit amount
	 * @param amount the amount to be deposited
     */
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
        	balance += amount;
            transactions.add(new Deposit(amount));
        }
    }

    /**
     * Withdraw amount
     * @param amount the amount to be withdrawn
     */
    public void withdraw(double amount) {
    	if (amount <= 0 || amount >= balance) {
    		throw new IllegalArgumentException("amount must be greater than zero & less than available balance");
    	} else {
    		balance -= amount;
    		transactions.add(new Withdraw(amount));
    	}
    }
    
    /**
     * 
     * @return The list of transaction on this account
     */
    public ArrayList<Transaction> getTransactions(){
    	return transactions;
    }

    /**
     * @return the sum of all transaction
     */
    public double sumTransactions() {
       return checkIfTransactionsExist(true);
    }

    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.getAmount();
        return amount;
    }

    /**
     * @return the type of account
     */
    public String getAccountType() {
        return accountType;
    }
    
    /**
     * 
     * @return most recent transaction
     */
    protected Transaction getLatestTransaction() {
    	return transactions.get(transactions.size() - 1);
    }

    /**
     * Transfer a given amount to any given account or receive an amount 
     * @param account the account to which to transfer the amount
     * @param balance the amount to be transferred
     * @param isSent true if the amount is sent, false if it is received
     */
    public boolean transferAmount(Account account, double amount, boolean isSent) {
    	assert(account != null): "Argument account is null!!";
    	assert(amount > 0 & amount<=balance): "Argument amount value entered is not valid";
    	if(isSent) {
    		balance -= amount;
    		transactions.add(new Transfer(amount, false));
    		account.transferAmount(this,amount,false);
    		return true;
    	}else {
    		balance += amount;
    		transactions.add(new Transfer(amount, true));
    		return true;
    	}		
    }
    
    /**
     * 
     * @return True if transactions has been carried out on this account
     */
    protected boolean hasTransactions() {
    	return this.transactions.isEmpty()? false: true;
    }
}
