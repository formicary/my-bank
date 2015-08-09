package com.abc.managers;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc.calculators.InterestCalculatorFactory;
import com.abc.calculators.IntrestCalculator;
import com.abc.model.Account;
import com.abc.model.AccountType;
import com.abc.model.Customer;
import com.abc.model.Money;
import com.abc.providers.AccountNumberProvider;
import com.abc.providers.DateProvider;
import com.abc.reports.AccountStatementGenerator;

@Service
public class AccountManagerImpl implements AccountManager{
	
	@Autowired
	private AccountStatementGenerator accountStatementGenerator;
	
	@Autowired
	private DateProvider dateProvider;
	
	@Autowired
	private AccountNumberProvider accountNumberProvider;
	
	@Autowired
	private InterestCalculatorFactory interestCalculatorFactory;


	
	public Account openAccount(final Customer customer, final AccountType type) {
		Objects.requireNonNull(customer);
		Objects.requireNonNull(type);
		final int accountNumber = accountNumberProvider.getAccountNumber();
		final Account account =  new Account(accountNumber, type);
		customer.addAccount(account);
		return account;
	}
	
	
	

	public void generateStatement(final Account account){
		accountStatementGenerator.generate(account);
	}



	public Money calculateInterest(Account account) {
		IntrestCalculator calculator = interestCalculatorFactory.getCalculator(account); 
		return calculator.calculateInterestEarned(account);
	}
	
	
	
}
