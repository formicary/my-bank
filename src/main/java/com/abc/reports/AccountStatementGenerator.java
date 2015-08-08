package com.abc.reports;

import com.abc.model.Account;

public interface AccountStatementGenerator {
	
	String generate(Account account);
	
}
