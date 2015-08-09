package com.abc.calculators;

import com.abc.model.Account;
import com.abc.model.Money;

public interface IntrestCalculator {

	Money calculateInterestEarned(Account account);
	
}
