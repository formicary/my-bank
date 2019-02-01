package com.abc;

/**
 * Class which represents the customers of the bank.
 * 
 * @author Daniel Seymour
 * @version 1.0
 */
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Customer {
    private String name;
    private List<Account> accounts;

    /**
     * Constructs a List of Account objects with a dynamic type of an ArrayList of 
     * Account objects. Represents the bank account held by the customer.
     * 
     * @param name String representation of the customer's name.
     */
    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    /**
     * Accessor for the name of the customer for whom its called.
     * @return String representation of the customer's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Adds an object of type Account to the List instantiated in the constructor.
     * @param account The account to be added to the list.
     * @return The list containing the newly added account.
     */
    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    /**
     * Accessor for the number of accounts in the List which calls it.
     * @return The size of the list that calls it.
     */
    public int getNumberOfAccounts() {
        return accounts.size();
    }
    
    /**
     * This method can be called to transfer a specified amount from one account to another.
     * @param amount The sum to be transferred.
     * @param accountFrom The account to transfer the money from.
     * @param accountTo The account to transfer the money to.
     */
    public void transferSum(double amount, Account accountFrom, Account accountTo) {
    	if(!checkIfAccountsExist(accountFrom, accountTo)) { // calls this method to check that the customer owns the types of accounts needed.
    		throw new IllegalArgumentException("Must own both account types");
    	}
    	else {
    		accountFrom.withdraw(amount); // withdraws the amount argument from the first account.
    		accountTo.deposit(amount); // deposits the amount argument in the second account.
    	}
    }
    
    /**
     * Checks if the customer who calls the method owns the accounts supplied as arguments.
     * @param accountA Account of a certain subtype.
     * @param accountB Account of another subtype.
     * @return boolean representing whether this is the case.
     */
    public boolean checkIfAccountsExist(Account accountA, Account accountB) {
    	for (Account a : this.accounts) {
    		if (a.getAccountType() == accountA.getAccountType()) {
    			for (Account b : this.accounts) {
    				if (b.getAccountType() == accountB.getAccountType()) {
    					return true;
    				}
    			}
    		}
    	}
    	return false;
    }

    /**
     * Scans each account owned by the customer object that calls it and sums their interest.
     * @return double representing the sum of the interest accrued by each account.
     */
    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            total += a.interestEarned();
        return total;
    }

    /**
     * Concatenates String representations of of each account the customer has.
     * @return String representation of the customer's statement detailing each account.
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
     * Converts the integer representing the account type into a String.
     * 
     * @param a The account to be converted.
     * @return String representation of the account's type.
     */
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
     * Converts a double into a String representation to two decimal places preceded by a dollar sign.
     * @param d The double to be converted.
     * @return String representation of the double as dollars and cents.
     */
    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}
