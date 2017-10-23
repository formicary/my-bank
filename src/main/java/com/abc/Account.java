package com.abc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.abc.interest.InterestScheme;
import com.abc.interest.InterestSchemeFactory;

/**
 * Account class to represent various types of banking accounts.
 * @author Christopher J. Smith
 */
public class Account {
	
	/**
	 * Enumeration of the possible account types
	 */
	public enum AccountType {
		CHECKING, SAVINGS, MAXI_SAVINGS
	}

	//Percentage multiplier representing a single day in a year. Rounded down to 4 decimal places.
	private static final BigDecimal DAY_MULTIPLIER = 
			BigDecimal.ONE.divide(new BigDecimal(365), 4, BigDecimal.ROUND_DOWN);
	
	//Object State variables
	private final AccountType ACCOUNT_TYPE;
	private final InterestScheme INTEREST;
	private final List<Transaction> TRANSACTIONS;
	//Buffer for sum of transactions
	private BigDecimal totalTransactions;

	/**
	 * Create an instance of an Account given a type.
	 * @param accountType is the enumerator of the account type being created.
	 */
	public Account(AccountType accountType) {
		ACCOUNT_TYPE = accountType;
		INTEREST = InterestSchemeFactory.getScheme(ACCOUNT_TYPE);
		
		//Ensure that elements can be added concurrently.
		TRANSACTIONS = Collections.synchronizedList(new ArrayList<Transaction>());
		
		totalTransactions = BigDecimal.ZERO;
	}

	/**
	 * Deposit a given amount into account.
	 * @param amount is the amount to be deposited.
	 * @throws IllegalArgumentException if the amount isn't greater than zero.
	 */
	public void deposit(BigDecimal amount) {
		//Check amount is more than 0
		if(amount.compareTo(BigDecimal.ZERO) > 0) {
			TRANSACTIONS.add(new Transaction(amount));
			updateTotal();
		} else {
			throw new IllegalArgumentException("amount must be greater than zero.");
		}
	}
	
	/**
	 * Deposit a given amount into account.
	 * @param amount is the amount to be deposited.
	 * @throws IllegalArgumentException if the amount isn't greater than zero.
	 */
	public void deposit(int amount) {deposit(new BigDecimal(amount));}
	/**
	 * Deposit a given amount into account.
	 * @param amount is the amount to be deposited.
	 * @throws IllegalArgumentException if the amount isn't greater than zero.
	 */
	public void deposit(short amount) {deposit(new BigDecimal(amount));}
	/**
	 * Deposit a given amount into account.
	 * @param amount is the amount to be deposited.
	 * @throws IllegalArgumentException if the amount isn't greater than zero.
	 */
	public void deposit(long amount) {deposit(new BigDecimal(amount));}
	/**
	 * Deposit a given amount into account.
	 * @param amount is the amount to be deposited.
	 * @throws IllegalArgumentException if the amount isn't greater than zero.
	 */
	public void deposit(double amount) {deposit(new BigDecimal(amount));}
	/**
	 * Deposit a given amount into account.
	 * @param amount is the amount to be deposited.
	 * @throws IllegalArgumentException if the amount isn't greater than zero.
	 */
	public void deposit(float amount) {deposit(new BigDecimal(amount));}

	/**
	 * Withdraw a given amount into account.
	 * @param amount is the amount to be withdrawn.
	 * @throws IllegalArgumentException if the amount isn't greater than zero.
	 */
	public void withdraw(BigDecimal amount) {
		//Check amount is more than 0
		if(amount.compareTo(BigDecimal.ZERO) > 0) {
			TRANSACTIONS.add(new Transaction(amount.negate()));
			updateTotal();
		} else {
			throw new IllegalArgumentException("amount must be greater than zero.");
		}
	}
	
	/**
	 * Withdraw a given amount into account.
	 * @param amount is the amount to be withdrawn.
	 * @throws IllegalArgumentException if the amount isn't greater than zero.
	 */
	public void withdraw(int amount) {withdraw(new BigDecimal(amount));}
	/**
	 * Withdraw a given amount into account.
	 * @param amount is the amount to be withdrawn.
	 * @throws IllegalArgumentException if the amount isn't greater than zero.
	 */
	public void withdraw(short amount) {withdraw(new BigDecimal(amount));}
	/**
	 * Withdraw a given amount into account.
	 * @param amount is the amount to be withdrawn.
	 * @throws IllegalArgumentException if the amount isn't greater than zero.
	 */
	public void withdraw(long amount) {withdraw(new BigDecimal(amount));}
	/**
	 * Withdraw a given amount into account.
	 * @param amount is the amount to be withdrawn.
	 * @throws IllegalArgumentException if the amount isn't greater than zero.
	 */
	public void withdraw(double amount) {withdraw(new BigDecimal(amount));}
	/**
	 * Withdraw a given amount into account.
	 * @param amount is the amount to be withdrawn.
	 * @throws IllegalArgumentException if the amount isn't greater than zero.
	 */
	public void withdraw(float amount) {withdraw(new BigDecimal(amount));}
	
