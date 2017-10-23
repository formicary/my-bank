package com.abc.interest;

import java.math.BigDecimal;
import java.util.List;

import com.abc.Transaction;

public class CheckingAccountInterest implements InterestScheme {

	public static final BigDecimal BASE_INTEREST = new BigDecimal("0.001");
	
	private static CheckingAccountInterest instance;
	
	public static synchronized CheckingAccountInterest getInstance() {
		if(instance == null) {
			instance = new CheckingAccountInterest();
		}
		return instance;
	}
	
	private CheckingAccountInterest() {};
	
	public BigDecimal getInterest(List<Transaction> transactions) {
		BigDecimal result = BigDecimal.ZERO;
		
		if(transactions != null && transactions.size() > 0) {
			for(Transaction t : transactions) {
				result = result.add(t.getExactAmount());
			}
			result = result.multiply(BASE_INTEREST);
		}
		
		return result;
	}
		
}
