package com.abc.calculators;

import java.math.BigDecimal;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.abc.model.Account;
import com.abc.model.Money;

@Component("savingAccountIntrestCalculator")
public class SavingAccountIntrestCalculator implements IntrestCalculator {

	private final static BigDecimal FIRST_LEVEL_RATE = new BigDecimal("0.1");
	private final static BigDecimal SECOND_LEVEL_RATE = new BigDecimal("0.2");
	private final static BigDecimal FIRST_LEVEL_THREASHOLD = new BigDecimal("1000");
	
	
	
	public BigDecimal calculateInterestEarned(Account account) {
		Objects.requireNonNull(account);
		Money balance = account.getBalance();
		
		if(balance.getAmount().compareTo(FIRST_LEVEL_THREASHOLD) < 1){
			return balance.getAmount().multiply(FIRST_LEVEL_RATE);
		}
		
		BigDecimal inrest = FIRST_LEVEL_THREASHOLD.multiply(FIRST_LEVEL_RATE);
		return balance.getAmount()
				.subtract(FIRST_LEVEL_THREASHOLD)
				.multiply(SECOND_LEVEL_RATE)
				.add(inrest);
		
	}

}
