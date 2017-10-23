package com.abc.interest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.abc.Transaction;

public class SavingsAccountInterest implements InterestScheme {

	public static final BigDecimal BASE_INTEREST = new BigDecimal("0.001");
	private static final BigDecimal BASE_INTEREST_MAX;
	public static final BigDecimal THRESHOLD_INTEREST = new BigDecimal("0.002");
	public static final BigDecimal THRESHOLD = new BigDecimal("1000");
	
	static {
		BASE_INTEREST_MAX = THRESHOLD.multiply(BASE_INTEREST);
	}
	
	private static SavingsAccountInterest instance;
	
	public static synchronized SavingsAccountInterest getInstance() {
		if(instance == null) {
			instance = new SavingsAccountInterest();
		}
		return instance;
	}
	
	private SavingsAccountInterest() {};
	
	public BigDecimal getInterest(List<Transaction> transactions) {
		BigDecimal result = BigDecimal.ZERO;
		
		if(transactions != null && transactions.size() > 0) {
			for(Transaction t : transactions) {
				result = result.add(t.getExactAmount());
			}
			
			//Check if less than or equal the threshold.
			if(result.compareTo(THRESHOLD) <= 0) {
				result = result.multiply(BASE_INTEREST);
			} else {
				result = result.subtract(THRESHOLD);
				result = result.multiply(THRESHOLD_INTEREST).add(BASE_INTEREST_MAX);
			}
		}
		
		return result;
	}
	
	public static void main(String[] args) {
		ArrayList<Transaction> a = new ArrayList<>();
		a.add(new Transaction(2000));
		System.out.println(getInstance().getInterest(a));
	}
}
