package com.abc.interest;

import java.math.BigDecimal;
import java.util.List;

import com.abc.Transaction;

/**
 * Interface for a interest scheme to calculate interest.
 * @author Chirstopher J. Smith
 */
public interface InterestScheme {

	/**
	 * Get interest for a given list of transactions.
	 * @param transactions is the set of transactions to calculate interest for.
	 * @return Returns the interest owed to that list of transactions.
	 */
	public BigDecimal getInterest(List<Transaction> transactions);

}