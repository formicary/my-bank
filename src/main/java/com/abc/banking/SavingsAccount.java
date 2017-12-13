package com.abc.banking;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.abc.banking.config.ApplicationConfig;

public final class SavingsAccount extends AbstractAccount {

	private static final BigDecimal INTEREST_RATE_PER_ANNUM_FIRST_AMOUNT = new BigDecimal("0.01");
	private static final BigDecimal INTEREST_RATE_PER_ANNUM_REST_AMOUNT = new BigDecimal("0.02");
	private static final BigDecimal INTEREST_RATE_THRESHOLD = new BigDecimal("1000.00");
	
	protected SavingsAccount() {
		super();
	}

	public String getAccountTypeName() {
		return "Savings Account";
	}

	@Override
	public BigDecimal getDailyInterest(LocalDate day) {
		
		BigDecimal balance = getBalanceAt(day);

        BigDecimal firstInterest = 
        		balance
        			.min(INTEREST_RATE_THRESHOLD)
        			.multiply(INTEREST_RATE_PER_ANNUM_FIRST_AMOUNT)
        			.divide(new BigDecimal(day.lengthOfYear()), 
        					ApplicationConfig.MONETARY_DECIMAL_PLACES_ALLOWED*3, 
        					ApplicationConfig.MONETARY_ROUNDING_MODE);

        
        BigDecimal restInterest = 
        		balance
        			.subtract(INTEREST_RATE_THRESHOLD)
        			.max(BigDecimal.ZERO)
        			.multiply(INTEREST_RATE_PER_ANNUM_REST_AMOUNT)
        			.divide(new BigDecimal(day.lengthOfYear()), 
        					ApplicationConfig.MONETARY_DECIMAL_PLACES_ALLOWED*3, 
        					ApplicationConfig.MONETARY_ROUNDING_MODE);
        
		return firstInterest.add(restInterest)
				.setScale(ApplicationConfig.MONETARY_DECIMAL_PLACES_ALLOWED, 
        					ApplicationConfig.MONETARY_ROUNDING_MODE);
	}
}
