package com.abc.interest;

import com.abc.Account;

/**
 * Factory class for creating InterestSchemes
 * @author Christopher J. Smith
 */
public class InterestSchemeFactory {
	
	/**
	 * Factory method that selects a InterestScheme based on an account type.
	 * @param account is the account type. If null or invalid, checking is assumed.
	 * @return Returns the InterestScheme for the given account type.
	 */
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
