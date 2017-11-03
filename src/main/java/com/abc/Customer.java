package com.abc;

import java.util.ArrayList;
import java.util.List;
import java.text.NumberFormat;
import java.util.Locale;

public class Customer {
    private String fullName;
    private List<Account> accounts;
    private Locale locale;

    //customer constructor
    public Customer(String fullName, Locale locale) {
        this.fullName = fullName;
        this.accounts = new ArrayList<Account>();
        this.locale = locale;
    }
    
    //getter methods
    public String getFullName() {
        return fullName;
    }
    
    public Locale getLocale() {
    	return locale;
    }
    
    public int getNumberOfAccounts() {
        return accounts.size();
    }

    //setter method for change of name (e.g. customer gets married and last name changes)
    public void setFullName(String fullName) {
    	this.fullName = fullName;
    }
    //setter method for change of region (locale)
    public void setLocale(Locale locale) {
    	this.locale = locale;
    }
    
    //open a new account method
    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    //'counter' for all interest earned across all accounts
    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            total += a.interestEarned();
        return total;
    }

    //returns summary of ALL accounts
    public String getStatement() {
        String statement = "=== Statement for " + fullName + " ===\n";
        double total = 0.0;
        for (Account a : accounts) {
            statement += "\n" + statementForAccount(a) + "\n";
            total += a.sumTransactions();
        }
        statement += "\n=== Total In All Accounts: " + NumberFormat.getCurrencyInstance(locale).format(total) + "\n"; //put currencies in required currency (based on locale)
        
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
            default:
            	s += "Oops! That's not a valid Account Type.";
            	break;
        }

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : a.transactions) {
            s += "\t" + (t.amount < 0 ? "Withdrawal" : "Deposit") + ": " + NumberFormat.getCurrencyInstance(locale).format(Math.abs(t.amount)) + "\n";
            total += t.amount;
        }
        s += "Total: " + NumberFormat.getCurrencyInstance(locale).format(total);
        return s;
    }
    
}
