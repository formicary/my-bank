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
        
        for (Account account : accounts) {
            statement += "\n" + statementForAccount(account) + "\n";
            total += account.sumTransactions();
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
        
        return statement;
    }

    private String statementForAccount(Account account) {
        String statement = "";
        
        statement += getAccountTypeForStatement(account);
        statement += getTransactionsForStatement(account);
        
        return statement;
    }
    
    private String getAccountTypeForStatement(Account account) {
    	//Translate to readable account type
        switch(account.getAccountType()){
            case Account.CHECKING:
                return "Checking Account\n";
            case Account.SAVINGS:
                return "Savings Account\n";
            case Account.MAXI_SAVINGS:
                return "Maxi Savings Account\n";
        }
        //TODO - Handle error
        return null;
    }
    
    private String getTransactionsForStatement(Account account) {
    	String formattedTransactions = "";
        double total = 0.0;
        
        //Translate each transaction into a readable version
        for (Transaction transaction : account.transactions) {
            formattedTransactions += "  " + (transaction.amount < 0 ? "withdrawal" : "deposit") + " " + toDollars(transaction.amount) + "\n";
            total += transaction.amount;
        }        
        formattedTransactions += "Total " + toDollars(total);
        
        return formattedTransactions;
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}
