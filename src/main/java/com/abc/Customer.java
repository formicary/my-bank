package com.abc;

import java.util.ArrayList;
import java.util.List;

import java.math.BigDecimal;

public class Customer {
	private String name;
    private List<Account> accounts;

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }
    
    public void transferAccountFunds(BigDecimal amount, Account fromAccount, Account toAccount) {
    	BigDecimal fromAccountBalance = fromAccount.getBalance();
    	
    	if (amount.compareTo(BigDecimal.ZERO) < 1) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        } else if (fromAccountBalance.compareTo(amount) < 0) {
        	throw new IllegalArgumentException("From account balance must be greater or equal to the amount");
        } else {
        	fromAccount.withdraw(amount);
        	toAccount.deposit(amount);
        }
    }

    public BigDecimal totalInterestAccrued() {
        BigDecimal totalInterest = BigDecimal.ZERO;
        for (Account account : accounts) {
        	account.accrueInterest();
        	totalInterest = totalInterest.add(account.getAccruedInterest());
        }
        return totalInterest;
    }

    public String getStatement() {
        String statement = null;
        statement = "Statement for " + name + "\n";
        
        BigDecimal totalBalance = BigDecimal.ZERO;
        for (Account account : accounts) {
            statement += "\n" + statementForAccount(account) + "\n";
            totalBalance = totalBalance.add(account.getBalance());
        }
        statement += "\nTotal In All Accounts " + toDollars(totalBalance);
        
        return statement;
    }

    private String statementForAccount(Account account) {
        String statement = account.getAccountType().toString() + " Account\n";

        BigDecimal total = BigDecimal.ZERO;
        for (Transaction transaction : account.getTransactions()) {
        	statement += "  " + transaction.getTransactionType().toString() + " " + toDollars(transaction.getAmount()) + "\n";
            total = total.add(transaction.getAmount());
        }
        statement += "Total " + toDollars(total);
        
        return statement;
    }

    private String toDollars(BigDecimal amount){
        return String.format("$%,.2f", amount.abs());
    }  

	public void setName(String name) {
		this.name = name;
	}
    
    public String getName() {
        return name;
    }
    
    public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }
}
