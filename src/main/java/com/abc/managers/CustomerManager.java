package com.abc.managers;

import com.abc.model.Account;
import com.abc.model.Customer;
import com.abc.reports.AccountStatementGenerator;

public interface CustomerManager {
	
	/**
	 * Generates customer statement
	 * 
	 * The statement includes all {@link Account} statements. 
	 * 
	 * @param customer
	 * @see {@link AccountStatementGenerator}
	 */
	void generateStatement(Customer customer);
}
