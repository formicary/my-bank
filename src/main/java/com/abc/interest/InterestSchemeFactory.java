package com.abc.interest;

import com.abc.Account;

public class InterestSchemeFactory {
	public static InterestScheme getScheme(Account.AccountType account) {
		InterestScheme result = null;
		
		switch(account) {
		case SAVINGS:
			result = SavingsAccountInterest.getInstance();
			break;
		case MAXI_SAVINGS:
			result = MaxiSavingsAccountInterest.getInstance();
			break;
		default:
			result = CheckingAccountInterest.getInstance();
		}
		
		return result;
	}
}
