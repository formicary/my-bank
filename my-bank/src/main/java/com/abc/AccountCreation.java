package com.abc;

import com.abc.Account.AccountType;

public class AccountCreation {

	public static Account create(Customer customer, AccountType accountType) {
		switch (accountType) {
		case CHECKING:
			return new CheckingAccount(customer, accountType);
		case SAVINGS:
			return new SavingsAccount(customer, accountType);
		case MAXI_SAVINGS:
			return new MaxiSavingsAccount(customer, accountType);
		}
		throw new IllegalArgumentException("Invalid account type.");
	}

}
