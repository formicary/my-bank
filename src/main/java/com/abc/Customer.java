package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Customer {
    private String name;
    private List<Account> accounts;
    

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    public String getName() {
        return name;
    }

    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    
    public double totalInterestEarned(boolean paid) {
        double total = 0;
        
    	for (Account a : accounts)
            total += a.interestEarned(paid);
        return total;
    }

    public String getStatement() {
        String statement = null;
        statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (Account a : accounts) {
            statement += "\n" + statementForAccount(a) + "\n";
            total += a.sumTransactions();
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
        return statement;
    }

    //customer should get statement for specific accounts
    //check the account exists before creating statement
    public String statementForAccount(Account a) {
        if (accounts.contains(a)) {
	    	String s = "";
	
	       //Translate to pretty account type
	        switch(a.getAccountType()){
	            case Account.CHECKING:
	                s += "Checking Account\n";
	                break;
	            case Account.SAVINGS:
	                s += "Savings Account\n";
	                break;
	            case Account.MAXI_SAVINGS:
	                s += "Maxi Savings Account\n";
	                break;
	        }
	
	        //Now total up all the transactions
	        double total = 0.0;
	        for (Transaction t : a.transactions) {
	            s += "  ";
	            switch (t.getTransactionType()) {
	            	case Transaction.DEPOSIT:
	            		s += "deposit";
	            		break;
	            	case Transaction.WITHDRAW:
	            		s += "withdrawal";
	            		break;
	            	case Transaction.INTEREST:
	            		s += "interest";
	            		break;
	            	default: 
	            		throw new IllegalArgumentException("transaction type does not exist, This should not occur");
	            }
	            
	        s +=  " " + toDollars(t.getTransactionAmount()) + "\n";
	            total += t.getTransactionAmount();
	        }
	        s += "Total " + toDollars(total);
	        return s;
	        }
        else {
    		throw new IllegalArgumentException("account does not exist for this customer");
    	}
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
    
    //Give the customer access to their accounts
    public List<Account> getAccounts() {
    	return accounts;
    }
    
    //The customer should know which account they are depositing to
    public void deposit (Account a, double amount) {
    	if (accounts.contains(a)) {
    		a.deposit(amount);
    	}
    	else {
    		throw new IllegalArgumentException("account does not exist for this customer");
    	}  	
    }
    
    //The customer should know which account they are withdrawing from
    public void withdraw (Account a, double amount) {
    	if (accounts.contains(a)) {
    		a.withdraw(amount);
    	}
    	else {
    		throw new IllegalArgumentException("account does not exist for this customer");
    	}
    	
    }
    
    //Transfer money from account a to account b
    public void transfer (Account a, Account b, double amount) {
    	if (accounts.contains(a) && accounts.contains(b)) {
    		a.withdraw(amount);
    		//will only deposit if amount is >=0, and amount is available in account a
    		//exception thrown will not allow deposit into b
    		b.deposit(amount);
    	}
    	else {
    		throw new IllegalArgumentException("account does not exist for this customer");
    	}
    }
}
