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
    }
    
    /**
     * @param Account "accFrom" to transfer money from to Account "accTo" a double value of "amount"
     * @return void
     * Make sure the two objects are not the same, prevent transfer into the same account,
     * and user owns both accounts, as specification mentions,
     * before calling tranfer on "accFrom" with "accTo" as parameter
     **/
    public void transferTo(Account accFrom, Account accTo, double amount) {
        //check if accounts are unique
    	if(accFrom.equals(accTo)) {
    		throw new IllegalArgumentException("Accounts must be unique");
    	}
    	else {
    		//checking if user owns both accounts
        	boolean isAccFromValid = false;
        	boolean isAccToValid = false;
        	
        	for(Account a: accounts) {
        		if(accFrom.equals(a)) {
        			isAccFromValid = true;
        		}
        		else if(accTo.equals(a)) {
        			isAccToValid = true;
        		}
        	}
        	
        	if(!isAccFromValid || !isAccToValid) {
        		throw new IllegalArgumentException("User must own both accounts");
        	}
        	else {
        		accFrom.withdraw(amount);
        		accTo.deposit(amount);
        	}
        	
    	}
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}
