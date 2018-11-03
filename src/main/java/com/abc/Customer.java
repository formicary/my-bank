package com.abc;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import static java.lang.Math.abs;

public class Customer {
    private final String name;
    private List<Account> accounts;

    
    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<>();
    }
    

    public String getName() {
        return name;
    }

    
    public int getNumberOfAccounts() {
        return accounts.size();
    }
    
    //method used to test customer accounts.
    public List<Account> getAllAccounts() {
    	return accounts;
    }
    
    
    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }
    
   //added method to return customers account by passing in the account type to be returned.
    public Account getAccount(AccountType typeOf) {
    	return accounts.stream()
    				   .filter(account -> account.getAccountTypeString().equals(typeOf.toString()))
    				   .findFirst()
    				   .orElseThrow(NoSuchElementException::new);	
    }
    
   
    public double totalInterestEarned(InterestCalculator calculator) {
        double total = 0;
        for (Account a : accounts)
            total += a.interestEarned(calculator);
        return total;
    }
    
    //customer can now transfer between their own accounts only.
    public void transferBetweenAccount(Account to, Account from, double amount) {
    	if(accounts.contains(to) && accounts.contains(from)) {
    		to.deposit(amount);
    		from.withdraw(amount);
    	} else
    		throw new IllegalArgumentException("accounts must be owned by customer");
    }
        		
    /* changed all string formatting methods to use string builder instead for concatenating strings
     * as inside for loops JVM may create many string builder instances when using + operator.
     * This way only one instance gets created saving time and memory.
     */
    public String getStatement() {
        StringBuilder statement = new StringBuilder("Statement for " + name + "\n");
        double total = 0.0;
        
        for (Account a : accounts) {
        	statement.append("\n" + statementForAccount(a) + "\n");
            total += a.sumTransactions();
        }
        statement.append("\nTotal In All Accounts " + toDollars(total));
        return statement.toString();
    }
    
    
    private String statementForAccount(Account a) {
        StringBuilder statement = new StringBuilder(a.getAccountTypeString() + "\n");

        //Now total up all the transactions.
        double total = 0.0;
        for (Transaction t : a.getTransactions()) {
        	double transactionAmount = t.getAmount();
        	statement.append("  ")
        			 .append((transactionAmount < 0 ? "withdrawal" : "deposit") + " ")
        			 .append(toDollars(transactionAmount) + " ")
        			 .append("On " + t.dateToString() + "\n");
        	total += transactionAmount;
        }
        statement.append("Total " + toDollars(total)); 
        return statement.toString();
    }

    
    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}
