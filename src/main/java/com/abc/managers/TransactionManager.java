package com.abc.managers;

import com.abc.model.Account;
import com.abc.model.CustomerTransactionRequest;
import com.abc.model.Money;

public interface TransactionManager {

	void addDeposit(Account account, Money money);

	void addWithdrawal(Account account, Money money);

	void addIntrest(Account account, Money money);

	void moveCustomerMoney(CustomerTransactionRequest request);
}
