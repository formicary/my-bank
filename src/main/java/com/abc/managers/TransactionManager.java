package com.abc.managers;

import com.abc.model.Account;
import com.abc.model.CustomerTransactionRequest;
import com.abc.model.Money;

public interface TransactionManager {

	/**
	 * Adds deposit to given account
	 * 
	 * @param account
	 * @param money
	 */
	void addDeposit(Account account, Money money);

	
	/**
	 * Adds withdrawal to given account
	 * 
	 * @param account
	 * @param money
	 */
	void addWithdrawal(Account account, Money money);

	
	/**
	 * Adds interests to given account
	 * 
	 * @param account
	 * @param money
	 */
	void addIntrest(Account account, Money money);

	
	
	/**
	 * Move customer money between their accounts
	 * 	 * 
	 * @param request
	 * @throws IllegalArgumentException if some of customer accounts does not exists
	 * 	or the customer does not have enough money on his account
	 */
	void moveCustomerMoney(CustomerTransactionRequest request);
}
