package com.abc;

import java.util.ArrayList;
import java.util.List;
/**
 * Abstract class for the three types of accounts. I chose this because all three account types
 * use the methods in this class, but the original implementation restricted the ways in which
 * each account type could be enhanced past the requirements of this exercise.
 * 
 * @author Daniel Seymour
 * @version 1.0
 *
 */
public abstract class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;
    /*
     * Access modifier changed from private to protected to be used by subclasses, final modifier 
     * removed for same reason.
     */
    protected int accountType;
    public List<Transaction> transactions;

    /**
     * Constructs an ArrayList object which will store the transactions
     * for the account.
     * 
     * "accountType" parameter and its instantiation removed (now in corresponding subclass).
     */
    public Account() {
        this.transactions = new ArrayList<Transaction>();
    }

    /**
     * Adds the amount given as an argument to the Transaction List, provided it's positive. 
     * 
     * @param amount
     * @throws IllegalArgumentException
     */
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    /**
     * Adds the amount given as an argument to the Transaction List, provided it's negative.
     * 
     * Changed:
     * Added exception for attempting to withdraw more than balance of the account. The specification did
     * not mention overdrafts, however this could be easily implemented if necessary.
     * 
     * @param amount
     * @throws IllegalArgumentException
     */
    public void withdraw(double amount) {
    	if (amount <= 0) {
    		throw new IllegalArgumentException("Amount must be greater than zero");
    	}
    	else if (amount > sumTransactions()) {
    		throw new IllegalArgumentException("Insufficient funds");
    	}
    	else {
    		transactions.add(new Transaction(- amount));
    	}
    }

    /**
     * Calls the "sumTransactions" method and returns its value. To be overridden by subclass methods.
     * @return double which is the sum of the deposits and withdrawals of the account.
     */
    public double interestEarned() {
        double amount = sumTransactions();
        return amount;
    }

    /**
     * Sums all values in Transaction List (if non-empty) and returns it, that is the account balance.
     * 
     * 
     * Moved summing code from "checkIfTransactionsExist" method and guarded with what's now returned
     * from it.
     * @return double The sum of the list of transactions
     */
    public double sumTransactions() {
    	double amount = 0.0;
    	if (checkIfTransactionsExist()) {
       		for (Transaction t: transactions) {
       			amount += t.amount;
       		}
    	}
        return amount;
    }

    /**
     * Returns true unless Transaction List is empty.
     * Essentially acts as exception handler when used in "sumTransactions" method.
     * 
     * Removed "checkAll" parameter and changed return type to boolean.
     * @return boolean Whether there is at least one object in the list of transactions.
     */
    public boolean checkIfTransactionsExist() {
        if (transactions.isEmpty()) {
        	return false;
        }
        return true;
    }

    /**
     * Accessor for the type of the account.
     * @return integer representing the account type according to the fields at the top of the class.
     */
    public int getAccountType() {
        return accountType;
    }

}
