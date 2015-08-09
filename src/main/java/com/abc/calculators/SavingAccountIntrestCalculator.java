package com.abc.calculators;

import java.math.BigDecimal;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.abc.model.Account;
import com.abc.model.AccountType;
import com.abc.model.Money;

@Component("savingAccountIntrestCalculator")
public class SavingAccountIntrestCalculator implements IntrestCalculator {

	private final static BigDecimal FIRST_LEVEL_PERCENTAGE_RATE = new BigDecimal("0.001");
	private final static BigDecimal SECOND_LEVEL_PERCENTAGE_RATE = new BigDecimal("0.002");
	private final static BigDecimal THOUSANDS = new BigDecimal("1000");
	
	
	/**
	 * Calculate interests for {@link AccountType#SAVINGS}
	 * 
	 *  Savings accounts have a rate of 0.1% for the first $1,000 then 0.2%
	 *  
	 * @param account
	 * @throws NullPointerException in case is account null
	 */
	public Money calculateInterestEarned(Account account) {
		Objects.requireNonNull(account);
		Money balance = account.getBalance();
		
		if(balance.getAmount().compareTo(THOUSANDS) < 1){
			return new Money( balance.getAmount().multiply(FIRST_LEVEL_PERCENTAGE_RATE));
		}
		
		BigDecimal firstLevelIntrest = THOUSANDS.multiply(FIRST_LEVEL_PERCENTAGE_RATE);
		BigDecimal secondLevelIntrest = balance.getAmount()
												.subtract(THOUSANDS)
												.multiply(SECOND_LEVEL_PERCENTAGE_RATE);
		
		return new Money( firstLevelIntrest.add(secondLevelIntrest) );
	}

}
