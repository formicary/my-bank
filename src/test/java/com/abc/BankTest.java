package com.abc;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BankTest {
	
	private Bank bank;
	
	@Before
	public void setUp() throws Exception {
		bank = new Bank();
	}

	@Test
	public void testCreateCustomer() {
		bank.createCustomer(TestConstants.CUSTOMER1);
		assertEquals(TestConstants.CUSTOMER1, bank.getCustomerList().get(0).getName());
	}
	
	@Test
	public void testTotalInterestPaid() {
		Customer cust1 = new Customer(TestConstants.CUSTOMER1);
		Customer cust2 = new Customer(TestConstants.CUSTOMER2);
		bank.getCustomerList().add(cust1);
		bank.getCustomerList().add(cust2);
		
		assertEquals(TestConstants.ZERO_INTEREST, bank.totalInterestPaid());
		
		cust1.openAccount(TestConstants.CHECKING_ACCOUNT);
		bank.getCustomerList().get(0).deposit(TestConstants.SECOND_ACCOUNT_ID, TestConstants.DEPOSIT_AMOUNT);
		assertEquals(TestConstants.TOTAL_INTEREST_PAID, bank.totalInterestPaid());
		
		cust2.openAccount(TestConstants.MAXISAVINGS_ACCOUNT);
		cust2.deposit(TestConstants.THIRD_ACCOUNT_ID, TestConstants.DEPOSIT_AMOUNT);
		assertEquals(TestConstants.TOTAL_INTEREST_PAID_TWO_ACCOUNTS, bank.totalInterestPaid());	
	}
	
	@Test
	public void testCustomerSummaries() {
		assertEquals(TestConstants.NO_CUSTOMERS_WITH_BANK, bank.customerSummaries());
		
		
		bank.createCustomer(TestConstants.CUSTOMER1);
		assertEquals(TestConstants.ONE_CUSTOMER_NO_ACCOUNTS, bank.customerSummaries());
		
		bank.createCustomer(TestConstants.CUSTOMER2);
		bank.getCustomerList().get(0).openAccount(TestConstants.CHECKING_ACCOUNT);
		assertEquals(TestConstants.TWO_CUSTOMERS_ONE_ACCOUNT, bank.customerSummaries());
		bank.customerSummaries();
	}
	
	@After
	public void tearDown() throws Exception {
		bank = null;
	}
	
	
}
