package com.abc.validators;

import com.abc.model.CustomerTransactionRequest;

public interface TransactionRequestValidator {
	
	void validate(CustomerTransactionRequest request);
}
