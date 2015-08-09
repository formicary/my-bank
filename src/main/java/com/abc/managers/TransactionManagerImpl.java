package com.abc.managers;

import static com.abc.model.TransactionType.DEPOSIT;
import static com.abc.model.TransactionType.INTEREST;
import static com.abc.model.TransactionType.WITHDRAWAL;

import java.util.Date;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc.model.Account;
import com.abc.model.Customer;
import com.abc.model.CustomerTransactionRequest;
import com.abc.model.Money;
import com.abc.model.Transaction;
import com.abc.model.TransactionType;
import com.abc.providers.DateProvider;
import com.abc.validators.TransactionRequestValidator;

@Service
public class TransactionManagerImpl implements TransactionManager{
	
	
	@Autowired
	private DateProvider dateProvider;
	
	@Autowired
	private TransactionRequestValidator requestValidator;
	
	
	public void addDeposit(final Account account, final Money money){
		validate(account, money);
		createTransaction(account, DEPOSIT, money);
	}
	
	

	public void addWithdrawal(final Account account, final Money money){
		validate(account, money);
		createTransaction(account, WITHDRAWAL, money.negate());
	}
	
	

	public void addIntrest(final Account account, final Money money){
		validate(account, money);
		createTransaction(account, INTEREST, money);
	}
	
	
	private void createTransaction(final Account account, final TransactionType type, final Money amount){
		final Date now = dateProvider.getDate().getTime();
		final Transaction transaction = new Transaction(now, type, amount);
		account.addTransaction( transaction );
	}
	
	
	
	private void validate(final Account account,final Money money){
		Objects.requireNonNull(account);
		Objects.requireNonNull(money);
		if(money.areLowerThenZero()){
			throw new IllegalArgumentException("amount must be greater than zero");
		}
	}



	public void moveCustomerMoney(CustomerTransactionRequest request) {
		Objects.requireNonNull(request);
		requestValidator.validate(request);
		final Customer customer = request.getCustomer();
		final Account fromAccount = customer.getAccountByNumber( request.getAccountNumberFrom() );
		final Account toAccount = customer.getAccountByNumber( request.getAccountNumberTo() );
		
		addWithdrawal(fromAccount, request.getAmount());
		addDeposit(toAccount, request.getAmount());
	}
	
	
	
}
