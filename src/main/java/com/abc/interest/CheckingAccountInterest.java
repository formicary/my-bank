package com.abc.interest;

import java.math.BigDecimal;
import java.util.List;

import com.abc.Transaction;

/**
 * A singleton class used to calculate interest for a checking account.
 * @author Christopher J. Smith.
 */
public class CheckingAccountInterest implements InterestScheme {

	//Interest rate.
	public static final BigDecimal BASE_INTEREST = new BigDecimal("0.001");
	
	// Singleton reference.
	private static CheckingAccountInterest instance;
	
	 /**
     * Get the singleton instance. This is synchronized so should avoid over calling and store a reference where possible.
     * @return Returns the singleton reference.
     */
    //Synchronized to ensure that one call cannot overwrite another call when creating an instance.
	public static synchronized CheckingAccountInterest getInstance() {
		if(instance == null) {
			instance = new CheckingAccountInterest();
		}
		return instance;
	}
	
	//Private constructor to ensure is singleton.
	private CheckingAccountInterest() {};
	

	/**
	 * Get the interest for a given list of transactions. 
	 * Interest is applied a a constant rate.
	 * @param transactions is a list of transactions you want to calculate interest for.
	 * @return The amount to interest for the given transactions at the set rate.
	 */
	public BigDecimal getInterest(List<Transaction> transactions) {
		BigDecimal result = BigDecimal.ZERO;
		
		//If list is valid, sum up transactions then apply the interest rate.
		if(transactions != null && transactions.size() > 0) {
			for(Transaction t : transactions) {
				result = result.add(t.getExactAmount());
			}
			result = result.multiply(BASE_INTEREST);
		}
		
		return result;
	}
		
}
