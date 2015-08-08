package com.abc.calculators;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.abc.model.Account;
import com.abc.model.Money;

@Component("checkingAccountInterestCalculator")
public class CheckingAccountInterestCalculator implements IntrestCalculator {
	
	private final static BigDecimal RATE = new BigDecimal("0.1");
	
	
	public BigDecimal calculateInterestEarned(final Account account) {
		final Money balance = account.getBalance();
		return balance.getAmount().multiply(RATE);
	}

}
