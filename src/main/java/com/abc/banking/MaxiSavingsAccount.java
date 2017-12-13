package com.abc.banking;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.abc.banking.config.ApplicationConfig;

public final class MaxiSavingsAccount extends AbstractAccount {

	private static final BigDecimal DEFAULT_INTEREST_RATE_PER_ANNUM = new BigDecimal("0.01");
	private static final BigDecimal SPECIAL_INTEREST_RATE_PER_ANNUM = new BigDecimal("0.05");
	private static final int DAYS_BALANCE_FOR_SPECIAL_INTEREST_RATE = 10;
	
	protected MaxiSavingsAccount() {
		super();
	}

	public String getAccountTypeName() {
		return "Maxi Savings Account";
	}

	@Override
	public BigDecimal getDailyInterest(LocalDate day) {
		
		BigDecimal balance = getBalanceAt(day);
		
		LocalDate startOfTestingPeriod = day.minusDays(DAYS_BALANCE_FOR_SPECIAL_INTEREST_RATE);
		
		// day parameter is regarded as one of 10 past days, as interest shall be accrued at the end of the day
		// start of testing period is not included
		boolean foundWithdrawals = 
			getTransactions()
			.stream()
			.filter(t -> t.getTransactionDate().isAfter(startOfTestingPeriod))
			.filter(t -> t.getTransactionDate().isBefore(day)
							|| t.getTransactionDate().equals(day))
			.anyMatch(transaction -> transaction.getAmount().compareTo(BigDecimal.ZERO) < 0);		
		
		BigDecimal interestRate = DEFAULT_INTEREST_RATE_PER_ANNUM;
		
		if(!foundWithdrawals)
			interestRate = SPECIAL_INTEREST_RATE_PER_ANNUM;
		
		 return balance
        			.multiply(interestRate)
        			.divide(new BigDecimal(day.lengthOfYear()), 
        					ApplicationConfig.MONETARY_DECIMAL_PLACES_ALLOWED, 
        					ApplicationConfig.MONETARY_ROUNDING_MODE);
	}
}
