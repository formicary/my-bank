package com.abc.managers;

import com.abc.model.Account;
import com.abc.model.AccountType;
import com.abc.model.Customer;

public interface AccountManager {

	Account openAccount(Customer customer, AccountType type);

	void generateStatement(Account account);
	


}