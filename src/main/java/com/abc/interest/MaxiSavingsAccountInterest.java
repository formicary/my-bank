package com.abc.interest;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.abc.DateProvider;
import com.abc.Transaction;

/**
 * A singleton class used to calculate interest for a maxi savings account.
 * @author CJSm
 *
 */
public class MaxiSavingsAccountInterest implements InterestScheme {

	//Constants for interest on the first 1000.
	public static final BigDecimal BASE_INTEREST = new BigDecimal("0.02");
	private static final BigDecimal BASE_INTEREST_MAX;
	
	//Constants for interest on the second 1000.
	public static final BigDecimal THRESHOLD1_INTEREST = new BigDecimal("0.05");
	public static final BigDecimal THRESHOLD1 = new BigDecimal("1000");
	private static final BigDecimal THRESHOLD1_INTEREST_MAX;
	
	//Constants for interest on any remaining currency.
	public static final BigDecimal THRESHOLD2_INTEREST = new BigDecimal("0.10");
	public static final BigDecimal THRESHOLD2 = new BigDecimal("1000");
	
	//Penalty interest earned when account has been withdrawn from in the last 10 days.
	public static final BigDecimal PENALTY_INTEREST = new BigDecimal("0.001");
	public static final int PENALTY_DAYS = 10;
	
	//Calculate the max interest for first two thresholds to avoid recalculating.
	static {
		BASE_INTEREST_MAX = THRESHOLD1.multiply(BASE_INTEREST);
		THRESHOLD1_INTEREST_MAX = THRESHOLD2.multiply(THRESHOLD1_INTEREST);
	}
	
	//Singleton reference
	private static MaxiSavingsAccountInterest instance;
	
	 /**
     * Get the singleton instance. This is synchronized so should avoid over calling and store a reference where possible.
     * @return Returns the singleton reference.
     */
    //Synchronized to ensure that one call cannot overwrite another call when creating an instance.
	public static synchronized MaxiSavingsAccountInterest getInstance() {
		if(instance == null) {
			instance = new MaxiSavingsAccountInterest();
		}
		return instance;
	}
	
	//Private constructor to ensure is singleton.
	private MaxiSavingsAccountInterest() {};
	
	/**
	 * Get the interest for a given list of transactions. 
	 * Interest is calculated in bands, the first 1000, the next 1000, and anything above that.
	 * If a withdrawal has been mad in the last 10 days then a constant penalty rate will be used.
	 * @param transactions is a list of transactions you want to calculate interest for.
	 * @return The amount to interest for the given transactions at the set rate.
	 */
	public BigDecimal getInterest(List<Transaction> transactions) {
		BigDecimal result = BigDecimal.ZERO;
		
		//If transactions are valid, sum the total transactions.
		//Check if there is a penalty then calulcate using the given rate.
		if(transactions != null && transactions.size() > 0) {
			for(Transaction t : transactions) {
				result = result.add(t.getExactAmount());
			}
			
			if(checkPenalty(transactions)) {
				result = penaltyInterest(result);
			} else {
				result = standardInterest(result);
			}
		}
		
		return result;
	}
	
	/**
	 * Interest calculation when a penalty is not in effect.
	 * @param total is the amount to apply interest to. If null zero is assumed.
	 * @return Returns the amount of interest owed to the total.
	 */
	private BigDecimal standardInterest(BigDecimal total) {	
		BigDecimal result = total != null ? total : BigDecimal.ZERO;
		
		//Check if total is less than first threshold
		if(result.compareTo(THRESHOLD1) <= 0) {
			//Calculate initial base interest.
			result = result.multiply(BASE_INTEREST);
		} else {	//Total is more than base threshold
			//Remove base threshold from total.
			result = result.subtract(THRESHOLD1);
			//Check if total is less than second threshold
			if(result.compareTo(THRESHOLD2) <= 0) {
				//Calculate interest upto second threshold.
				result = result.multiply(THRESHOLD1_INTEREST)
						.add(BASE_INTEREST_MAX);
			} else {	//Total is more than secondary threshold
				//Calculate all interest together
				result = result.subtract(THRESHOLD1);
				result = result.multiply(THRESHOLD2_INTEREST)
						.add(THRESHOLD1_INTEREST_MAX)
						.add(BASE_INTEREST_MAX);
			}
		}
		
		return result;
	}
	
	/**
	 * Interest calculation when a penalty is in effect.
	 * @param total is the amount to apply interest to. If null zero is assumed.
	 * @return Returns the amount of interest owed to the total.
	 */
	private BigDecimal penaltyInterest(BigDecimal total) {
		if(total == null) {
			return BigDecimal.ZERO;
		} else {
			//Calculate interest at the penalty rate.
			return total.multiply(PENALTY_INTEREST);
		}
	}
	
	/**
	 * Check if there has been a withdrawal in the past 10 days.
	 * @param transactions is the list of transactions to check.
	 * @return Returns true if there has been a transaction in the past 10 days, else false.
	 */
	private boolean checkPenalty(List<Transaction> transactions) {
		boolean result = false;
		
		//Calculate time 10 days ago
		long penaltyPeriod = DateProvider.getInstance().now().getTime() - 
				TimeUnit.DAYS.toMillis(PENALTY_DAYS);
		
		//Fore each transaction check they are not within the penalty period.
		for(Transaction t : transactions) {
			if(t.getAmount().compareTo(BigDecimal.ZERO) < 0 &&
					t.getDate().getTime() >= penaltyPeriod) {
				result = true;
				break;
			}
		}
		
		return result;		
	}
		
}
