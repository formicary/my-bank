package com.abc.reports;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.abc.model.Account;
import com.abc.model.Customer;
import com.abc.model.Money;

@Component
public class CustomerStatementGeneratorImpl implements CustomerStatementGenerator{
	
	@Autowired
	private AccountStatementGenerator accountStatementGenerator;
	
	public String generate(final Customer customer) {
		Objects.requireNonNull(customer, "Account can not be null");
		
		final StringBuilder statement = new StringBuilder("Statement for ")
				.append(customer.getName())
				.append("\n");
		
		Money total = Money.ZERO_USD;
		for (final Account account : customer.getAccounts()) {
            statement
            	.append("\n")
            	.append(accountStatementGenerator.generate(account))
            	.append("\n");
            	total = total.add(account.getBalance());
        }
		
		statement
			.append("\nTotal In All Accounts ")
			.append(total.getFormatted());
		
		return statement.toString();
	}

}
