package com.abc.reports;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.abc.model.AccountBuilder;
import com.abc.model.Bank;
import com.abc.model.Customer;


public class BankSummaryGeneratorImplTest {
	
	private BankSummaryGenerator summaryGenerator = new BankSummaryGeneratorImpl();
	
	
	@Test
	public void shouldGenerateBankSummaryWithOneCustomerAndAccount(){
		Bank bank = new Bank();
		Customer customer = new Customer("ANY_NAME");
		customer.addAccount(AccountBuilder.createChecking().get());
		bank.addCustomer(customer);
		
		
		assertEquals("Customer Summary\n - ANY_NAME (1 account)", summaryGenerator.generate(bank));
	}
	
	
	@Test
	public void shouldGenerateBankSummaryWithMultipleCustomerAndAccount(){
		Bank bank = new Bank();
		Customer customer = new Customer("ANY_NAME");
		bank.addCustomer(customer);
		customer.addAccount(AccountBuilder.createChecking().get());
		customer.addAccount(AccountBuilder.createMaxiSaving().get());
		
		assertEquals("Customer Summary\n - ANY_NAME (2 accounts)", summaryGenerator.generate(bank));
	}
	
	@Test(expected = NullPointerException.class)
	public void shouldFailedIfBankIsNull(){
		summaryGenerator.generate( null );
	}
}
