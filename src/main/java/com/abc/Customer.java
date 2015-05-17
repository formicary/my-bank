package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class holds an abstraction of a retail bank customer.
 * @author Donald Campbell (campbell.donald@gmail.com)
 *
 */
public class Customer {
    private String name;
    private int uniqueIDNumber;
    private List<Account> accounts;

    /**
     * Constructor (standard), accepting a name a unique ID.
     * @param name The customer's human-readable name.
     * @param uniqueIDNumber The customer's unique ID number.
     */
    public Customer(String name, int uniqueIDNumber) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
        this.uniqueIDNumber = uniqueIDNumber;
    }

    /**
     * Getter method for the customer's name.
     * @return The customer's human-readable name.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Getter method for the customer's unique ID.
     * @return The customer's unique ID.
     */
    public int getUniqueIDNumber() {
    	return uniqueIDNumber;
    }

    /**
     * Opens a new account of a given type.
     * @param accountType The account type to open.
     */
    public void openAccount(int accountType) {
        Account newAccount = new Account(accountType);
        accounts.add(newAccount);
    }
    
    /**
     * Opens a new account, with a settable opening date.
     * @param accountType The account type to open.
     * @param openingDate The date to use as the account's opening date.
     */
    public void openAccount(int accountType, Date openingDate) {
    	Account newAccount = new Account(accountType, openingDate);
    	accounts.add(newAccount);
    }
    
    /**
     * Returns the number of accounts the user currently owns.
     * @return The number of accounts the user owns.
     */
    public int getNumberOfAccounts() {
        return accounts.size();
    }

    /**
     * Returns the total amount of interest (across all accounts) the user has earned at the bank, as of the current system date..
     * @return The total amount of interest (across all accounts) the user has earned at the bank.
     */
    public double totalInterestEarned() {
    	return totalInterestEarned(null);
    } 
    
    /**
     * Returns the total amount of interest (across all accounts) the user has earned at the bank, as of a given reference date.
     * @param asOf The reference date from which to calculate the total amount of interest earned. 
     * @return The total amount of interest (across all accounts) the user has earned as of the given reference date.
     */
    public double totalInterestEarned(Date asOf) {
        double total = 0.0;
        
        for (Account a : accounts) {
            total += a.calculateInterest(asOf);
        } // for
        
        return total;
    }

    /** 
     * Returns a statement for the user on all his/her accounts.
     * @return A string representing a statement of the user's accounts.
     */
    public String getStatement() {
        String statement = null;
        statement = "Statement for " + name + "\n";
        double total = 0.0;
        
        for (Account currAccount : accounts) {
            statement += "\n" + statementForAccount(currAccount) + "\n";
            total += currAccount.getBalance();
        }
        
        statement += "\nTotal In All Accounts " + HelperFunctions.toDollars(total);
        
        return statement;
    }

    /**
     * Returns a statement for a specific account.
     * @param account The account to generate a statement for.
     * @return A string representing the statement for the account.
     */
    private String statementForAccount(Account account) {
        String outputString = "";
        double total = 0.0, interest;
        Transaction currTrans;
        
        switch(account.getAccountType()){
            case Account.CHECKING:
                outputString += "Checking Account\n";
                break;
            case Account.SAVINGS:
                outputString += "Savings Account\n";
                break;
            case Account.MAXI_SAVINGS:
                outputString += "Maxi Savings Account\n";
                break;
        }
        
        for (int transIndex = 0; transIndex < account.getNumberOfTransactions(); transIndex++) {
        	currTrans = account.getTransaction(transIndex);
        	
        	outputString += "  " + HelperFunctions.formatDate(currTrans.getTransactionDate()) + " " + (currTrans.getAmount() < 0 ? "withdrawal" : "deposit") + " " + HelperFunctions.toDollars(currTrans.getAmount()) + "\n";
            total += currTrans.getAmount();
        }
        
        interest = account.calculateInterest();
        total += interest;
        
        outputString += "Interest " + HelperFunctions.toDollars(account.calculateInterest()) + "\n";
        outputString += "Total " + HelperFunctions.toDollars(total);
        
        return outputString;
    }
    
    /** 
     * Deposit money into an account.
     * @param accountIndex Account to deposit money into.
     * @param amount Amount to deposit.
     * @param date Date to use for the deposit transaction.
     */
    public void deposit(int accountIndex, double amount, Date date) {
    	Account account;
		
		if (accountIndex >= 0 && accountIndex < accounts.size()) {
			account = accounts.get(accountIndex);
		} else {
			throw new IllegalArgumentException ("invalid account index");
		} // else
		
    	if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            account.addTransaction(new Transaction(amount, date));
        }
    }
    
    /**
     * Deposit money, using current system date as the deposit date.
     * @param accountIndex Account to deposit money into.
     * @param amount Amount to deposit.
     */ 
    public void deposit(int accountIndex, double amount) {
    	deposit(accountIndex, amount, DateProvider.getInstance().now());
    }

    /**
     * Withdraw money from an account.
     * @param accountIndex Account to withdraw money from.
     * @param amount Amount to withdraw.
     * @param date Date to use as transaction date.
     */
	public void withdraw(int accountIndex, double amount, Date date) {
		double currentAmount;
		Account account;
		
		if (accountIndex >= 0 && accountIndex < accounts.size()) {
			account = accounts.get(accountIndex);
		} else {
			throw new IllegalArgumentException ("invalid account index");
		} // else
		
	    if (amount <= 0) {
	        throw new IllegalArgumentException("amount must be greater than zero");
	    } else {
	    	currentAmount = account.getBalance();
	    	
	    	if (currentAmount < amount) {
	    		throw new IllegalArgumentException("you do not have sufficient funds to withdraw this amount.");
	    	} // if
	    	
	        account.addTransaction(new Transaction(-amount, date));
	    }
	}
	
	/**
	 * Withdraw money from an account, using the current system date as transaction date.
	 * @param accountIndex Account to withdraw money from.
	 * @param amount Amount to withdraw.
	 */
	public void withdraw(int accountIndex, double amount) {
		withdraw(accountIndex, amount, DateProvider.getInstance().now());
	}
	
	/**
	 * Returns the account balance for a given account.
	 * @param account Account to get balance for.
	 * @return Returns the account balance for the given account.
	 */
	public double getAccountBalance (int account) {
		if (account >= 0 && account < accounts.size()) {
			return accounts.get(account).getBalance();
		} else {
			throw new IllegalArgumentException("invalid account index");
		} // else
	}
	
	/**
	 * Transfer money from one account to another.
	 * @param fromAccount Account to transfer money from.
	 * @param toAccount Account to transfer money to.
	 * @param amount Amount to transfer.
	 * @param date Date to use as transaction dates.
	 */
	public void transferMoney(int fromAccount, int toAccount, double amount, Date date) {
		withdraw(fromAccount, amount, date);
		deposit(toAccount, amount, date);
	}
	
	/**
	 * Transfer money from one account to another, using current system date as transaction dates.
	 * @param fromAccount Account to transfer money from.
	 * @param toAccount Account to transfer money to.
	 * @param amount Amount to transfer.
	 */
	public void transferMoney(int fromAccount, int toAccount, double amount) {
		withdraw(fromAccount, amount, DateProvider.getInstance().now());
		deposit(toAccount, amount, DateProvider.getInstance().now());
	}
}
