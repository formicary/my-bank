package com.abc;

public class AccountFactory {

	public static Account getAccount(AccountType type) {
		
		switch (type) {
		
			case CHECKING_ACCOUNT:
				return new CheckingAccount();
				
			case SAVINGS_ACCOUNT:
				return new SavingsAccount();
				
			case MAXI_SAVINGS_ACCOUNT:
				return new MaxiSavingsAccount();
				
			default:
				return null;
		}
	}
}
