package com.abc.calculators;

import java.math.BigDecimal;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.abc.model.Account;
import com.abc.model.AccountType;
import com.abc.model.Money;

@Component("checkingAccountInterestCalculator")
public class CheckingAccountInterestCalculator implements IntrestCalculator {
	
	private final static BigDecimal PERCENTAGE_RATE = new BigDecimal("0.001");
	
	
	/**
	 * Calculate interests for {@link AccountType#CHECKING}
	 * 
	 *  Checking accounts have a flat rate of 0.1%
	 *  
	 * @param account
	 * @throws NullPointerException in case is account null
	 */
	public Money calculateInterestEarned(final Account account) {
		Objects.requireNonNull(account);
		final Money balance = account.getBalance();
		return new Money(  balance.getAmount().multiply(PERCENTAGE_RATE) );
	}

}
