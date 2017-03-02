package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Customer {
    private String name;
    private List<Account> accounts;

    /**
     * Constructor for the class
     * @param name
     */
    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    /**
     * Function to open an account
     * @param account
     * @return the opened account
     */
	public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

	/**
	 * Function to find number of accounts
	 * @return size of accounts
	 */
    public int getNumberOfAccounts() {
        return accounts.size();
    }

    /**
     * Function to calculate total interest earned
     * @return interest earned
     */
    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            total += a.interestEarned();
        return total;
    }

    /**
     * Function to get account statement for all the accounts
     * @return statement of all accounts
     */
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

    /**
     * Function to generate a statement of account based on account type.
     * @param account
     * @return statement of account based on account type
     */
    public String statementForAccount(Account account) {
        String message = "";

       //Translate to pretty account type
        switch(account.getAccountType()){
            case Account.CHECKING:
            	message += "Checking Account\n";
                break;
            case Account.SAVINGS:
            	message += "Savings Account\n";
                break;
            case Account.MAXI_SAVINGS:
            	message += "Maxi Savings Account\n";
                break;
        }

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : account.transactions) {
        	message += "  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount) + "\n";
            total += t.amount;
        }
        message += "Total " + toDollars(total);
        return message;
    }

    /**
     * Function to add $ to the amount
     * @param d
     * @return amount in string format
     */
    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
    
    /**
     * Function to validate if the account being accessed is valid
     * @param accountNumber
     * @return validity of account
     */
    public Account isAccountValid(int accountNumber){
    	
    	for(Account a: accounts){
    		if(a.getAccountNumber() == accountNumber){
    			return a;
    		}
    	}
    	
    	return null;
    }
    
    // Getter & Setter Methods
    
    public String getName() {
        return name;
    }
    
    public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
}
