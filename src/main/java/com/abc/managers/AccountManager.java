package com.abc.managers;

import com.abc.model.Account;
import com.abc.model.AccountType;
import com.abc.model.Customer;
import com.abc.model.Money;

public interface AccountManager {

	Account openAccount(Customer customer, AccountType type);

	void addDeposit(Account account, Money money);

	void addWithdrawal(Account account, Money money);

	void addIntrest(Account account, Money money);

	void generateStatement(Account account);

}