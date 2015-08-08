package com.abc.calculators;

import java.math.BigDecimal;

import com.abc.model.Account;

public interface IntrestCalculator {

	BigDecimal calculateInterestEarned(Account account);
	
	
	
}
