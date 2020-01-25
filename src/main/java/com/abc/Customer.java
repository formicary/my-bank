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

    public String getStatement() {
    	StringBuilder builder=new StringBuilder();
        String statement = "";
        statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (Account account : accounts) {
        	//string builder a bit more efficient in loops
        	builder.append("\n");
        	builder.append(statementForAccount(account));
        	builder.append("\n");
            total += account.sumTransactions();
        }
        statement+=builder.toString();
        statement += "\nTotal In All Accounts " + toDollars(total);
        return statement;
    }
    
    public void transfer(Account from, Account to,double amountTransfer) {
    	//check both accounts belong to the same account holder
    	if(accounts.contains(from) && accounts.contains(to)) {
    		from.withdraw(amountTransfer);
    		to.deposit(amountTransfer);
    	}else {
    		throw new IllegalArgumentException("both accounts must belong to the customer");
    	}
    }

    private String statementForAccount(Account account) {
        String statement = "";

       //Translate to pretty account type
        switch(account.getAccountType()){
            case Account.CHECKING:
            	statement += "Checking Account\n";
                break;
            case Account.SAVINGS:
            	statement += "Savings Account\n";
                break;
            case Account.MAXI_SAVINGS:
            	statement += "Maxi Savings Account\n";
                break;
        }
        
        
        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : account.transactions) {
        	statement += "  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount) + "\n";
            total += t.amount;
        }
        statement += "Total " + toDollars(total);
        return statement;
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
    
}
