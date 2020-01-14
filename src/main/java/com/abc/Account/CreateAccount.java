package com.abc.Account;

public class CreateAccount {

	public static Account createAccount(AccountType accType) {
		Account account = null;
		if (accType.toString().equals("CHECKING")) {
			account = new CheckingAccount();
		} else if (accType.toString().equals("SAVINGS")) {
			account = new SavingsAccount();
		} else if (accType.toString().equals("MAXI_SAVINGS")) {
			account = new MaxiSavingsAccount();
		}
		return account;
	}

}
