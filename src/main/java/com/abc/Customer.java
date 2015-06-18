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

    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            total += a.interestEarned();
        return total;
    }
    
    public int makeTransfer(int accountIdx1, int accountIdx2,
    		double amount) {
    	// for now transfer between two accounts by number
    	// (this can be made easier to use in a GUI or something)
    	// return an int for possible error handling
    	
    	// FROM idx1 TO idx2
    	int na = getNumberOfAccounts();
    	if(accountIdx1 >= na || accountIdx1 < 0) {
    		throw new IllegalArgumentException("account " + accountIdx1 + " does not exist.");
    	}
    	if(accountIdx2 >= na || accountIdx2 < 0) {
    		throw new IllegalArgumentException("account " + accountIdx2 + " does not exist.");    		
    	}
    	if(accountIdx1 == accountIdx2) {
    		throw new IllegalArgumentException("account source and destination are the same.");
    	}
    	
    	try {
    		accounts.get(accountIdx1).withdraw(amount, true);
    	} catch(IllegalArgumentException e) {
    		e.printStackTrace();
    		return 1;
    	}
    	accounts.get(accountIdx2).deposit(amount, true);
    	return 0;
    }

    public String getStatement() {
        String statement = null;
        statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (Account a : accounts) {
            statement += "\n" + statementForAccount(a) + "\n";
            total += a.getAccountBalance();
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
        return statement;
    }

    private String statementForAccount(Account a) {
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
        	String transactionType = "";
        	switch(t.transactionType) {
        		case Transaction.INTEREST:
        			transactionType += "interest";
        			break;
        		case Transaction.DEPOSIT:
        			transactionType += "deposit";
        			break;
        		case Transaction.WITHDRAWAL:
        			transactionType += "withdrawal";
        			break;
        		case Transaction.TRANSFER_IN:
        			transactionType += "transfer in";
        			break;
        		case Transaction.TRANSFER_OUT:
        			transactionType += "transfer out";
        			break;
        		default:
        			transactionType += "undefined";
        			break;
        	}
            s += "  " + transactionType + " " + toDollars(t.amount) + "\n";
            total += t.amount;
        }
        s += "Total " + toDollars(total);
        return s;
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}
