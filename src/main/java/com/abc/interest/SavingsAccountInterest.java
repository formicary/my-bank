package com.abc.interest;

import java.math.BigDecimal;
import java.util.List;

import com.abc.Transaction;

/**
 * A singleton class used to calulate interes for a savings account.
 * @author Christopher J. Smith
 */
public class SavingsAccountInterest implements InterestScheme {

	//Constants for interest on first 1000
	public static final BigDecimal BASE_INTEREST = new BigDecimal("0.001");
	private static final BigDecimal BASE_INTEREST_MAX;
	
	//Constants for interest on rest of currency.
	public static final BigDecimal THRESHOLD_INTEREST = new BigDecimal("0.002");
	public static final BigDecimal THRESHOLD = new BigDecimal("1000");
	
	//Calculate the max interest for the initial threshold to avoid recalculating.
	static {
		BASE_INTEREST_MAX = THRESHOLD.multiply(BASE_INTEREST);
	}
	
	//Singleton reference.
	private static SavingsAccountInterest instance;
	
	/**
     * Get the singleton instance. This is synchronized so should avoid over calling and store a reference where possible.
     * @return Returns the singleton reference.
     */
    //Synchronized to ensure that one call cannot overwrite another call when creating an instance.
	public static synchronized SavingsAccountInterest getInstance() {
		if(instance == null) {
			instance = new SavingsAccountInterest();
		}
		return instance;
	}
	
	//Private constructor to ensure is singleton.
	private SavingsAccountInterest() {};
	
	/**
	 * Get the interest for a given list of transactions. 
	 * Interest is calculated in bands, the first 1000, and anything above that.
	 * @param transactions is a list of transactions you want to calculate interest for.
	 * @return The amount to interest for the given transactions at the set rate.
	 */
	public BigDecimal getInterest(List<Transaction> transactions) {
		BigDecimal result = BigDecimal.ZERO;
		
		//If valid transactions, sum the transactions.
		//Then calculate the interest off of that sum.
		if(transactions != null && transactions.size() > 0) {
			for(Transaction t : transactions) {
				result = result.add(t.getExactAmount());
			}
			
			//Check if within first threshold.
			if(result.compareTo(THRESHOLD) <= 0) {
				//Calculate interest based in first threshold.
				result = result.multiply(BASE_INTEREST);
			} else {	//Over first threshold
				//Apply first threshold then calculate interest.
				result = result.subtract(THRESHOLD);
				result = result.multiply(THRESHOLD_INTEREST).add(BASE_INTEREST_MAX);
			}
		}
		
		return result;
	}
}
