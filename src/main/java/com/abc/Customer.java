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
        String statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (int i = 0; i < accounts.size(); i++) {
        	Account a = accounts.get(i);
            statement += "\nNo." +(i+1)+" "+ statementForAccount(a) + "\n";
            total += a.sumTransactions();
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
        return statement;
    }

    private String statementForAccount(Account a) {
        String s = a.getAccountType()+" Account\n";
        double total = 0.0;
        
        for (Transaction t : a.getTransactions()) {
        	s += printTransaction(t.getTransactionAmount());
            total += t.getTransactionAmount();
        }
        s += "Total " + toDollars(total);
        return s;
        /*
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
            s += "  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount) + "\n";
            total += t.amount;
        }
        s += "Total " + toDollars(total);
        return s;
        */
    }
    
    private String printTransaction(double amount){
    	String transactionAmount = toDollars(amount);
    	String transactionType = typeofTransaction(amount);
    	return "  " + transactionType + " " + transactionAmount + "\n";
    }
    
    
    private String typeofTransaction(double amount){
    	if (amount < 0){
    		return "withdrawal";
    	}
    	else{
    		return "deposit";
    	}
    }
    
    
    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}
