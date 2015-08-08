package com.abc.reports;

import com.abc.model.Customer;

public interface CustomerStatementGenerator {
	
	String generate(Customer account);
}
