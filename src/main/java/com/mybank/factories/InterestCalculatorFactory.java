package com.mybank.factories;

import com.mybank.calculators.*;
import com.mybank.entities.Account;
import com.mybank.exceptions.UndefinedAccountTypeException;

public class InterestCalculatorFactory {

	
	public static InterestCalculator getAccountCalculator(Account account) throws UndefinedAccountTypeException{
		
		switch(account.getAccountType()){
		case Account.CHECKING:
			return new CheckingAccountCalculator();
		case Account.SAVINGS:
			return new SavingsAccountCalculator();
		case Account.MAXI_SAVINGS:
			return new MaxiSavingsAccountCalculator();
		default:
			throw new UndefinedAccountTypeException();
		}
	
	}
}
