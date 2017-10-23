package com.abc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.abc.interest.InterestScheme;
import com.abc.interest.InterestSchemeFactory;

public class Account {
	
	public enum AccountType {
		CHECKING, SAVINGS, MAXI_SAVINGS
	}

	private static final BigDecimal DAY_MULTIPLIER = 
			BigDecimal.ONE.divide(new BigDecimal(365), 4, BigDecimal.ROUND_DOWN);
	
	private final AccountType ACCOUNT_TYPE;
	private final InterestScheme INTEREST;
	private final List<Transaction> TRANSACTIONS;
	
	private BigDecimal totalTransactions;

	public Account(AccountType accountType) {
		ACCOUNT_TYPE = accountType;
		INTEREST = InterestSchemeFactory.getScheme(ACCOUNT_TYPE);
		TRANSACTIONS = Collections.synchronizedList(new ArrayList<Transaction>());
		totalTransactions = BigDecimal.ZERO;
	}

	public void deposit(BigDecimal amount) {
		if(amount.compareTo(BigDecimal.ZERO) > 0) {
			TRANSACTIONS.add(new Transaction(amount));
			updateTotal();
		} else {
			throw new IllegalArgumentException("amount must be greater than zero.");
		}
	}
	
	public void deposit(int amount) {deposit(new BigDecimal(amount));}
	public void deposit(short amount) {deposit(new BigDecimal(amount));}
	public void deposit(long amount) {deposit(new BigDecimal(amount));}
	public void deposit(double amount) {deposit(new BigDecimal(amount));}
	public void deposit(float amount) {deposit(new BigDecimal(amount));}

	public void withdraw(BigDecimal amount) {
		if(amount.compareTo(BigDecimal.ZERO) > 0) {
			TRANSACTIONS.add(new Transaction(amount.negate()));
			updateTotal();
		} else {
			throw new IllegalArgumentException("amount must be greater than zero.");
		}
	}
	
	public void withdraw(int amount) {withdraw(new BigDecimal(amount));}
	public void withdraw(short amount) {withdraw(new BigDecimal(amount));}
	public void withdraw(long amount) {withdraw(new BigDecimal(amount));}
	public void withdraw(double amount) {withdraw(new BigDecimal(amount));}
	public void withdraw(float amount) {withdraw(new BigDecimal(amount));}
	

	public BigDecimal interestEarned() {
		return INTEREST.getInterest(getTransactionsClone());
	}
	
	public BigDecimal interestEarnedDaily() {
		BigDecimal interest = INTEREST.getInterest(getTransactionsClone());
		return interest.multiply(DAY_MULTIPLIER);
	}
	
	public int getNumberTransactions() {
		return TRANSACTIONS.size();
	}
	
	//Returns a clone so no outside objects can add to the collection.
	public List<Transaction> getTransactionsClone(){
		return new ArrayList<Transaction>(TRANSACTIONS);
	}
	
	public Iterator<Transaction> getTransactionIterator(){
		return TRANSACTIONS.iterator();
	}

	//Synchronised so that when a new total is being calculated a total isn't returned
	public synchronized BigDecimal sumTransactions() {		
		return totalTransactions;
	}
	
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

	public boolean transactionsExist() {
		return TRANSACTIONS.size() > 0;
	}

	public AccountType getAccountType() {
		return ACCOUNT_TYPE;
	}

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
