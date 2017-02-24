package com.mybank.entities;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.mybank.exceptions.UndefinedAccountTypeException;
import com.mybank.entities.Account;
import com.mybank.entities.Bank;
import com.mybank.entities.Customer;

import static org.junit.Assert.assertEquals;

import org.junit.Before;

public class BankTest {
	private static final double DOUBLE_DELTA = 1e-15;
	private Bank testBank;

	@Mock
	Customer mockCustimer;
	@Mock
	Account mockCheckingAccount;

	@Before
	public void setUp() {
		testBank = new Bank();
		mockCustimer = Mockito.mock(Customer.class);
		mockCheckingAccount = Mockito.mock(Account.class);

		testBank.addCustomer(mockCustimer);
		Mockito.when(mockCustimer.getName()).thenReturn("John");
	}

	@Test
	public void addCustomer_ShouldAddCustomer() {

		assertEquals(1, testBank.getCustomers().size());
		assertEquals(mockCustimer, testBank.getCustomers().get(0));
	}

	@Test
	public void customersSummary_ShouldReturnSummary_GivenOneAccount() {

		Mockito.when(mockCustimer.getNumberOfAccounts()).thenReturn(1);
		assertEquals("Customer Summary\n - John (1 account)", testBank.customersSummary());
	}

	@Test
	public void customersSummary_ShouldReturnSummary_GivenManyAccounts() {

		Mockito.when(mockCustimer.getNumberOfAccounts()).thenReturn(2);
		assertEquals("Customer Summary\n - John (2 accounts)", testBank.customersSummary());

		Mockito.when(mockCustimer.getNumberOfAccounts()).thenReturn(8);
		assertEquals("Customer Summary\n - John (8 accounts)", testBank.customersSummary());

		Mockito.when(mockCustimer.getNumberOfAccounts()).thenReturn(16);
		assertEquals("Customer Summary\n - John (16 accounts)", testBank.customersSummary());
	}

	@Test
	public void totalInterestPaid_ShouldReturnTotalInterestPaid_GivenPossible() throws UndefinedAccountTypeException {

		Mockito.when(mockCustimer.totalInterestEarned()).thenReturn(100.0);
		assertEquals(100.0, testBank.totalInterestPaid(), DOUBLE_DELTA);

	}

	@Test
	public void totalInterestPaid_ShouldReturnMinusOne_GivenUndefinedAccountType()
			throws UndefinedAccountTypeException {

		Mockito.when(mockCustimer.totalInterestEarned()).thenThrow(new UndefinedAccountTypeException());
		assertEquals(-1, testBank.totalInterestPaid(), DOUBLE_DELTA);

	}
}
