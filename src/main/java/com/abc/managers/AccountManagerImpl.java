package com.abc.managers;

import static com.abc.model.TransactionType.DEPOSIT;
import static com.abc.model.TransactionType.INTEREST;
import static com.abc.model.TransactionType.WITHDRAWAL;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc.model.Account;
import com.abc.model.AccountType;
import com.abc.model.Customer;
import com.abc.model.Money;
import com.abc.model.Transaction;
import com.abc.reports.AccountStatementGenerator;

@Service
public class AccountManagerImpl implements AccountManager{
	
	@Autowired
	private AccountStatementGenerator accountStatementGenerator;
	

	/* (non-Javadoc)
	 * @see com.abc.managers.AccountManager#openAccount(com.abc.model.Customer, com.abc.model.AccountType)
	 */
	public Account openAccount(final Customer customer, final AccountType type) {
		Objects.requireNonNull(customer);
		Objects.requireNonNull(type);
		final Account account =  new Account(type);
		customer.addAccount(account);
		return account;
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.abc.managers.AccountManager#addDeposit(com.abc.model.Account, com.abc.model.Money)
	 */
	public void addDeposit(final Account account, final Money money){
		validate(account, money);
		final Transaction transaction = new Transaction(DEPOSIT, money);
		account.addTransaction( transaction );
	}
	
	
	/* (non-Javadoc)
	 * @see com.abc.managers.AccountManager#addWithdrawal(com.abc.model.Account, com.abc.model.Money)
	 */
	public void addWithdrawal(final Account account, final Money money){
		validate(account, money);
		final Transaction transaction = new Transaction(WITHDRAWAL, money.negate());
		account.addTransaction(transaction);
	}
	
	
	/* (non-Javadoc)
	 * @see com.abc.managers.AccountManager#addIntrest(com.abc.model.Account, com.abc.model.Money)
	 */
	public void addIntrest(final Account account, final Money money){
		validate(account, money);
		final Transaction transaction = new Transaction(INTEREST, money);
		account.addTransaction(transaction);
	}
	
	/* (non-Javadoc)
	 * @see com.abc.managers.AccountManager#generateStatement(com.abc.model.Account)
	 */
	public void generateStatement(final Account account){
		accountStatementGenerator.generate(account);
	}
	
	private void validate(final Account account,final Money money){
		Objects.requireNonNull(account);
		Objects.requireNonNull(money);
		if(money.areLowerThenZero()){
			throw new IllegalArgumentException("amount must be greater than zero");
		}
	}
	
}
