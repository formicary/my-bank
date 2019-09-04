package com.abc;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

public class CustomerTest {
	
	//----------TESTS FOR CREATING EACH TYPE OF ACCOUNT-------------------
	
	@Test
	public void testCheckingAccountCreation() {
		Customer john = new Customer("John");
		john.openCheckingAccount();
		
		assertEquals(1, john.getAccounts().size());
		assertEquals( "Checking Account", john.getAccounts().get(0).getAccountType());
	}
	
	@Test
	public void testSavingAccountCreation() {
		Customer john = new Customer("John");
		john.openSavingAccount();
		
		assertEquals(1, john.getAccounts().size());
		assertEquals("Saving Account", john.getAccounts().get(0).getAccountType());
	}
	
	@Test
	public void testMaxiSavingAccountCreation() {
		Customer john = new Customer("John");
		john.openMaxiSavingAccount();
		
		assertEquals(1, john.getAccounts().size());
		assertEquals("Maxi-Saving Account", john.getAccounts().get(0).getAccountType());
	}
	
	//----------TESTS FOR CREATING MULTIPLE OF EACH TYPE OF ACCOUNT-------------------

	@Test
	public void testTwoCheckingAccounts() {
		Customer john = new Customer("John");
		john.openCheckingAccount();
		john.openCheckingAccount();
		
		assertEquals(2, john.getAccounts().size());
	}
	
	@Test
	public void testTwoSavingAccounts() {
		Customer john = new Customer("John");
		john.openSavingAccount();
		john.openSavingAccount();
		
		assertEquals(2, john.getAccounts().size());
	}
	
	@Test
	public void testTwoMaxiSavingAccounts() {
		Customer john = new Customer("John");
		john.openMaxiSavingAccount();
		john.openMaxiSavingAccount();
		
		assertEquals(2, john.getAccounts().size());
	}
	
	//----------TESTS FOR CREATING MULTIPLE DIFFERENT TYPES OF ACCOUNT-------------------
	
	@Test
	public void testCheckingsAndSavings() {
		Customer john = new Customer("John");
		john.openCheckingAccount();
		john.openSavingAccount();
		
		assertEquals(2, john.getAccounts().size());
		assertEquals("Checking Account", john.getAccounts().get(0).getAccountType());
		assertEquals("Saving Account", john.getAccounts().get(1).getAccountType());
	}
	
	@Test
	public void testCheckingsAndMaxiSavings() {
		Customer john = new Customer("John");
		john.openCheckingAccount();
		john.openMaxiSavingAccount();
		
		assertEquals(2, john.getAccounts().size());
		assertEquals("Checking Account", john.getAccounts().get(0).getAccountType());
		assertEquals("Maxi-Saving Account", john.getAccounts().get(1).getAccountType());
	}
	
	@Test
	public void testSavingsAndMaxiSavings() {
		Customer john = new Customer("John");
		john.openSavingAccount();
		john.openMaxiSavingAccount();
		
		assertEquals(2, john.getAccounts().size());
		assertEquals("Saving Account", john.getAccounts().get(0).getAccountType());
		assertEquals("Maxi-Saving Account", john.getAccounts().get(1).getAccountType());
	}
	
	@Test
	public void testAllAccountCreations() {
		Customer john = new Customer("John");
		john.openCheckingAccount();
		john.openSavingAccount();
		john.openMaxiSavingAccount();
		
		assertEquals(3, john.getAccounts().size());
		assertEquals("Checking Account", john.getAccounts().get(0).getAccountType());
		assertEquals("Saving Account", john.getAccounts().get(1).getAccountType());
		assertEquals("Maxi-Saving Account", john.getAccounts().get(2).getAccountType());
	}
	
	
	//----------TESTS FOR CUSTOMER NAME GETTER/SETTING-------------------
	
	@Test
	public void testNameCreation() {
		Customer john = new Customer("John");
		
		assertEquals("John", john.getName());
	}
	
	@Test
	public void testNameSetter() {
		Customer john = new Customer("Johnn");
		john.setName("John");
		
		assertEquals("John", john.getName());
	}
	
	//-----------TESTS FOR CUSTOMER STATEMENT GENERATION-------------------------
	@Test
	public void TestCustomerStatementGeneration() {
		Customer john = new Customer("John");
		john.openCheckingAccount();
		john.openSavingAccount();
		
		
		
		john.getAccounts().get(0).Deposit(100.0);
		john.getAccounts().get(1).Deposit(4000.0);
		john.getAccounts().get(1).Withdraw(200.0);
				
		 assertEquals("Statement for John\n" +
	                "\n" +
	                "Checking Account\n" +
	                "  deposit $100.00\n" +
	                "Total $100.00\n" +
	                "\n" +
	                "Saving Account\n" +
	                "  deposit $4,000.00\n" +
	                "  withdrawal $200.00\n" +
	                "Total $3,800.00\n" +
	                "\n" +
	                "Total In All Accounts $3,900.00", john.getStatement());
		
	}
	
	@Test
	public void TestCustomerStatementGenerationNegativeTotal() {
		Customer john = new Customer("John");
		john.openCheckingAccount();
		john.openSavingAccount();
		john.openMaxiSavingAccount();
		
		
		john.getAccounts().get(0).Deposit(1000.0);
		john.getAccounts().get(1).Deposit(2000.0);
		john.getAccounts().get(1).Withdraw(3000.0);
		john.getAccounts().get(2).Withdraw(337.0);
		
		System.out.println(john.getStatement());
		
		 assertEquals("Statement for John\n" +
	                "\n" +
	                "Checking Account\n" +
	                "  deposit $1,000.00\n" +
	                "Total $1,000.00\n" +
	                "\n" +
	                "Saving Account\n" +
	                "  deposit $2,000.00\n" +
	                "  withdrawal $3,000.00\n" +
	                "Total $-1,000.00\n" +
	                "\n" +
	                "Maxi-Saving Account\n" +
	                "  withdrawal $337.00\n" +
	                "Total $-337.00\n" +
	                "\n" +
	                "Total In All Accounts $-337.00", john.getStatement());
		
	}
}
