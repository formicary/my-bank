package com.abc.reports;

import static java.text.MessageFormat.*;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.abc.model.Bank;
import com.abc.model.Customer;

@Component
public class BankSummaryGeneratorImpl implements BankSummaryGenerator{

	public String generate(final Bank bank) {
		
		Objects.requireNonNull(bank, "Bank can not be null");
		StringBuilder summary = new StringBuilder("Customer Summary");
		
		for(Customer customer :  bank.getCustomers()){
			
			summary.append("\n - ")
					.append(customer.getName())
					.append(" (")
					.append(format("{0} {0,choice,1#account|1<accounts}", customer.getNumberOfAccounts()))
					.append(")");
					
		}
		
		return summary.toString();
		
	}

}
