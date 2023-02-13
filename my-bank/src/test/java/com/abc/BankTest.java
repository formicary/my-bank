package com.abc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.abc.Account.AccountType;

import static org.mockito.Mockito.when;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class BankTest {
	private static final double DOUBLE_DELTA = 1e-15;

	@Mock
	private Customer mockCustomer1;
	@Mock
	private Customer mockCustomer2;
	@Mock
	private Customer mockCustomer3;

	@Test
	public void testCustomerSummary() {
		when(mockCustomer1.getName()).thenReturn("John");
		when(mockCustomer1.getNumberOfAccounts()).thenReturn(1);
		when(mockCustomer2.getName()).thenReturn("James");
		when(mockCustomer2.getNumberOfAccounts()).thenReturn(2);

		Bank bank = new Bank();
		bank.addCustomer(mockCustomer1);
		bank.addCustomer(mockCustomer2);

		assertEquals("Customer Summary\n - John (1 account)\n - James (2 accounts)", bank.customerSummary());
	}

	@Test
	public void testAddCustomer() {
		Bank bank = new Bank();
		bank.addCustomer(mockCustomer1);
		bank.addCustomer(mockCustomer2);
		bank.addCustomer(mockCustomer3);
		bank.addCustomer(mockCustomer1);
		bank.addCustomer(null);
		assertEquals(3, bank.getCustomers().size());
	}

	@Test
	public void testTotalInterestPaid() {
		when(mockCustomer1.totalInterestEarned()).thenReturn(1.0);
		when(mockCustomer2.totalInterestEarned()).thenReturn(2.0);
		when(mockCustomer3.totalInterestEarned()).thenReturn(3.0);
		Bank bank = new Bank();
		bank.addCustomer(mockCustomer1);
		bank.addCustomer(mockCustomer2);
		bank.addCustomer(mockCustomer3);
		assertEquals(6.0, bank.totalInterestPaid(), DOUBLE_DELTA);
	}

	@Test
	public void testCreateAccount() {
		Bank bank = new Bank();
		assertNotNull(bank.createAccount(mockCustomer1, AccountType.CHECKING));
	}

}