	/**
	 * Get the expected interest that will be earned annually.
	 * @return Returns the amount to be earned through interest.
	 */
	public BigDecimal interestEarned() {
		return INTEREST.getInterest(getTransactionsClone());
	}
	
	/**
	 * Get the expected interest that will be earned daily.
	 * @return Returns the amount to be earned through interest.
	 */
	public BigDecimal interestEarnedDaily() {
		BigDecimal interest = INTEREST.getInterest(getTransactionsClone());
		return interest.multiply(DAY_MULTIPLIER);
	}
	
	/**
	 * Get the total number of transactions a account has made.
	 * @return Returns the total number of transactions.
	 */
	public int getNumberTransactions() {
		return TRANSACTIONS.size();
	}
	
	/**
	 * Get a clone list of all the transactions an account has made.
	 * @return Returns a clone list of all the transactions made. The transaction references are the same.
	 */
	//Returns a clone so no outside objects can add to the collection.
	public List<Transaction> getTransactionsClone(){
		return new ArrayList<Transaction>(TRANSACTIONS);
	}
	
	/**
	 * Get a Itereator that iterates through all the accounts transactions. 
	 * @return Returns an Iterator of transactions.
	 */
	public Iterator<Transaction> getTransactionIterator(){
		return TRANSACTIONS.iterator();
	}

	/**
	 * Get the total sum of all account transactions.
	 * @return Returns the total sum of all account transactions.
	 */
	//Synchronised so that when a new total is being calculated a total isn't returned
	public synchronized BigDecimal sumTransactions() {		
		return totalTransactions;
	}
	
	/**
	 * Internal method to update the total account holdings buffer
	 */
	/* Doesn't just add last element as it is possible elements are being 
	 * added while this is calculating.
	 * Decided it's better deposits and withdraws happen concurrently with the 
	 * down side being total is calculated more often*/
	private synchronized void updateTotal() {
		BigDecimal result = BigDecimal.ZERO;
		
		if(transactionsExist()) {
			for(Transaction t: TRANSACTIONS) {
				result = result.add(t.getExactAmount());
			}
		}
		
		totalTransactions = result;
	}

	/**
	 * Checks that an account has made some transactions
	 * @return Returns true if transactions have ever been made, else returns false.
	 */
	public boolean transactionsExist() {
		return TRANSACTIONS.size() > 0;
	}

	/**
	 * Get the account type of the account.
	 * @return Returns the account type of the account.
	 */
	public AccountType getAccountType() {
		return ACCOUNT_TYPE;
	}

	/**
	 * Get a printable statement of the account.
	 * @return Returns a printable statement of the account.
	 */
	public String getStatement() {
		StringBuilder result = new StringBuilder();
		
		switch(ACCOUNT_TYPE) {
		case CHECKING:
			result.append("Checking Account\n");
			break;
		case SAVINGS:
			result.append("Savings Account\n");
			break;
		case MAXI_SAVINGS:
			result.append("Maxi Savings Account\n");
			break;
		}
		
		List<Transaction> list;
		BigDecimal total;
		synchronized(TRANSACTIONS){
			list = getTransactionsClone();
			total = sumTransactions();
		}
		
		for(Transaction t : list) {
			result.append("  ");
			
			//Check if transaction is a withdrawal or deposit
			if(t.getExactAmount().compareTo(BigDecimal.ZERO) < 0) {
				result.append("Withdrawal ");
				result.append(Transaction.toCurrecy(t.getAmount().negate())).append('\n');
			} else {
				result.append("Deposit ");
				result.append(Transaction.toCurrecy(t.getAmount())).append('\n');
			}
		}
		result.append("Total ").append(Transaction.toCurrecy(total));
		
		return result.toString();
	}
	
	@Override
	public String toString() {
		return "Holdings: " + Transaction.toCurrecy(sumTransactions()) + "  Transactions: " + getNumberTransactions(); 
	}
}
