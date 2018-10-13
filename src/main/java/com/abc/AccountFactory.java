package com.abc;

/**
 * 
 * @author Accenture, rrana
 * @version v2.0
 *
 */
public class AccountFactory implements Factory<Account,Customer, AccountEnum> {

	/**
	 * @param customer the customer who will own the account
	 * @param en the type of account from AccountEnum
	 * @return the created account
	 */
	public Account create(Customer customer, AccountEnum en) {
		switch(en.getType()) {
		case "SAVINGS ACCOUNT":
			return new SavingsAccount(customer, 0);
		case "CHECKING ACCOUNT":
			return new CheckingAccount(customer, 0);
		case "MAXISAVINGACCOUNT":
			return new MaxiSavingAccount(customer, 0);
		}
		return null;
	}
}
