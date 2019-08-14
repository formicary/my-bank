package com.abc;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class BankTest {
    
	@Test
	public void testAddCustomerAndBank(){
		Bank b = new Bank();
		Customer c = new Customer("Tom Sturgeon");
		
		b.addCustomer(c);
		assertEquals("Customer Summary\n - Tom Sturgeon (0 accounts)", b.customerSummary());
		
		c.openAccount(new CheckingAccount());
		assertEquals("Customer Summary\n - Tom Sturgeon (1 account)", b.customerSummary());
		
		c.openAccount(new SavingsAccount());
		assertEquals("Customer Summary\n - Tom Sturgeon (2 accounts)", b.customerSummary());	
	}
	
	@Test
	public void testGetFirstCustomer(){
		Bank b = new Bank();
		Customer c0 = new Customer("Tom Sturgeon");
		Customer c1 = new Customer("Someone Else");
		b.addCustomer(c0);
		b.addCustomer(c1);
		
		assertEquals("Tom Sturgeon", b.getFirstCustomer());
	}
	
	@Test
	public void testTotalInterestPaid(){
		Bank b = new Bank();
		Customer c1 = new Customer("Tom Sturgeon");
		Customer c2 = new Customer("Someone Else");
		
		b.addCustomer(c1);
		b.addCustomer(c2);
		
    	Account checking1 = new CheckingAccount();
    	Account savings1 = new SavingsAccount();
    	Account checking2 = new CheckingAccount();
    	Account savings2 = new SavingsAccount();
    	
    	checking1.deposit(new Money("1000")); // 0.1 flat rate
    	savings1.deposit(new Money("1100")); // 0.2 interest
    	
    	checking2.deposit(new Money("1000")); // 0.1 flat rate
    	savings2.deposit(new Money("1100")); // 0.2 interest
    	
    	c1.openAccount(checking1);
    	c1.openAccount(savings1);
    	c2.openAccount(checking2);
    	c2.openAccount(savings2);
    	
    	// check all accounts each one with interest of $3.2 totals to $6.4
    	assertEquals(new Money("6.4").getAmount(), b.totalInterestPaid().getAmount());
	}
  

}
