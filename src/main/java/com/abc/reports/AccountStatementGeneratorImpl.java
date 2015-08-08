package com.abc.reports;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.abc.model.Account;
import com.abc.model.Transaction;

@Component
public class AccountStatementGeneratorImpl implements AccountStatementGenerator{

	
	
	public String generate(final Account account) {
		Objects.requireNonNull(account);
		
		final StringBuilder statement = new StringBuilder()
				.append(account.getAccountType().getName())
				.append("\n");
		
		for(final Transaction transaction : account.getTransactionList()){
			statement.append("  ");
			statement.append(transaction.getType().getName());
			statement.append(" ")
					.append(transaction.getMoney().getFormatted())
					.append("\n ");
		}
		
		
		statement.append("Total ")
				 .append(account.getBalance());
		
		return statement.toString();
	}

	
}
