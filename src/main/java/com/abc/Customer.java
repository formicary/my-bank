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

    public int openAccount(Account account) {
        accounts.add(account);
        return accounts.indexOf(account); // changed to return index of new account, intended for use in tandem with transferBetweenAccounts
        								  // alternative values include: unique account ID, account type (if only 1 of each account type allowed)
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    
    //account type, account itself, account location
    public void transferBetweenAccounts(double amount, int withdrawAcc, int depositAcc){
    	if(withdrawAcc < 0 || withdrawAcc >= accounts.size() 
    			|| depositAcc < 0 || depositAcc >= accounts.size()){
    		throw  new IndexOutOfBoundsException("Incorrect account ID given");
    	}
    	if(amount > accounts.get(withdrawAcc).amount){
    	    System.out.println("Transfer failed: not enough money in account for a transfer.");
    	    return;
    	}
    	accounts.get(withdrawAcc).withdraw(amount);
    	accounts.get(depositAcc).deposit(amount);
    }
    
    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            total += a.totalInterest;
        return total;
    }

    public String getStatement() {
        String statement = null;
        statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (Account a : accounts) {
            statement += "\n" + statementForAccount(a) + "\n";
            total += a.amount;
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
        for (Transaction t : a.transactions) {
            s += "  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount) + "\n";
        }
        s += "Interest earned " + toDollars(a.totalInterest) + "\n";
        s += "Total " + toDollars(a.amount);
        return s;
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }

}
