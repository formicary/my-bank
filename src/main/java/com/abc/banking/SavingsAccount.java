package com.abc.banking;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.abc.banking.config.ApplicationConfig;

public final class SavingsAccount extends AbstractAccount {

	public final BigDecimal INTEREST_RATE_PER_ANNUM_FIRST_AMOUNT = new BigDecimal("0.01");
	public final BigDecimal INTEREST_RATE_PER_ANNUM_REST_AMOUNT = new BigDecimal("0.02");
	public final BigDecimal INTEREST_RATE_THRESHOLD = new BigDecimal("1000.00");
	
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
        					ApplicationConfig.monetaryDecimalPlacesAllowed*3, 
        					ApplicationConfig.monetaryRoundingMode);

        
        BigDecimal restInterest = 
        		balance
        			.subtract(INTEREST_RATE_THRESHOLD)
        			.max(BigDecimal.ZERO)
        			.multiply(INTEREST_RATE_PER_ANNUM_REST_AMOUNT)
        			.divide(new BigDecimal(day.lengthOfYear()), 
        					ApplicationConfig.monetaryDecimalPlacesAllowed*3, 
        					ApplicationConfig.monetaryRoundingMode);
        
		return firstInterest.add(restInterest)
				.setScale(ApplicationConfig.monetaryDecimalPlacesAllowed, 
        					ApplicationConfig.monetaryRoundingMode);
	}
}
