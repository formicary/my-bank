package com.abc.bank;

import java.util.List;
import java.util.Map;

import com.abc.bank.account.Account;
import com.abc.bank.account.AccountType;
import com.abc.bank.admin.Customer;
import com.abc.bank.bankops.AuthorizationException;
import com.abc.bank.bankops.Transaction;
import com.abc.bank.bankops.TransactionType;


public interface ICustomerService {
	
	/**
	 * Adds the account.
	 *
	 * @param customer the customer
	 * @param accType the acc type
	 * @return the integer
	 */
	Integer addAccount(Customer customer,AccountType accType);
	
	/**
	 * Do banking.
	 *
	 * @param customer the customer
	 * @param tType the t type
	 * @param params the params
	 * @return the list
	 */
	List<Transaction> doBanking(Customer customer,TransactionType tType, Object... params);
	
	/**
	 * Gets the accounts.
	 *
	 * @param c the c
	 * @return the accounts
	 */
	Map<Integer,Account> getAccounts(Customer c);
	
	/**
	 * Gets the account.
	 *
	 * @param c the c
	 * @param accNo the acc no
	 * @return the account
	 * @throws AuthorizationException the authorization exception
	 */
	Account getAccount (Customer c, Integer accNo) throws AuthorizationException;
	
	/**
	 * Creates the statement.
	 *
	 * @param c the c
	 * @return the string
	 */
	public String createStatement(Customer c);

}
