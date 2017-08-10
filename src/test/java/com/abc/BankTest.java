package com.abc;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import Banking.Bank;
import Banking.Customer;

public class BankTest {

	
	@Test
	public void addingOneCustomerReturnsASizeOfOne(){
		
		Bank bank = new Bank();
		
		bank.addCustomer(new Customer("Dave"));
	
		assertEquals(bank.getCustomers().size(),1);
	}
	
	@Test
	public void addingNoCustomersReturnsASizeOfZero(){
		
		Bank bank = new Bank();
			
		assertEquals(bank.getCustomers().size(),0);
	}
	
}
