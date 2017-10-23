package com.abc.interest;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.abc.DateProvider;
import com.abc.Transaction;

public class MaxiSavingsAccountInterest implements InterestScheme {

	public static final BigDecimal BASE_INTEREST = new BigDecimal("0.02");
	private static final BigDecimal BASE_INTEREST_MAX;
	public static final BigDecimal THRESHOLD1_INTEREST = new BigDecimal("0.05");
	public static final BigDecimal THRESHOLD1 = new BigDecimal("1000");
	private static final BigDecimal THRESHOLD1_INTEREST_MAX;
	public static final BigDecimal THRESHOLD2_INTEREST = new BigDecimal("0.10");
	public static final BigDecimal THRESHOLD2 = new BigDecimal("1000");
	public static final BigDecimal PENALTY_INTEREST = new BigDecimal("0.001");
	public static final int PENALTY_DAYS = 10;
	
	static {
		BASE_INTEREST_MAX = THRESHOLD1.multiply(BASE_INTEREST);
		THRESHOLD1_INTEREST_MAX = THRESHOLD2.multiply(THRESHOLD1_INTEREST);
	}
	
	private static MaxiSavingsAccountInterest instance;
	
	public static synchronized MaxiSavingsAccountInterest getInstance() {
		if(instance == null) {
			instance = new MaxiSavingsAccountInterest();
		}
		return instance;
	}
	
	private MaxiSavingsAccountInterest() {};
	
	public BigDecimal getInterest(List<Transaction> transactions) {
		BigDecimal result = BigDecimal.ZERO;
		
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
	
	private BigDecimal standardInterest(BigDecimal total) {	
		BigDecimal result = total;
		
		if(result.compareTo(THRESHOLD1) <= 0) {
			result = result.multiply(BASE_INTEREST);
		} else {
			result = result.subtract(THRESHOLD1);
			if(result.compareTo(THRESHOLD2) <= 0) {
				result = result.multiply(THRESHOLD1_INTEREST)
						.add(BASE_INTEREST_MAX);
			} else {
				result = result.subtract(THRESHOLD1);
				result = result.multiply(THRESHOLD2_INTEREST)
						.add(THRESHOLD1_INTEREST_MAX)
						.add(BASE_INTEREST_MAX);
			}
		}
		
		return result;
	}
	
	private BigDecimal penaltyInterest(BigDecimal total) {
		return total.multiply(PENALTY_INTEREST);
	}
	
	private boolean checkPenalty(List<Transaction> transactions) {
		boolean result = false;
		
		long penaltyPeriod = DateProvider.getInstance().now().getTime() - 
				TimeUnit.DAYS.toMillis(PENALTY_DAYS);
		
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
