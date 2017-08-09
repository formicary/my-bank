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

    private String statementForAccount(Account a) {
        String s = "";

       //Translate to pretty account type
        switch(a.getAccountType()){
            case CHECKING:
                s += "Checking Account\n";
                break;
            case SAVINGS:
                s += "Savings Account\n";
                break;
            case MAXI_SAVINGS:
                s += "Maxi Savings Account\n";
                break;
            default: throw new IllegalArgumentException("Invalid accoount type");
        }

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : a.transactions) {
        	
            s += "  " + (t.getType().toString().toLowerCase()) + " " + toDollars(t.getAmount()) + "\n";
            total += t.getAmount();
        }
        s += "Total " + toDollars(total);
        return s;
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
    
    public void transferBetweenAccounts(Account fromAccount, Account toAccount, double amount){
    	
    	if(!accounts.contains(fromAccount) || !accounts.contains(fromAccount)){
    		
    		throw new IllegalArgumentException("Both accounts should belong "
    				+ "to the customer requesting the transfer");
    		
    	} else if(amount>fromAccount.getCurrentBalance()){
    		
    		throw new IllegalArgumentException("Transfer invalid, "
    				+ "source account has only " + fromAccount.getCurrentBalance() + " left");
    		
    	} else{
    		
    		fromAccount.withdraw(amount);
    		toAccount.deposit(amount);
    		
    	}
    }
}
