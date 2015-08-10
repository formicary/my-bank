package com.abc.managers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc.model.Customer;
import com.abc.reports.CustomerStatementGenerator;

@Service
public class CustomerManagerImpl implements CustomerManager {
	
	@Autowired
	private CustomerStatementGenerator customerStatementGenerator;
	
	
	
	public void generateStatement(final Customer customer) {
		customerStatementGenerator.generate(customer);
	}

}
