package com.abc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BankTest {
	private static final double DOUBLE_DELTA = 1e-15;

	@Test
	public void customerSummary() {
		Bank bank = new Bank();
		Customer john = new Customer("John");
		john.openAccount(AccountFactory.getInstance().createAccount(Account.CHECKING, AccountFactory.DEBUG_ENABLED));
		bank.addCustomer(john);

		assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
	}


	
	@Test
	public void firstCustomerTest() {
		Bank bank = new Bank();
		Customer bill = new Customer("Bill");
		bank.addCustomer(bill);
		bank.addCustomer(new Customer("Bob"));
		
		assertEquals("Bill", bank.getFirstCustomer());
	}
	
	@Test
	public void firstCustomerExceptionTest() {
		Bank bank = new Bank();
		
		assertEquals("Error", bank.getFirstCustomer());
		
	}

}
