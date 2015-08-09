package com.abc.validators;

import org.springframework.stereotype.Component;

import com.abc.model.Account;
import com.abc.model.Customer;
import com.abc.model.CustomerTransactionRequest;

@Component
public class RequestValidatorImpl implements TransactionRequestValidator {

	
	
	public void validate(CustomerTransactionRequest request){
		final Customer customer = request.getCustomer();
		final Account fromAccount = customer.getAccountByNumber( request.getAccountNumberFrom() );
		
		if(fromAccount == null){
			throw new IllegalArgumentException("Cutomer account does not exist " + request.getAccountNumberFrom());
		}
		
		if(fromAccount.getBalance().compareTo(request.getAmount()) == -1){
			throw new IllegalArgumentException("Customer does not have enough amout of many in " + request.getAccountNumberFrom());
		}
		
		final Account toAccount = customer.getAccountByNumber(request.getAccountNumberTo());
		
		if(toAccount == null){
			throw new IllegalArgumentException("Cutomer account does not exist " + request.getAccountNumberFrom());
		}
	}
}
