package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Customer {
    private String name;
    private List<Account> accounts;

    //Customer Constructor
    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    //Return Customer Name
    public String getName() {
        return name;
    }

    //Add Account to Customer
    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }
    
    //Return Number of Accounts
    public int getNumberOfAccounts() {
        return accounts.size();
    }
    
    //Transfer Money between Two Accounts
    public void accountTransfer(Double amount, Account fromAccount, Account toAccount) {
    	try {
	    	fromAccount.withdraw(amount);
	    	toAccount.deposit(amount);
    	}
    	catch(IllegalArgumentException e) {
    		System.out.println("Amount must be greater than zero");
    	}
    }

    //Return total amount of interested earned on all accounts
    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            total += a.totalInterest();
        return total;
    }

    //Return a statement for all the customer's accounts as String
    public String getStatement() {
        String statement = null;
        statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (Account account : accounts) {
            statement += "\n" + statementForAccount(account) + "\n";
            total += (account.sumTransactions() + account.totalInterest());
        }
        statement += "\nTotal In All Accounts " + StatementUtil.toDollars(total, true);
        return statement;
    }

    //Return a statement for a given account 
    private String statementForAccount(Account account) {
        String statement = "";

       //Translate to pretty account type
        switch(account.getAccountType()){
            case CHECKING:
            	statement += "Checking Account\n";
                break;
            case SAVINGS:
            	statement += "Savings Account\n";
                break;
            case MAXI_SAVINGS:
            	statement += "Maxi Savings Account\n";
                break;
        }

        //Total all the transactions
        double total = account.sumTransactions() + account.totalInterest();
        statement += account.getAccountStatement();
        statement += "Total " + StatementUtil.toDollars(total, true);
        return statement;
    }
}
