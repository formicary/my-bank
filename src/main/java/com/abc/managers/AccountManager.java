package com.abc.managers;

import com.abc.model.Account;
import com.abc.model.AccountType;
import com.abc.model.Customer;
import com.abc.model.Money;

public interface AccountManager {
	
	/**
	 * Opens a new customer account
	 * 
	 * @param customer
	 * @param type
	 * @return
	 */
	Account openAccount(Customer customer, AccountType type);

	
	/**
	 * Generates account statement, which consists of all
	 * transactions and final account balance
	 * 
	 * @param account
	 */
	void generateStatement(Account account);
	
	
	
	/**
	 * Calculates interests for given account
	 * 
	 * The calculation is base on {@link AccountType}. 
	 * 
	 * @param account
	 * @return calculated interests for given account
	 */
	Money calculateInterest(Account account);

}