package com.abc.calculators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.abc.model.Account;

@Component
public class InterestCalculatorFactory {

	@Autowired
	@Qualifier("savingAccountIntrestCalculator")
	private IntrestCalculator savingAccountInterestCalculator;

	@Autowired
	@Qualifier("checkingAccountInterestCalculator")
	private IntrestCalculator checkingAccountInterestCalculator;

	@Autowired
	@Qualifier("maxiSavingAccountInterestCalculator")
	private IntrestCalculator maxiSavingAccountInterestCalculator;

	public IntrestCalculator getCalculator(final Account account) {

		switch (account.getAccountType()) {
		case SAVINGS:
			return savingAccountInterestCalculator;
		case CHECKING:
			return savingAccountInterestCalculator;
		case MAXI_SAVINGS:
			return savingAccountInterestCalculator;
		default:
			break;
		}
		throw new IllegalArgumentException("No interest calculator was found for account type: "
				+ account.getAccountType());
	}

}
